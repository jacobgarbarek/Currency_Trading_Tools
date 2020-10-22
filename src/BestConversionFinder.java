
import java.util.InputMismatchException;
import java.util.Scanner;

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
        String[] currencies = new String[]{"AUD","EUR","MXN","NZD","USD"};
        double[][] exchangeRates = new double[][]{
            {1.0000, 0.5966, 0.0000, 1.0694, 0.7077 },
            {1.6751, 1.0000, 0.0000, 1.7919, 1.1861 },
            {0.0000, 0.0000, 1.0000, 0.0000, 0.0474 },
            {0.9348, 0.5579, 0.0000, 1.0000, 0.6616 },
            {1.4125, 0.8431, 21.0496, 1.5110, 1.0000 }
        };
        
        CurrencyGraph<String> graph = new CurrencyGraph<String>(currencies, exchangeRates);
        System.out.println("Exchange Rates (21 Oct 2020)");
        System.out.println(graph);
        
        boolean inputCorrect = false;
        Scanner input = new Scanner(System.in);
        int startingIndex = 0;
        
        do{
            try{
                System.out.print("Enter a starting currency index (e.g. 0 for AUD): ");
                startingIndex = input.nextInt();
                inputCorrect = true;
            }catch(InputMismatchException ex){
                System.out.println("Please input a number.");
                inputCorrect = false;
                input.nextLine();
            }
            
            if(inputCorrect && (startingIndex < 0 || startingIndex >= currencies.length)){
                System.out.println("Please enter a valid index.");
                inputCorrect = false;
            }
        }while(!inputCorrect);
        
        inputCorrect = false;
        int endingIndex = 0;
        
        do{
            try{
                System.out.print("Enter a ending currency index (e.g. 1 for EUR): ");
                endingIndex = input.nextInt();
                inputCorrect = true;
            }catch(InputMismatchException ex){
                System.out.println("Please input a number.");
                inputCorrect = false;
                input.nextLine();
            }
            
            if(inputCorrect && startingIndex == endingIndex){
                System.out.println(currencies[startingIndex]+" is already your starting index.");
                inputCorrect = false;
            }else if(inputCorrect){
                if (endingIndex < 0 || endingIndex >= currencies.length) {
                    System.out.println("Please enter a valid index.");
                    inputCorrect = false;
                }
            }
        }while(!inputCorrect);
        
        ShortestPathResult<String> sp = graph.getShortestPaths(startingIndex, endingIndex);
        
        if(sp != null){
            System.out.println("\nThe optimal conversion path from " + currencies[startingIndex] + " to " + currencies[endingIndex] + " is:");

            if (sp.getConversionRate() <= exchangeRates[startingIndex][endingIndex]) //sometimes due to rounding path may have middle exchange that thinks it's path is better (although it's the same)
            {
                System.out.println("(" + currencies[startingIndex] + "-" + currencies[endingIndex] + ")" + " = " + exchangeRates[startingIndex][endingIndex]);
            } else {
                System.out.println(sp);
            }
        } else{
            System.out.println("An optimal conversion could not be found as there is an arbitrage opportunity.");
        }
        
    }
}
