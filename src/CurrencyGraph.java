
import java.text.DecimalFormat;
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
public class CurrencyGraph<E> extends AdjacencyListGraph<E>{
    private float[][] exchangeRates;
    private String[] currencies;
    private ArrayList<Vertex> vertexList;
    private Map<Edge<E>,Float> weights;
    
    public CurrencyGraph(String[] currencies, float[][] exchangeRates){
        super(GraphType.DIRECTED);
        this.currencies = currencies;
        this.exchangeRates = exchangeRates;
        weights = new HashMap<Edge<E>,Float>();
        vertexList = new ArrayList<>();
        calculateAdjacencyList();
    }

    private void calculateAdjacencyList() {
        for(String currency : currencies)
            vertexList.add(this.addVertex((E)currency));
        
        for(int i=0;i<exchangeRates.length;i++){
            for(int j=0;j<exchangeRates[i].length;j++){
                float exchangeRate = exchangeRates[i][j];
                
                if(exchangeRate != 0 && i != j){
                    exchangeRate = (float) Math.log(1/exchangeRate);
                    Edge<E> edge = this.addEdge(vertexList.get(i), vertexList.get(j));
                    weights.put(edge, exchangeRate);
                }
            }
        }
    }
    
    public ShortestPathResult getShortestPaths(int sourceIndex, int destinationIndex){
        Vertex<E> source = vertexList.get(sourceIndex);
        Vertex<E> destination = vertexList.get(destinationIndex);
        Map<Vertex<E>,Edge<E>> leastEdges = new HashMap<Vertex<E>,Edge<E>>();
        Map<Vertex<E>,Float> shortestPathEstimates = new HashMap<Vertex<E>,Float>();
        
        for (Vertex<E> vertex : this.vertexSet()){
            shortestPathEstimates.put((Vertex<E>) vertex, Float.MAX_VALUE);
            leastEdges.put((Vertex<E>) vertex, null);
        }
        
        shortestPathEstimates.put(source, new Float(0));
        
        for(int i=1;i<this.vertexSet().size();i++){
            for (Edge<E> edge : this.edgeSet()){
                Vertex<E>[] endVertices = edge.endVertices();
                Float du = shortestPathEstimates.get(endVertices[0]);
                
                if(du != Float.MAX_VALUE){
                    if (du + weights.get(edge) < shortestPathEstimates.get(endVertices[1])) {
                        shortestPathEstimates.put(endVertices[1], du + weights.get(edge));
                        leastEdges.put(endVertices[1], edge);
                    }
                }
            }
        }
        
        for (Edge<E> edge : this.edgeSet()){
            Vertex<E>[] endVertices = edge.endVertices();
            Float du = shortestPathEstimates.get(endVertices[0]);
            
            if(du != Float.MAX_VALUE){
                if (du + weights.get(edge) < shortestPathEstimates.get(endVertices[1])) {
                    return new ShortestPathResult(leastEdges, weights, true, source, destination);
                }
            }
        }
        
        return new ShortestPathResult(leastEdges, weights, false, source, destination);
    }
    
    @Override
    public String toString()
   {  String output = "Exchange Rates (13 Oct 2020)\n";
      for (int i=0; i<currencies.length; i++){
          output += "("+i+") "+currencies[i] + " : ";
          for(int j=0; j<exchangeRates[i].length;j++){
              if(j != i)
                output += exchangeRates[i][j] + "["+currencies[j]+"], ";
          }
          output += "\n";
      }
      
      output += "\nEdge Weights\n";
      
      for (Vertex<E> vertex : vertices){
         output += vertex + ": ";
         Set<Edge<E>> e = adjacencyLists.get(vertex);
         
         for(Edge<E> edge : e){
             output += weights.get(edge) + "" + edge+", ";
         }
         output += "\n";
      }
      
      return output;
   }
}
