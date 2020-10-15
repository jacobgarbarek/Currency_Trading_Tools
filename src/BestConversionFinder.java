/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jacob
 */
public class BestConversionFinder {
    public static void main(String[] args){
        /*String[] currencies = new String[]{"NZD","AUD","EUR","USD","GBP"};
        float[][] exchangeRates = new float[][]{
            {1f, 0.92f, 0.56f, 0.66f, 0.51f },
            {1.08f, 1f, 0.61f, 0.7f, 0.55f },
            {1.78f, 1.64f, 1f, 1.18f, 0.90f },
            {1.51f, 1.39f, 0.85f, 1f, 0.77f },
            {1.97f, 1.82f, 1.11f, 1.30f, 1f }
        };
        
        CurrencyGraph<String> graph = new CurrencyGraph<String>(currencies, exchangeRates);
        
        System.out.println(graph);*/
        
        String[] currencies = new String[]{"AUD","EUR","MXN","NZD","USD"};
        float[][] exchangeRates = new float[][]{
            {1f, 0.61f, 0f, 1.08f, 0.72f },
            {1.64f, 1f, 0f, 1.77f, 1.18f },
            {0f, 0f, 1f, 0f, 0.047f },
            {0.92f, 0.56f, 0f, 1f, 0.67f },
            {1.39f, 0.85f, 21.19f, 1.5f, 1f }
        };
        
        CurrencyGraph<String> graph = new CurrencyGraph<String>(currencies, exchangeRates);
        System.out.println(graph);
        
        System.out.println("Finding best conversion rate: ");
        ShortestPathResult<String> sp = graph.getShortestPaths(1, 0);
        System.out.println(sp);
        for (Edge<String> edge : sp.getShortestPathEdges().values()){
          if(edge != null)
              System.out.print(" "+edge);
      }
    }
}
