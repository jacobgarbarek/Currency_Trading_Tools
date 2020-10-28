import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jacob Garbarek (ID: 17980551) & Angelo Ryndon (ID: 18028033)
 */
public class BridgeExchangeGraph<E> extends AdjacencyListGraph<E> {
    private static enum Colour{WHITE, GREY, BLACK};                             //Colour representing vertex being not visited, visited & processed
    private double[][] exchangeRates;
    private String[] currencies;
    private ArrayList<Vertex> vertexList;
    private Map<Vertex<E>, Colour> vertexColours;
    private Map<Vertex<E>, Vertex<E>> parents;                                  //keeps track of each vertex's parent during search
    private Set<Edge<E>> traversedEdges;
    private Map<Vertex<E>, Integer> d;                                          //vertex's label as it is discovered (based on counter)
    private Map<Vertex<E>, Integer> m;                                          //label indicating how far back vertex can go (without via parent)
    private int counter;                                                        //keeps of order vertex is visited in
    
    public BridgeExchangeGraph(String[] currencies, double[][] exchangeRates){
        super(GraphType.UNDIRECTED);
        this.currencies = currencies;
        this.exchangeRates = exchangeRates;
        vertexList = new ArrayList<>();
        d = new HashMap<Vertex<E>, Integer>();
        m = new HashMap<Vertex<E>, Integer>();
        parents = new HashMap<Vertex<E>, Vertex<E>>();
        traversedEdges = new HashSet<>();
        counter = 0;
        
        for(String currency : currencies){                                      //creates vertices in graph from supplied currencies
            vertexList.add(this.addVertex((E)currency));
        }
        
        vertexColours = new HashMap<Vertex<E>, Colour>(vertices.size());
        
        for (Vertex<E> vertex : vertices)                                       //colours all vertices as unvisited
            vertexColours.put(vertex, Colour.WHITE); 
        
        for(int i=0;i<exchangeRates.length;i++){                                //creates all edges within graph using exchange rate provided
            for(int j=i;j<exchangeRates[i].length;j++){
                double exchangeRate = exchangeRates[i][j];
                
                if(exchangeRate != 0 && i != j){
                    this.addEdge(vertexList.get(i), vertexList.get(j));
                }
            }
        }
    }
    
    //Depth First Search method adapted and taken from Andrew Ensor
    private void performDepthFirstSearch(Vertex<E> startVertex)
   {  if (!this.containsVertex(startVertex))
         throw new IllegalArgumentException("vertex not in graph");
      // handle the starting vertex
      vertexColours.put(startVertex, Colour.GREY);
      vertexDiscovered(startVertex);
      // visit each adjacent vertex
      for (Edge<E> incidentEdge : startVertex.incidentEdges())
      {  Vertex<E> adjacentVertex
            = incidentEdge.oppositeVertex(startVertex);
         if (vertexColours.get(adjacentVertex) == Colour.WHITE)
         {  edgeTraversed(incidentEdge);
            performDepthFirstSearch(adjacentVertex);
         }
      }
      vertexColours.put(startVertex, Colour.BLACK);
      vertexFinished(startVertex);
   }
    
    // hook method that is called whenever a vertex has been discovered
   private void vertexDiscovered(Vertex<E> vertex)
   {
       d.put(vertex, counter);                                                  //tracks vertex in order visited
       counter++;
   }
   
   // hook method that is called whenever a vertex has been finished
   private void vertexFinished(Vertex<E> vertex)
   {    
       Set<Vertex<E>> endVertices = vertex.adjacentVertices();                  //filters parent vertex out of processed  vertex's end vertices
       Vertex parentVertex = parents.get(vertex);
       if(parentVertex != null)
           endVertices.remove(parentVertex);
       
       int smallestValue = d.get(vertex);
       
       for(Vertex v : endVertices){                                             //checks neighbours of processed vertex to calculate m value
           if(vertexColours.get(v) == Colour.BLACK){                            //neighbour already processed
               int value = m.get(v);                                        
               smallestValue = Math.min(smallestValue, value);
           } else if(vertexColours.get(v) == Colour.GREY){                      //neighbour not processed
               int value = d.get(v);
               smallestValue = Math.min(smallestValue, value);
           }
       }
       
       m.put(vertex, smallestValue);
   }
   
   // hook method that is called whenever a tree edge is traversed
   private void edgeTraversed(Edge<E> edge)
   {  
       Vertex[] vertices = edge.endVertices();
       
       if(parents.containsKey(vertices[1])){                                        //edge is the wrong way around
           Edge<E> rotatedEdge = new AdjacencyListEdge(vertices[1], vertices[0]);
           vertices = rotatedEdge.endVertices();
           traversedEdges.add(rotatedEdge);
       }else
           traversedEdges.add(edge);
       
       parents.put(vertices[1], vertices[0]);                                   //records parent of traversed edge
   }
   
   public Set<Edge<E>> findBridges(int index){
       performDepthFirstSearch(vertexList.get(index));
       Set<Edge<E>> bridges = new HashSet<>();
       
       for(Edge e : traversedEdges){
           Vertex[] vertices = e.endVertices();
           if(m.get(vertices[1]) > d.get(vertices[0]))
               bridges.add(e);
       }
       
       if(bridges.isEmpty())
           return null;                                                         //handle within program
       
       return bridges;
   }
}
