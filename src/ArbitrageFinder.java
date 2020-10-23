
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
        
        Map<Vertex<String>,String> arbitragePaths = graph.getArbitrage();
        
        if(!arbitragePaths.isEmpty()){
            System.out.println("Current arbitrage opportunities: ");
            for(String s: arbitragePaths.values())
                System.out.println(s);
        }else
            System.out.println("There are no current arbitrage opportunities.");
        
        String[] currencies2 = new String[]{"AUD","EUR","MXN","NZD","USD", "CNY", "BDT", "NPR", "PEN", "RUB", "EGP"};
        double[][] rates = new double[11][11];

        //conversions added row -> col
        rates[0][0] = 1.0; //AUD - AUD
        rates[0][1] = 0.6056; // AUD - EURO
        rates[0][3] = 1.0733; // AUD - NZD;
        rates[0][4] = 0.7097;// AUD - USD;
        rates[0][5] = 4.7765;// AUD - CNY

        rates[1][0] = 1.6508; //EUR - AUD;
        rates[1][1] = 1.0; //EUR - EURO;
        rates[1][3] = 1.7719; //EUR - NZD
        rates[1][4] = 1.1718; //EUR - USD
        rates[1][5] = 7.8864; //EUR - CNY
        rates[1][9] = 91.4132; //EUR - RUB
        rates[1][10] = 11.7174; //EUR - EGP

        rates[2][2] = 1.0; //MXN - MXN
        rates[2][4] = 0.0467; //MXN - USD
        rates[2][8] = 0.1685; // MXN - PEN

        rates[3][0] = 0.9314; //NZD - AUD
        rates[3][1] = 0.5641; //NZD - EUR
        rates[3][3] = 1.0; //NZD - NZD
        rates[3][4] = 0.6611; // NZD - USD
        rates[3][5] = 4.4496; // NZD - CNY
         
        rates[4][0] = 1.4086; //USD - AUD
        rates[4][1] = 0.8532; //USD - EUR
        rates[4][2] = 21.3704; //USD - MXN
        rates[4][3] = 1.5120; //USD - NZD
        rates[4][4] = 1.0; //USD - USD;
        rates[4][5] = 6.7297; //USD - CNY
        rates[4][8] = 3.6022; //USD - PEN
        rates[4][9] = 78.006; //USD - RUB

        rates[5][0] = 0.2091; //CNY - AUD
        rates[5][1] = 0.1266; //CNY - EUR
        rates[5][3] = 0.2245; //CNY - NZD
        rates[5][4] = 0.1484; //CNY - USD
        rates[5][5] = 1.0; //CNY - CNY
        rates[5][6] = 12.3629; //CNY - BDT
        rates[5][7] = 17.2090; //CNY - NPR
        rates[5][9] = 11.5818; //CNY - RUB
        
        rates[6][5] = 0.0779; //BDT - CNY
        rates[6][6] = 1.0; //BDT - BDT
        rates[6][7] = 1.3425; //BDT - NPR
        
        rates[7][5] = 0.0566; //NPR - CNY
        rates[7][6] = 0.7009; //NPR - BDT
        rates[7][7] = 1.0; //NPR - NPR
        
        rates[8][2] = 5.9867; //PEN - MXN
        rates[8][4] = 0.2801; // PEN - USD
        rates[8][8] = 1.0;//PEN - PEN
        
        rates[9][1] = 0.0109;//RUB - EUR
        rates[9][4] = 0.0128;//RUB - USD
        rates[9][5] = 0.0862;//RUB - CNY
        rates[9][9] = 1.0;//RUB - RUB
        
        rates[10][1] = 0.1383;// EGP - EUR
        
        System.out.println("**********************************************************************************************");
        CurrencyGraph<String> graph2 = new CurrencyGraph<String>(currencies2, rates);
        System.out.println("Exchange Rates TEST 2");
        System.out.println(graph2);
        
        Map<Vertex<String>,String> arbitragePaths2 = graph2.getArbitrage();
        
        if(!arbitragePaths2.isEmpty()){
            System.out.println("Current arbitrage opportunities: ");
            for(String s: arbitragePaths2.values())
                System.out.println(s);
        }else
            System.out.println("There are no current arbitrage opportunities.");
    }
}
