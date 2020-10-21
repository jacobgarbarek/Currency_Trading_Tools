
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
public class ShortestPathResult<E> {
    private Vertex<E> source;
    private Vertex<E> destination;
    private Map<Vertex<E>,Edge<E>> shortestPathEdges;
    private Map<Vertex<E>,Set<Edge<E>>> adjacencyLists;
    private ArrayList<Vertex<E>> vertexList;
    private Map<Edge<E>,Double> weights;
    private boolean arbitrage;
    private String path;
    private double conversionRate;
    private String arbitragePath;
    private AllPairsFloydWarshall apfw;
    
    public ShortestPathResult(Map<Vertex<E>,Edge<E>> shortestPathEdges, Map<Edge<E>,Double> weights, boolean arbitrage, Vertex<E> source, Vertex<E> destination){
        this(shortestPathEdges, weights, arbitrage, source, destination, null, null);
    }
    
    public ShortestPathResult(Map<Vertex<E>,Edge<E>> shortestPathEdges, Map<Edge<E>,Double> weights, boolean arbitrage, Vertex<E> source, Vertex<E> destination, Map<Vertex<E>,Set<Edge<E>>> adjacencyLists, ArrayList<Vertex<E>> vertexList){
        this.shortestPathEdges = shortestPathEdges;
        this.weights = weights;
        this.arbitrage = arbitrage;
        this.source = source;
        this.destination = destination;
        this.adjacencyLists = adjacencyLists;
        this.vertexList = vertexList;
        
        if(arbitrage){
            double[][] weightsTable = new double[vertexList.size()][vertexList.size()];
            
            for(int i=0;i<weightsTable.length;i++){
                Set<Edge<E>> edges = adjacencyLists.get(vertexList.get(i));
                for(int j=0;j<weightsTable[i].length;j++){
                    weightsTable[i][j] = Double.POSITIVE_INFINITY;
                }
                for(Edge<E> e : edges){
                    Vertex<E>[] endVertices = e.endVertices();
                    weightsTable[i][vertexList.indexOf(endVertices[1])] = weights.get(e);
                }
            }
            
            this.apfw = new AllPairsFloydWarshall(weightsTable);
            this.arbitragePath = getArbitragePath(vertexList.indexOf(source), apfw.getD(), apfw.getP(), apfw.getN());
            if(arbitragePath == null)
                arbitrage = false;
        }else
            calculateConversionRateAndPath();
    }
    
    public String getArbitragePath(int indexOf, double[][][] d, int[][][] p, int n) {
        String path = null;

        if(d[n][indexOf][indexOf] < 0){
            int prevStep = p[n][indexOf][indexOf];
            path = vertexList.get(indexOf)+")";
            do{
                path = vertexList.get(prevStep)+"-"+ path;
                prevStep = p[n][indexOf][prevStep];
            }while(p[n][indexOf][prevStep] != indexOf);
            path = "("+path;
        }
        
        return path;
    }
    
    public String getAb(){
        return arbitragePath;
    }
    
    public Map<Vertex<E>,Edge<E>> getShortestPathEdges(){
        return shortestPathEdges;
    }
    
    public boolean isArbitrage(){
        return arbitrage;
    }
    
    public double getConversionRate(){
        return conversionRate;
    }
    
    private void calculateConversionRateAndPath(){
        path = "";
        conversionRate = 0;
        boolean pathFound = false;
        Edge<E> edge = shortestPathEdges.get(destination);
        
        
        while(!pathFound){
            path = edge + path;
            conversionRate += weights.get(edge);
            Vertex<E>[] endVertices = edge.endVertices();
            
            if(endVertices[0].equals(source))
                pathFound = true;
            else{
                edge = shortestPathEdges.get(endVertices[0]);
            }
        }
        
        conversionRate = (1/Math.exp(conversionRate));
        BigDecimal bd = new BigDecimal(Double.toString(conversionRate));
        bd = bd.setScale(4, RoundingMode.HALF_EVEN);
        conversionRate = bd.doubleValue();
        path += " = " + conversionRate;
    }
    
    @Override
    public String toString(){
        return path;
    }
}
