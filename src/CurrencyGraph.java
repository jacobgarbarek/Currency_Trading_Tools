
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
public class CurrencyGraph<E> extends AdjacencyListGraph<E>{
    private double[][] exchangeRates;
    private String[] currencies;
    private ArrayList<Vertex> vertexList;
    private Map<Edge<E>,Double> weights;
    
    public CurrencyGraph(String[] currencies, double[][] exchangeRates){
        super(GraphType.DIRECTED);
        this.currencies = currencies;
        this.exchangeRates = exchangeRates;
        weights = new HashMap<Edge<E>,Double>();
        vertexList = new ArrayList<>();
        calculateAdjacencyList();
    }

    private void calculateAdjacencyList() {
        for(String currency : currencies)
            vertexList.add(this.addVertex((E)currency));
        
        for(int i=0;i<exchangeRates.length;i++){
            for(int j=0;j<exchangeRates[i].length;j++){
                double exchangeRate = exchangeRates[i][j];
                
                if(exchangeRate != 0 && i != j){
                    BigDecimal bd = new BigDecimal(Double.toString(Math.log(1/exchangeRate)));
                    bd = bd.setScale(4, RoundingMode.HALF_UP);
                    exchangeRate = bd.doubleValue();
                    Edge<E> edge = this.addEdge(vertexList.get(i), vertexList.get(j));
                    weights.put(edge, exchangeRate);
                }
            }
        }
    }
    
    public ShortestPathResult getShortestPaths(int sourceIndex, int destinationIndex){
        Vertex<E> source = vertexList.get(sourceIndex);
        Vertex<E> destination = vertexList.get(destinationIndex);
        Map<Vertex<E>,Edge<E>> leastEdges = new HashMap<Vertex<E>,Edge<E>>();
        Map<Vertex<E>,Double> shortestPathEstimates = new HashMap<Vertex<E>,Double>();
        
        for (Vertex<E> vertex : this.vertexSet()){
            shortestPathEstimates.put((Vertex<E>) vertex, Double.POSITIVE_INFINITY);
            leastEdges.put((Vertex<E>) vertex, null);
        }
        
        shortestPathEstimates.put(source, new Double(0));
        
        for(int i=1;i<this.vertexSet().size();i++){
            for (Edge<E> edge : this.edgeSet()){
                Vertex<E>[] endVertices = edge.endVertices();
                Double du = shortestPathEstimates.get(endVertices[0]);
                
                if(du < Float.POSITIVE_INFINITY){
                    if (du + weights.get(edge) < shortestPathEstimates.get(endVertices[1])) {
                        double duW = du + weights.get(edge);
                        
                        BigDecimal bd = new BigDecimal(Double.toString(duW));
                        bd = bd.setScale(4, RoundingMode.HALF_UP);
                        duW = bd.doubleValue();
                        shortestPathEstimates.put(endVertices[1], duW);
                        leastEdges.put(endVertices[1], edge);
                    }
                }
            }
        }
        
        for (Edge<E> edge : this.edgeSet()){
            Vertex<E>[] endVertices = edge.endVertices();
            Double du = shortestPathEstimates.get(endVertices[0]);
             
            if(du < Float.POSITIVE_INFINITY){
                if (du + weights.get(edge) < shortestPathEstimates.get(endVertices[1])) {
                    System.out.println("Arbitrage");
                    return null;        //handle this
                }
            }
        }
        
        return new ShortestPathResult(leastEdges, weights, source, destination);
    }
    
    @Override
    public String toString()
   {  String output = "";
      for (int i=0; i<currencies.length; i++){
          output += "("+i+") "+currencies[i] + " : ";
          for(int j=0; j<exchangeRates[i].length;j++){
              if(j != i)
                output += exchangeRates[i][j] + "["+currencies[j]+"], ";
          }
          output += "\n";
      }
      
      output += "\nEdge Weights\n";
      
      for (Vertex<E> vertex : vertices){
         output += vertex + ": ";
         Set<Edge<E>> e = adjacencyLists.get(vertex);
         
         for(Edge<E> edge : e){
             output += weights.get(edge) + "" + edge+", ";
         }
         output += "\n";
      }
      
      return output;
   }

    public Map<Vertex<E>,String> getArbitrage() {
        double[][] weightsTable = new double[vertexList.size()][vertexList.size()];

        for (int i = 0; i < weightsTable.length; i++) {
            Set<Edge<E>> edges = adjacencyLists.get(vertexList.get(i));
            for (int j = 0; j < weightsTable[i].length; j++) {
                weightsTable[i][j] = Double.POSITIVE_INFINITY;
            }
            for (Edge<E> e : edges) {
                Vertex<E>[] endVertices = e.endVertices();
                weightsTable[i][vertexList.indexOf(endVertices[1])] = weights.get(e);
            }
        }
        
        AllPairsFloydWarshall apfw = new AllPairsFloydWarshall(weightsTable);
        System.out.println(apfw);
        double[][][] d = apfw.getD();
        int[][][] p = apfw.getP();
        int n = apfw.getN();
        
        Map<Vertex<E>,String> aPaths = new HashMap<>();
        HashSet<Vertex<E>> duplicateChecker = new HashSet<>();
        String path = null;
        
        for(int i=0;i<vertexList.size();i++){
            double conversionRate = 0;
            if(d[n][i][i] < 0){                             //arbitrage opportunity exists
                boolean innerClosedPath = false;
                int prevStep = p[n][i][i];
                path = vertexList.get(i) + ")";
                int finalPointingIndex = i;
                
                do {
                    Vertex<E> prevV = vertexList.get(prevStep);
                    
                    if(!duplicateChecker.add(prevV))
                        innerClosedPath = true;
                    else{
                        Set<Edge<E>> edges = adjacencyLists.get(prevV);
                        
                        for (Edge<E> e : edges) {
                            Vertex<E>[] endVertices = e.endVertices();
                            if(endVertices[1].equals(vertexList.get(finalPointingIndex))){
                                conversionRate += weights.get(e);
                                break;
                            }
                        }
                        path = vertexList.get(prevStep) + "-" + path;
                        finalPointingIndex = prevStep;
                        prevStep = p[n][i][prevStep];
                    }
                } while (!innerClosedPath && p[n][i][prevStep] != i);
                
                if(!innerClosedPath){
                    conversionRate = (1 / Math.exp(conversionRate));
                    BigDecimal bd = new BigDecimal(Double.toString(conversionRate));
                    bd = bd.setScale(4, RoundingMode.HALF_EVEN);
                    conversionRate = bd.doubleValue();
                    path = "(" + path + " = "+conversionRate;
                    aPaths.put(vertexList.get(i), path);
                }
                
                duplicateChecker.clear();
                path = "";
            }
        }
        
        return aPaths;
    }
}
