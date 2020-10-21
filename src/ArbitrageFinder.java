/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jacob
 */
public class ArbitrageFinder {
    public static void main(String[] args){
        String[] currencies = new String[]{"AUD","EUR","MXN","NZD","USD"};
        double[][] exchangeRates = new double[][]{
            {1.0000, 0.6100, 0.0000, 1.0800, 0.7200 },
            {1.6400, 1.0000, 0.0000, 1.7700, 1.1800 },
            {0.0000, 0.0000, 1.0000, 0.0000, 0.0470 },
            {0.9200, 0.5600, 0.0000, 1.0000, 0.670 },
            {1.3900, 0.8500, 21.1900, 1.5000, 1.0000 }
        };
        CurrencyGraph<String> graph = new CurrencyGraph<String>(currencies, exchangeRates);
        System.out.println("Exchange Rates (21 Oct 2020)");
        System.out.println(graph);
        
        ShortestPathResult<String> sp = graph.getShortestPaths(3, 1);
        System.out.println(sp.getAb());
    }
}
