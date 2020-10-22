
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
    private Map<Edge<E>,Double> weights;
    private String path;
    private double conversionRate;
    
    public ShortestPathResult(Map<Vertex<E>,Edge<E>> shortestPathEdges, Map<Edge<E>,Double> weights, Vertex<E> source, Vertex<E> destination){
        this.shortestPathEdges = shortestPathEdges;
        this.weights = weights;
        this.source = source;
        this.destination = destination;
        calculateConversionRateAndPath();
    }
    
    public Map<Vertex<E>,Edge<E>> getShortestPathEdges(){
        return shortestPathEdges;
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
