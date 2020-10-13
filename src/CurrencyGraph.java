
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
public class CurrencyGraph<E> extends AdjacencyListGraph{
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
            vertexList.add(this.addVertex(currency));
        
        for(int i=0;i<exchangeRates.length;i++){
            for(int j=0;j<exchangeRates[i].length;j++){
                float exchangeRate = exchangeRates[i][j];
                
                if(exchangeRate != 0){
                    exchangeRate = (float) Math.log(1/exchangeRate);
                    Edge<E> edge = this.addEdge(vertexList.get(i), vertexList.get(j));
                    weights.put(edge, exchangeRate);
                }
            }
        }
    }
    
    public Set<Edge<E>> getShortestPaths(Vertex<E> source){
        Map<Vertex<E>,Edge<E>> leastEdges = new HashMap<Vertex<E>,Edge<E>>();
        final Map<Vertex<E>,Float> shortestPathEstimates = new HashMap<Vertex<E>,Float>();
        
        for (Object vertex : this.vertexSet()){
            shortestPathEstimates.put((Vertex<E>) vertex, Float.MAX_VALUE);
            leastEdges.put((Vertex<E>) vertex, null);
        }
        
        shortestPathEstimates.put(source, new Float(0));
        
        
        return null;
    }
    
    @Override
    public String toString()
   {  String output = "";
      for (int i=0; i<currencies.length; i++){
          output += "("+i+") "+currencies[i] + " has the following exchange rates: ";
          for(int j=0; j<exchangeRates[i].length;j++){
              if(j != i)
                output += exchangeRates[i][j] + "["+currencies[j]+"], ";
          }
          output += "\n";
      }
      
      return output;
   }
}
