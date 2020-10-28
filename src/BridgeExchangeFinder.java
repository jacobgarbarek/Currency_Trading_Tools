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
public class BridgeExchangeFinder {
    public static void main(String args[]){
        System.out.println("Test 1 (Based off Assessment Example)\n");
        String[] currencies = new String[]{"AFN", "BMD", "CNY", "DKK", "EUR", "FJD", "GMD", "HUF", "ISK", "USD", "NZD", "AUD", "PLN"};
        double[][] exchangeRates = new double[][]{
            {1, 1.2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},                          //AFN (A)
            {1.2, 1, 1.2, 1.2, 0, 0, 0, 0, 0, 0, 0, 0, 0},                      //BMD (B)
            {0, 1.2, 1, 1.2, 1.2, 0, 0, 0, 0, 0, 0, 0, 0},                      //CNY (C)
            {0, 1.2, 1.2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},                        //DKK (D)
            {0, 0, 1.2, 0, 1, 1.2, 1.2, 1.2, 0, 1.2, 0, 0, 0},                  //EUR (E)
            {0, 0, 0, 0, 1.2, 1, 1.2, 0, 0, 0, 0, 0, 0},                        //FJD (F)
            {0, 0, 0, 0, 1.2, 1.2, 1, 0, 0, 0, 0, 0, 0},                        //GMD (G)
            {0, 0, 0, 0, 1.2, 0, 0, 1, 1.2, 0, 0, 0, 0},                        //HUF (H)
            {0, 0, 0, 0, 0, 0, 0, 1.2, 1, 0, 0, 0, 0},                          //ISK (I)
            {0, 0, 0, 0, 1.2, 0, 0, 0, 0, 1, 1.2, 1.2, 1.2},                    //USD (J)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1.2, 1, 1.2, 0},                        //NZD (K)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1.2, 1.2, 1, 1.2},                      //AUD (L)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1.2, 0, 1.2, 1},                        //PLN (M)
        };

        BridgeExchangeGraph<String> graph = new BridgeExchangeGraph<String>(currencies, exchangeRates);
        System.out.println(graph);
        Set<Edge<String>> bridges = graph.findBridges(0);
        
        if(bridges.isEmpty()){
            System.out.println("There are no bridges in the provided exchange rates.");
        } else {
            System.out.println("The bridges of the provided exchange rates are: ");
            for (Edge bridge : bridges) {
                System.out.println(bridge);
            }
        }
        
        System.out.println("\n***********************************************************\n");
        
        System.out.println("Test 2 (Based off Andrew's Example)\n");
      
        String[] currencies2 = new String[]{"AUD","EUR","MXN","NZD","USD"};
        double[][] exchangeRates2 = new double[][]{
            {1.0000, 0.5966, 0.0000, 1.0694, 0.7077 },
            {1.6751, 1.0000, 0.0000, 1.7919, 1.1861 },
            {0.0000, 0.0000, 1.0000, 0.0000, 0.0474 },
            {0.9348, 0.5579, 0.0000, 1.0000, 0.6616 },
            {1.4125, 0.8431, 21.0496, 1.5110, 1.0000 }
        };
        
        BridgeExchangeGraph<String> graph2 = new BridgeExchangeGraph<String>(currencies2, exchangeRates2);
        System.out.println(graph2);
        Set<Edge<String>> bridges2 = graph2.findBridges(0);
        
        if(bridges2.isEmpty()){
            System.out.println("There are no bridges in the provided exchange rates.");
        } else {
            System.out.println("The bridges of the provided exchange rates are: ");
            for (Edge bridge : bridges2) {
                System.out.println(bridge);
            }
        }
    }
}
