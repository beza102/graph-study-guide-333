import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Practice {

  /**
   * Returns the count of vertices with odd values that can be reached from the given starting vertex.
   * The starting vertex is included in the count if its value is odd.
   * If the starting vertex is null, returns 0.
   *
   * Example:
   * Consider a graph where:
   *   5 --> 4
   *   |     |
   *   v     v
   *   8 --> 7 < -- 1
   *   |
   *   v
   *   9
   * 
   * Starting from 5, the odd nodes that can be reached are 5, 7, and 9.
   * Thus, given 5, the number of reachable odd nodes is 3.
   * @param starting the starting vertex (may be null)
   * @return the number of vertices with odd values reachable from the starting vertex
   */
  public static int oddVertices(Vertex<Integer> starting) {
    if(starting == null) return 0;

    //set for visited
    Set<Vertex<Integer>> visited = new HashSet<>();
    return oddVertices(starting, visited);
  }
  public static int oddVertices(Vertex<Integer> vertex , Set<Vertex<Integer>> visited){

    if(visited.contains(vertex)) return 0;

      //mark visited
    visited.add(vertex);
    

    //odd
    int count =0;
    if (vertex.data != null && vertex.data % 2 != 0) {
      count =1;
    }

    //look at neighbor
    for(Vertex<Integer> neighbor : vertex.neighbors){
      count = count + oddVertices(neighbor, visited);
    }

    return count;
  }

  /**
   * Returns a *sorted* list of all values reachable from the starting vertex (including the starting vertex itself).
   * If duplicate vertex data exists, duplicates should appear in the output.
   * If the starting vertex is null, returns an empty list.
   * They should be sorted in ascending numerical order.
   *
   * Example:
   * Consider a graph where:
   *   5 --> 8
   *   |     |
   *   v     v
   *   8 --> 2 <-- 4
   * When starting from the vertex with value 5, the output should be:
   *   [2, 5, 8, 8]
   *
   * @param starting the starting vertex (may be null)
   * @return a sorted list of all reachable vertex values by 
   */
  public static List<Integer> sortedReachable(Vertex<Integer> starting) {
    //if null return empty list
    if(starting == null){
      return new ArrayList<>();
    }
    
    List<Integer> values = new ArrayList<>();

    //set for visited
    Set<Vertex<Integer>> visited = new HashSet<>();

    sortedReachable(starting, visited, values);

    Collections.sort(values);



    //stack for DFS
    return values;

  }
  public static void sortedReachable(Vertex<Integer> vertex, Set<Vertex<Integer>> visited, List<Integer> values ){
    if(visited.contains(vertex)){
      return;
    }

    visited.add(vertex);
    if(vertex.data != null){
      values.add(vertex.data);
    }

    for(Vertex<Integer> neighbor : vertex.neighbors){
      sortedReachable(neighbor, visited, values);
    }
  }


  /**
   * Returns a sorted list of all values reachable from the given starting vertex in the provided graph.
   * The graph is represented as a map where each key is a vertex and its corresponding value is a set of neighbors.
   * It is assumed that there are no duplicate vertices.
   * If the starting vertex is not present as a key in the map, returns an empty list.
   *
   * @param graph a map representing the graph
   * @param starting the starting vertex value
   * @return a sorted list of all reachable vertex values
   */
  public static List<Integer> sortedReachable(Map<Integer, Set<Integer>> graph, int starting) {
    if(!graph.containsKey(starting)){
      return new ArrayList<>();
    }
    
   //Set for visited 
    Set<Integer> visited = new HashSet<>();
    //for neghbors
    List<Integer> result = new ArrayList<>();
    sortedReachable(graph, starting, visited, result);

    Collections.sort(result);

    return result;

  }

  public static void sortedReachable(Map<Integer, Set<Integer>> graph, int current, Set<Integer> visited, List<Integer> result){
    if(visited.contains(current)){
      return;
    }

    //mark visited
    visited.add(current);

    result.add(current);

    Set<Integer> neighbors = graph.get(current);

    if(neighbors != null){
      for(int neighbor : neighbors){
        sortedReachable(graph, neighbor, visited, result);

      }
    }
  }

  /**
   * Returns true if and only if it is possible both to reach v2 from v1 and to reach v1 from v2.
   * A vertex is always considered reachable from itself.
   * If either v1 or v2 is null or if one cannot reach the other, returns false.
   *
   * Example:
   * If v1 and v2 are connected in a cycle, the method should return true.
   * If v1 equals v2, the method should also return true.
   *
   * @param <T> the type of data stored in the vertex
   * @param v1 the starting vertex
   * @param v2 the target vertex
   * @return true if there is a two-way connection between v1 and v2, false otherwise
   */
  public static <T> boolean twoWay(Vertex<T> v1, Vertex<T> v2) {
    //if null false
    if(v1 == null || v2 == null){
      return false;
    }

    //if same return true
    if(v1 == v2){
      return true;
    }
    
    //check  v2 rechable from v1
    boolean v1ReachableV2 = twoWay(v1, v2, new HashSet<>());
    if(!v1ReachableV2){
      return false;
    }

    //cheack v1 rechable from v2
    boolean v2ReachableV1 = twoWay(v2, v1, new HashSet<>());

    return v1ReachableV2 && v2ReachableV1;
  }

  public static<T> boolean twoWay(Vertex<T> start, Vertex<T> target, Set<Vertex<T>> visited ){
    if(start == target){
      return true;
    }

    //mark visited
    visited.add(start);

    for(Vertex<T> neighbor : start.neighbors){
      if(twoWay(neighbor, target, visited)){
        return true;
      }
    }

    return false;
  }

  /**
   * Returns whether there exists a path from the starting to ending vertex that includes only positive values.
   * 
   * The graph is represented as a map where each key is a vertex and each value is a set of directly reachable neighbor vertices. A vertex is always considered reachable from itself.
   * If the starting or ending vertex is not positive or is not present in the keys of the map, or if no valid path exists,
   * returns false.
   *
   * @param graph a map representing the graph
   * @param starting the starting vertex value
   * @param ending the ending vertex value
   * @return whether there exists a valid positive path from starting to ending
   */
  public static boolean positivePathExists(Map<Integer, Set<Integer>> graph, int starting, int ending) {
    if(!graph.containsKey(starting) ||!graph.containsKey(ending)){
      return false;
    }

    //check if not positive
    if(starting <=0 || ending <0){
      return false;
    }

    Set<Integer> visited = new HashSet<>();
    return positivePathExists(graph, starting, ending, visited);
    
  }

  public static boolean positivePathExists(Map<Integer, Set<Integer>> graph, int current, int ending, Set<Integer> visited){
    if(current == ending){
      return true;
    }

    //current is negative or been visited
    if(current <=0 || visited.contains(current)){
      return false;
    }

    visited.add(current);

    //get neighbors
    Set<Integer> neighbors = graph.get(current);

    if(neighbors != null){
      for(int neighbor : neighbors){
        if(positivePathExists(graph, neighbor, ending, visited)){
          return true;
        }
      }
    }

    return false;

  }

  /**
   * Returns true if a professional has anyone in their extended network (reachable through any number of links)
   * that works for the given company. The search includes the professional themself.
   * If the professional is null, returns false.
   *
   * @param person the professional to start the search from (may be null)
   * @param companyName the name of the company to check for employment
   * @return true if a person in the extended network works at the specified company, false otherwise
   */
  public static boolean hasExtendedConnectionAtCompany(Professional person, String companyName) {
    if(person == null){
      return false;
    }  
    
    Set<Professional> visited = new  HashSet<>();

    return hasExtendedConnectionAtCompany(person, companyName, visited);
  }

  public static boolean hasExtendedConnectionAtCompany(Professional current, String companyName, Set<Professional> visited){
    //skip if visited
    if(visited.contains(current)){
      return false;
    }

    visited.add(current);

    //check if they work i the company
    if(current.getCompany().equals(companyName)){
      return true;
    }

    for(Professional connection : current.getConnections()){
      if(hasExtendedConnectionAtCompany(connection, companyName, visited)){
        return true;
      }
    }
    return false;
  }

  /**
   * Returns a list of possible next moves starting from a given position.
   * 
   * Starting from current, which is a [row, column] location, a player can move 
   * by one according to the directions provided.
   * 
   * The directions are given as a 2D array, where each inner array is a [row, column]
   * pair that describes a move. For example, if given the below array it would be possible
   * to move to the right, up, down, or down/left diagonally.
   * {
   *  {0, 1},  // Right
   *  {-1, 0}, // Up
   *  {1, 0},  // Down
   *  {1, -1}  // Down/left diagonal
   * }
   * 
   * However, the player can not move off the edge of the board, or onto any 
   * location that has an 'X' (capital X).
   * 
   * The possible moves are returned as a List of [row, column] pairs. The List
   * can be in any order.
   * 
   * Example:
   * 
   * board: 
   * {
   *  {' ', ' ', 'X'},
   *  {'X', ' ', ' '},
   *  {' ', ' ', ' '}
   * }
   * 
   * current:
   * {1, 2}
   * 
   * directions:
   * {
   *  {0, 1},  // Right
   *  {-1, 0}, // Up
   *  {1, 0},  // Down
   *  {1, -1}  // Down/left diagonal
   * }
   * 
   * expected output (order of list is unimportant):
   * [{2, 2}, {2, 1}]
   * 
   * Explanation:
   * The player starts at {1, 2}.
   * The four directions the player might have to go are right, up, down, and down/left (based on the directions array).
   * They cannot go right because that would go off the edge of the board.
   * They cannot go up because there is an X.
   * They can go down.
   * They can go down/left.
   * The resultant list has the two possible positions.
   * 
   * 
   * You can assume the board is rectangular.
   * You can assume valid input (no nulls, properly sized arrays, current is in-bounds,
   * directions only move 1 square, any row/column pairs are arrays of length 2,
   * directions are unique).
   * 
   * If there are no possible moves, the method returns an empty list.
   * 
   * @param board a rectangular array where 'X' represent an impassible location
   * @param current the [row, column] starting position of the player
   * @param directions an array of [row, column] possible directions
   * @return an unsorted list of next moves
   */
  public static List<int[]> nextMoves(char[][] board, int[] current, int[][] directions) {
    List<int[]> result = new ArrayList<>();

    //total of row and col in border
    int row = board.length;

    int col = board[0].length;

    //loop through the direction
    for(int[] direction : directions){
      int  newR = current[0] +direction[0];
      int newC = current[1] + direction[1];

      //cheack if its inside border
      if(newR >= 0 && newR<row && newC >= 0 && newC < col ){

        //check blocked by x
        if(board[newR][newC] != 'X'){

          //add to result
          result.add(new int[]{newR, newC});
        }
      }
    }
    return result;
  }
}
