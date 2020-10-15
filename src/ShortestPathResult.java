
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
public class ShortestPathResult<E> {
    private Vertex<E> source;
    private Vertex<E> destination;
    private Map<Vertex<E>,Edge<E>> shortestPathEdges;
    private Map<Edge<E>,Float> weights;
    private boolean arbitrage;
    
    public ShortestPathResult(Map<Vertex<E>,Edge<E>> shortestPathEdges, Map<Edge<E>,Float> weights, boolean arbitrage, Vertex<E> source, Vertex<E> destination){
        this.shortestPathEdges = shortestPathEdges;
        this.weights = weights;
        this.arbitrage = arbitrage;
        this.source = source;
        this.destination = destination;
    }
    
    public Map<Vertex<E>,Edge<E>> getShortestPathEdges(){
        return shortestPathEdges;
    }
    
    public boolean isArbitrage(){
        return arbitrage;
    }
    
    @Override
    public String toString(){
        String output = "";
        boolean pathFound = false;
        Edge<E> edge = shortestPathEdges.get(destination);
        float exchange = 0;
        
        while(!pathFound){
            output = edge + output;
            exchange += weights.get(edge);
            Vertex<E>[] endVertices = edge.endVertices();
            
            if(endVertices[0].equals(source))
                pathFound = true;
            else{
                edge = shortestPathEdges.get(endVertices[0]);
            }
        }
        
        exchange = (float) (1/Math.exp(exchange));
        output += " = " + exchange;
        
        return output;
    }
}
