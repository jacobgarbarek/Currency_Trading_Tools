import java.util.ArrayList;
import java.util.HashSet;

/**
   A class that demonstrates the Floyd-Warshall algorithm for solving
   the all-pairs shortest paths problem in O(n^3). Class was adapted by Jacob Garbarek
   and Angelo Ryndon to handle arbitrage opportunities between exchange rates.
   @author Andrew Ensor (Adapted by Jacob Garbarek (ID: 17980551) & Angelo Ryndon (ID: 18028033)
*/

public class AllPairsFloydWarshall
{
   private static final double INFINITY = Double.POSITIVE_INFINITY;
   private static final int NO_VERTEX = -1;
   private int n; // number of vertices in the graph
   private double[][][] d; //d[k][i][i] is weight of path from v_i to v_j
   private int[][][] p; //p[k][i][i] is penultimate vertex in path
   private ArrayList<Double> arbitrageExchanges;
   private ArrayList<ArrayList<Integer>> arbitragePaths;
   
   public AllPairsFloydWarshall(double[][] weights)
   {
      arbitragePaths = new ArrayList<>();
      arbitrageExchanges = new ArrayList<>();
      n = weights.length;
      d = new double[n+1][][];
      d[0] = weights;
      // create p[0]
      p = new int[n+1][][];
      p[0] = new int[n][n];
      for (int i=0; i<n; i++)
      {  for (int j=0; j<n; j++)
         {  if (weights[i][j]<INFINITY)
               p[0][i][j] = i;
            else
               p[0][i][j] = NO_VERTEX;
         }
      }
      
      // build d[1],...,d[n] and p[1],...,p[n] dynamically
      for (int k=1; k<=n; k++)
      {  d[k] = new double[n][n];
         p[k] = new int[n][n];
         for (int i=0; i<n; i++)
         {  for (int j=0; j<n; j++)
            {  double s;
               if (d[k-1][i][k-1]!=INFINITY&&d[k-1][k-1][j]!=INFINITY)
                  s = d[k-1][i][k-1] + d[k-1][k-1][j];
               else
                  s = INFINITY;
               
               if (d[k-1][i][j] <= s)
               {                   
                  d[k][i][j] = d[k-1][i][j];
                  p[k][i][j] = p[k-1][i][j];
               }
               else
               {  
                  d[k][i][j] = s;
                  p[k][i][j] = p[k-1][k-1][j];
               }
               }
            }
         recordArbitragePaths(k);                       //arbitrage paths are recorded as more vertices are allowed to be used in shortest path
       }
   }
   
   // returns a string representation of matrix d[n] and p[n]
   public String toString()
   {  String output = "Shortest lengths\n";
      for (int i=0; i<n; i++)
      {  for (int j=0; j<n; j++)
         {  if (d[n][i][j] != INFINITY)
               output += ("\t" + d[n][i][j]);
            else
               output += "\tinfin";
         }
         output += "\n";
      }
      output += "Previous vertices on shortest paths\n";
      for (int i=0; i<n; i++)
      {  for (int j=0; j<n; j++)
         {  if (p[n][i][j] != NO_VERTEX)
               output += ("\t" + p[n][i][j]);
            else
               output += "\tnull";
         }
         output += "\n";
      }
      
      return output;
   }
    
    public ArrayList<ArrayList<Integer>> getArbitragePaths(){
        return arbitragePaths;
    }
    
    public ArrayList<Double> getArbitrageExchanges(){
        return arbitrageExchanges;
    }

    private void recordArbitragePaths(int current) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j && d[current][i][j] != INFINITY) {
                    if(d[current][i][j] < 0){                                               //arbitrage
                        ArrayList<Integer> tempPath = new ArrayList<Integer>();
                        tempPath.add(i);                                                    //adds end currency index
                        HashSet<Integer> duplicateChecker = new HashSet<>();                //prevents being stuck within inner arbitrage loop
                        
                        int pathIndex = p[current][i][j];
                        
                        do {
                            tempPath.add(pathIndex);                                        
                            pathIndex = p[current][i][pathIndex];
                        } while (pathIndex != i && duplicateChecker.add(pathIndex));        //ends when back to original currency or inner arbitrage is located
                        
                        tempPath.add(i);                                                    //adds start currency index

                        if(pathIndex == i && !arbitragePaths.contains(tempPath)){           //complete arbitrage path and path is not already recorded
                            arbitragePaths.add(tempPath);
                            arbitrageExchanges.add(d[current][i][j]);
                                  
                            if (tempPath.size() == 3) {                                     //can instantly get inverse arbitrage path between two currencies
                                ArrayList<Integer> oppositeWay = new ArrayList<Integer>();
                                oppositeWay.add(tempPath.get(1));
                                oppositeWay.add(tempPath.get(0));
                                oppositeWay.add(tempPath.get(1));
                                
                                arbitragePaths.add(oppositeWay);
                                arbitrageExchanges.add(d[current][i][j]);
                            }
                        }
                    }
                }
            }
        }
    }
}
