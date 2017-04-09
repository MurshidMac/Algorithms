
import java.util.*;

/**
 * w1583002 2014191
 * Created by murshid on 4/1/17.
 *Mohamed Murshid Hassen w1583002 2014191
* Algorithm to find the Shortest path between two given points in an Directed adjacency matrix
 * Creating A Weighted Graph to Calculate a MST (Minimum Spanning Tree of Type)
 * TO calculate the Path in which the Points can traverse through the tree
 * Basically It Uses A* star (Advance Breath-First Approach to calculate the Tree of Paths and Obstacles)
 */
public class GraphWeightedGrid {

    // Cost Calculation
    public static int costForMovingDiagonal = 14;       // Cost For Movement
    public static int vertical_heuristic_Cost = 10;     // Cost for Moving From a point
    // Data-Structures Used To Store Values
    Node[][] grid;                                      // Array of Type Node
    boolean[][] closed;                                 // Array of Type Boolean
    PriorityQueue<Node> queue;                           // Priority Queue to Store the Node in Priority Queue List
    // Initial Start of the Node
    public static int startNodeX = -1;                  // start of a Node
    public static int startNodeY = -1;                  // start J of a Node
    public static int endNodeX = -1, endNodeY = -1;     // End of a Node

    public boolean booleanpath = true;                  // This is initial Node Calculation
    int gridSizeX, gridSizeY;                           // These are two points to determine the Grid positions


    // Final Modified Constructor while Creating an Object of type
    public GraphWeightedGrid(int x, int y) {
        grid = new Node[x][y];                          // Size of the Initial Grid
        closed = new boolean[x][y];                     // Storing a Boolean arraay for Obstacles
    }


    //
    public void createBlockTree(int i, int j) {
        grid[i][j] = null;
    }

    public void initializeStartNode(int i, int j) {
        startNodeX = i;
        startNodeY = j;
    }

    public void initializeEndNode(int i, int j) {
        endNodeX = i;
        endNodeY = j;
    }


    // Initialize the Graph for the Boolean
    public void createGrid(boolean[][] random) {


        this.gridSizeX = random.length;  // This is size of the matrix taken N This is Square Matrix
        this.gridSizeY = random.length;  // This is the size of the matrix N

        //Lamda function which Does the Ordering to a priority Queue
        queue = new PriorityQueue<>((Object o1, Object o2) -> {
            //PriorityQueue will be ordered in this manner
            //We override the method compare in Java.Util.Comparator
            Node c1 = (Node) o1;
            Node c2 = (Node) o2;

            return c1.fCost < c2.fCost ? -1 :
                    c1.fCost > c2.fCost ? 1 : 0;
    /*       if (c1.fCost < c2.fCost)
               return -1;
           else if (c1.fCost > c2.fCost)
               return 1;
           else
               return 0;*/
        });

        //

        initializeStartNode(startNodeX, startNodeY);    // Initial Start Node
        initializeEndNode(endNodeX, endNodeY);          // Initial End Node

        for (int i = 0; i < gridSizeX; ++i) {
            for (int j = 0; j < gridSizeY; ++j) {
                grid[i][j] = new Node(i, j);
                grid[i][j].hcost = Math.abs(i - endNodeX) + Math.abs(j - endNodeY); // setting up the heuristic Cost for the Nodes
                if (i > j) {
                    grid[i][j].hcost = vertical_heuristic_Cost * j + 10 * (i - j);
                } else {
                    grid[i][j].hcost = vertical_heuristic_Cost * j + 10 * (i - j);
                }
            }
        }

        System.out.println(startNodeX + "," + startNodeY);
        grid[startNodeX][startNodeY].fCost = 0;             // Setting the Final Cost TO Zero

        for (int i = 0; i < gridSizeX; ++i) {               // Complexity of N
            for (int j = 0; j < gridSizeY; ++j) {           // Complexity of N
                if (random[i][j]) {
                    createBlockTree(i, j);                  // setting the Block coordinates
                }
            }
        }

        // Finding the Path Now Using the
        Astar();

        for (int i = 0; i < gridSizeX; ++i) {
            for (int j = 0; j < gridSizeY; ++j) {
                if (grid[i][j] != null) {
                    System.out.printf("%-3d", grid[i][j].fCost);
                } else {
                    System.out.print(" 0-Block");
                }
                System.out.println();
            }
        }
        System.out.println();

        if (closed[endNodeX][endNodeY]) {
            // Tracking the Reverse Path
            System.out.println("Path: ");   // // Printing the Path
            Node current = grid[endNodeX][endNodeY]; // Setting up a Type Node to A Grid
            System.out.println(current);    // Printing the Node

            System.out.println("N: " + gridSizeX);
            // Looping through the Current Node
            while (current.parentReference != null) {
                StdDraw.setPenRadius(0.005);
                switch (costForMovingDiagonal) {
                    case 10:
                        StdDraw.setPenColor(85, 173, 122);  // Setting the Pen Colours
                        break;
                    case 14:
                        StdDraw.setPenColor(30, 93, 136);
                        StdDraw.setPenRadius(0.0085);
                        StdDraw.filledCircle(current.x, current.y, 0.15);
                        break;
                    case 20:
                        StdDraw.setPenColor(35, 41, 46);
                        StdDraw.setPenRadius(0.008);
                        StdDraw.circle(current.x, current.y, 0.15);
                        break;
                }
                StdDraw.line(current.parentReference.x, current.parentReference.y, current.x, current.y);
                // if the Line is meant to be Diagonal Towards the left

                if (current.parentReference.x > current.x && current.parentReference.x != current.y) {
                    ////StdDraw.line(current.parent.i, current.parent.j + DRAW_RADIUS, current.i, current.j);
                } else if (current.parentReference.x < current.x && current.parentReference.x != current.y) {
                    //StdDraw.line(current.parent.i, current.parent.j - DRAW_RADIUS, current.i, current.j);
                }
                System.out.print(" -> " + current.parentReference);
                current = current.parentReference;

            }
            System.out.println();
        } else {
            System.out.println("No possible Path");
        }
    }

    private void Astar() {
        try {
            queue.add(grid[startNodeX][startNodeY]);
        } catch (NullPointerException e) {

        }
        Node current;

        // Rest of the Node are added
        while (true) {
            // Its a predifined funtion to get the value
            current = queue.poll();
            // if()
            if (current == null) break;// stops if the loops is in a break position

            closed[current.x][current.y] = true;

            if (current.equals(grid[endNodeX][endNodeY])) {
                return;
            }

            Node cell;

            try {
                // if the current cell has neighbour cells
                if (current.x - 1 >= 0) {
                    // cell in the first neighbouring Cell
                    cell = grid[current.x][current.y];
                    checkUpdate(current, cell, current.fCost + vertical_heuristic_Cost);


                    // if Both the Left and Bottom neighbouring
                    if (booleanpath && current.y - 1 >= 0) {
                        // Check and Update the Cost of the Bottom Left
                        cell = grid[current.x - 1][current.y - 1];
                        if (grid[current.x - 1][current.y] != null || grid[current.x][current.y - 1] != null)
                            checkUpdate(current, cell, current.fCost + costForMovingDiagonal);
                    }

                    // if the current cell has neibouring cell on top of it
                    if (booleanpath && current.y + 1 < grid[0].length) {
                        // Check and update the cost of the top Left neighobour cell
                        cell = grid[current.x - 1][current.y + 1];
                        // if BOTH the TOP and LEFT neobours are blocked we cannot travese through TOP LEFT
                        if (grid[current.x - 1][current.y] != null || grid[current.x][current.y + 1] != null) {
                            checkUpdate(current, cell, current.fCost + costForMovingDiagonal);
                        }
                    }
                }

                if (current.y - 1 >= 0) {
                    cell = grid[current.x][current.y - 1];
                    // Check and Update the Cost for Bottom Neobour
                    checkUpdate(current, cell, current.fCost + vertical_heuristic_Cost);
                }
                if (current.y + 1 < grid[0].length) {
                    cell = grid[current.x][current.y + 1];
                    checkUpdate(current, cell, current.fCost + vertical_heuristic_Cost);
                }

                if (current.x + 1 < grid.length) {
                    cell = grid[current.x + 1][current.y];
                    checkUpdate(current, cell, current.fCost + vertical_heuristic_Cost);

                    if (booleanpath && current.y - 1 >= 0) {
                        cell = grid[current.x + 1][current.y - 1];
                        if (grid[current.x + 1][current.y] != null || grid[current.x][current.y - 1] != null) {
                            checkUpdate(current, cell, current.fCost + costForMovingDiagonal);
                        }
                    }

                    if (booleanpath && current.y + 1 < grid[0].length) {
                        cell = grid[current.x + 1][current.y + 1];
                        // if Both the TOP and Right Neighbour nodes are blocked we cannot traverse through
                        // TOP and Right
                        if (grid[current.x + 1][current.y] != null || grid[current.x][current.y + 1] != null) {
                            checkUpdate(current, cell, current.fCost + costForMovingDiagonal);
                        }
                    }
                }

            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }


    }

    /*
    public  boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }
    */


    private void checkUpdate(Node current, Node cell, int i) {
        // if cell is not a Cell ()Exception or If Closed[][]
        if (cell == null || closed[cell.x][cell.y]) return;

        int distX = Math.abs(endNodeX - cell.x);    // This is absolution for Non Zero
        int distY = Math.abs(endNodeY - cell.y);    // This is absolution for Non Zero

        if (distX > distY) {
            cell.gcost = costForMovingDiagonal * distY + vertical_heuristic_Cost * (distX - distY);
        } else {
            cell.gcost = costForMovingDiagonal * distX + vertical_heuristic_Cost * (distY - distX);
        }
        // new Final Cost Calculated
        int finalCostFN = cell.gcost;

        // Boolean InOpen holds true and If the Cell is connected in the PriorityQueue
        // Note grid holds values irresepective of queue or close Except for Block which null

        boolean inDegree = queue.contains(cell);

        // if neigbour cell is in not in queue OR if the new fCost is lower than the previous
        if (!inDegree || finalCostFN < cell.fCost) {
            cell.fCost = finalCostFN;
            cell.parentReference = current;
            // if node was not queue then it is added to the queue priorityQueue, it is at this point that the
            // Queue is sorted automatically by lamda function in the above line
            if (!inDegree) queue.add(cell);
        }

        if (vertical_heuristic_Cost == 14) {
            StdDraw.setPenColor(StdDraw.ORANGE);    // it will try to find the V_and_ H Cost
            StdDraw.filledSquare(cell.x, cell.y, .1);
        }
        if (vertical_heuristic_Cost == 10) {
            StdDraw.setPenColor(StdDraw.BOOK_RED);    // it will try to find the V_and_ H Cost
            StdDraw.filledSquare(cell.x, cell.y, .1);
        }
        if (vertical_heuristic_Cost == 10) {
            StdDraw.setPenColor(StdDraw.MAGENTA);    // it will try to find the V_and_ H Cost
            StdDraw.filledSquare(cell.x, cell.y, .1);
        }

    }


}
