/**
 * Created by murshid on 4/1/17.
 * 2014191 w1583002 Mohamed Murshid Hassen
 * Node Class for a Grid
 * Each Grid in the Node class is a Type of Node
 */
public class Node {

    public int x;               // X axis in the Grid
    public int y;               // Y axis in the Grid
    Node parentReference;       // parent Node

    public int gcost;           // General-Cost During Moving all Four Directions
    public int hcost=0;         // Heuristic Cost From the target Node
    public int fCost =0;     // Final Cost for the Movement

    // Initializing the Node Constructor Using the Path
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString(){
        return "["+this.x+", "+this.y+"]";
    }
}
