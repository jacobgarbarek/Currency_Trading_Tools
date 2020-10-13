
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
    private Map<Edge<String>,Float> weights;
    
    public CurrencyGraph(String[] currencies, float[][] exchangeRates){
        super(GraphType.DIRECTED);
        this.currencies = currencies;
        this.exchangeRates = exchangeRates;
        weights = new HashMap<Edge<String>,Float>();
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
                    Edge<String> edge = this.addEdge(vertexList.get(i), vertexList.get(j));
                    weights.put(edge, exchangeRate);
                }
            }
        }
    }
    
    public String getBestConversionRate(int currency1, int currency2){
        Vertex currencyVertex1 = vertexList.get(currency1);
        Vertex currencyVertex2 = vertexList.get(currency2);
        
        
        
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
