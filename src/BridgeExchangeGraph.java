
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    
    public BridgeExchangeGraph(String[] currencies, double[][] exchangeRates){
        super(GraphType.UNDIRECTED);
        this.currencies = currencies;
        this.exchangeRates = exchangeRates;
        vertexList = new ArrayList<>();
        
        for(String currency : currencies)
            vertexList.add(this.addVertex((E)currency));
        
        for(int i=0;i<exchangeRates.length;i++){
            for(int j=i;j<exchangeRates[i].length;j++){
                double exchangeRate = exchangeRates[i][j];
                
                if(exchangeRate != 0 && i != j){
                    this.addEdge(vertexList.get(i), vertexList.get(j));
                }
            }
        }
    }
}
