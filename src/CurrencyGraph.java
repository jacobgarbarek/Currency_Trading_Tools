/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jacob
 * @param <E>
 */
public class CurrencyGraph<E> extends AdjacencyListGraph<E>{
    private float[][] exchangeRates;
    
    public CurrencyGraph(float[][] exchangeRates){
        super();
        this.exchangeRates = exchangeRates;
    }
    
    
}
