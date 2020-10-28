
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
 * @author Jacob
 */
public class BridgeExchangeGraph<E> extends AdjacencyListGraph<E> {
    private double[][] exchangeRates;
    private String[] currencies;
    private ArrayList<Vertex> vertexList;
    private static enum Colour{WHITE, GREY, BLACK};
    private Map<Vertex<E>, Colour> vertexColours;
    private Map<Vertex<E>, Vertex<E>> parents;
    public Set<Edge<E>> traversedEdges;
    private Vertex parent;
    public Map<Vertex<E>, Integer> d;
    public Map<Vertex<E>, Integer> m;
    private int counter;
    
    public BridgeExchangeGraph(String[] currencies, double[][] exchangeRates){
        super(GraphType.UNDIRECTED);
        this.currencies = currencies;
        this.exchangeRates = exchangeRates;
        vertexList = new ArrayList<>();
        d = new HashMap<Vertex<E>, Integer>();
        m = new HashMap<Vertex<E>, Integer>();
        parents = new HashMap<Vertex<E>, Vertex<E>>();
        traversedEdges = new HashSet<>();
        parent = null;
        counter = 0;
        
        for(String currency : currencies){
            vertexList.add(this.addVertex((E)currency));
        }
        
        vertexColours = new HashMap<Vertex<E>, Colour>(vertices.size());
        
        for (Vertex<E> vertex : vertices)
            vertexColours.put(vertex, Colour.WHITE); 
        
        for(int i=0;i<exchangeRates.length;i++){
            for(int j=i;j<exchangeRates[i].length;j++){
                double exchangeRate = exchangeRates[i][j];
                
                if(exchangeRate != 0 && i != j){
                    this.addEdge(vertexList.get(i), vertexList.get(j));
                }
            }
        }
    }
    
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
   protected void vertexDiscovered(Vertex<E> vertex)
   {
       d.put(vertex, counter);
       counter++;
   }
   
   // hook method that is called whenever a vertex has been finished
   protected void vertexFinished(Vertex<E> vertex)
   {
       Set<Vertex<E>> endVertices = vertex.adjacentVertices();
       Vertex parentVertex = parents.get(vertex);
       if(parentVertex != null)
           endVertices.remove(parentVertex);
       
       int smallestValue = d.get(vertex);
       
       for(Vertex v : endVertices){
           if(vertexColours.get(v) == Colour.BLACK){
               int value = m.get(v);
               smallestValue = Math.min(smallestValue, value);
           } else if(vertexColours.get(v) == Colour.GREY){
               int value = d.get(v);
               smallestValue = Math.min(smallestValue, value);
           }
       }
       
       m.put(vertex, smallestValue);
   }
   
   // hook method that is called whenever a tree edge is traversed
   protected void edgeTraversed(Edge<E> edge)
   {  
       Vertex[] vertices = edge.endVertices();
       parents.put(vertices[1], vertices[0]);
       traversedEdges.add(edge);
   }
   
   public Set<Edge<E>> findBridges(int index){
       performDepthFirstSearch(vertexList.get(index));
       Set<Edge<E>> bridges = new HashSet<>();
       
       for(Edge e : traversedEdges){
           Vertex[] vertices = e.endVertices();
           if(m.get(vertices[1]) > d.get(vertices[0]))
               bridges.add(e);
       }
       
       return bridges;
   }
}
