
import java.util.HashSet;
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
public class BridgeExchangeFinder {
    public static void main(String args[]){
        String[] currencies = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"};
        double[][] exchangeRates = new double[][]{
            {1, 1.2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},             //A
            {1.2, 1, 1.2, 1.2, 0, 0, 0, 0, 0, 0, 0, 0, 0},        //B
            {0, 1.2, 1, 1.2, 1.2, 0, 0, 0, 0, 0, 0, 0, 0},       //C
            {0, 1.2, 1.2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},        //D
            {0, 0, 1.2, 0, 1, 1.2, 1.2, 1.2, 0, 1.2, 0, 0, 0},                       //E
            {0, 0, 0, 0, 1.2, 1, 1.2, 0, 0, 0, 0, 0, 0},             //F
            {0, 0, 0, 0, 1.2, 1.2, 1, 0, 0, 0, 0, 0, 0},             //G
            {0, 0, 0, 0, 1.2, 0, 0, 1, 1.2, 0, 0, 0, 0},             //H
            {0, 0, 0, 0, 0, 0, 0, 1.2, 1, 0, 0, 0, 0},             //I
            {0, 0, 0, 0, 1.2, 0, 0, 0, 0, 1, 1.2, 1.2, 1.2},            //J
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1.2, 1, 1.2, 0},             //K
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1.2, 1.2, 1, 1.2},             //L
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1.2, 0, 1.2, 1},              //M
        };

        BridgeExchangeGraph<String> graph = new BridgeExchangeGraph<String>(currencies, exchangeRates);
        System.out.println(graph);
        Set<Edge<String>> bridges = graph.findBridges(0);
        
        System.out.println("The bridges of the provided exchange rates are: ");
        for(Edge bridge : bridges)
            System.out.println(bridge);
        
        String[] currencies2 = new String[]{"EUR", "NZD", "AUD", "USD", "MXN"};
        double[][] exchangeRates2 = new double[][]{
            {1.0000, 1.7919, 1.6751, 1.1861, 0},
            {0.5579, 1.0000, 0.9348, 0.6616, 0},
            {0.5966, 1.0694, 1.0000, 0.7077, 0},
            {0.8431, 1.5110, 1.4125, 1.0000, 21.0496},
            {0.0000, 0.0000, 0.0000, 0.0474, 1.0000},
        };
        
        BridgeExchangeGraph<String> graph2 = new BridgeExchangeGraph<String>(currencies2, exchangeRates2);
        System.out.println("\n"+graph2);
        Set<Edge<String>> bridges2 = graph2.findBridges(0);
        
        System.out.println("The bridges of the provided exchange rates are: ");
        for(Edge bridge : bridges2)
            System.out.println(bridge);
    }
}
