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
    }
}
