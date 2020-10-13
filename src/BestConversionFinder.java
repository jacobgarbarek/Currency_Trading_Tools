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
        String[] currencies = new String[]{"NZD","AUD","EUR","USD","GBP"};
        float[][] exchangeRates = new float[][]{
            {0f, 0.92f, 0.56f, 0.66f, 0.51f },
            {1.08f, 0f, 0.61f, 0.7f, 0.55f },
            {1.78f, 1.64f, 0, 1.18f, 0.90f },
            {1.51f, 1.39f, 0.85f, 0, 0.77f },
            {1.97f, 1.82f, 1.11f, 1.30f, 0 }
        };
        
        GraphADT<String> graph = new CurrencyGraph<String>(currencies, exchangeRates);
        
        System.out.println(graph);
        
        
    }
}
