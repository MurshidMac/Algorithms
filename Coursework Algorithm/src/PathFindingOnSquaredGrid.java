/*
* Mohamed Murshid Hassen w1583002 2014191
* Algorithm to find the Shortest path between two given points in an Directed adjacency matrix
* */

import java.util.Scanner;


public class PathFindingOnSquaredGrid {

    // given an N-by-N matrix of queue cells, return an N-by-N matrix
    // of cells reachable from the top

    // Calculation of Grid Horizontal and Vertical Matrix
    // Implmentation can be done through Calling the Method

    public double probability;
    public static int Ai,Aj,Bi,Bj;
    public int vi,vj;
    public static int size;
    public double probab;
    static Scanner in = new Scanner(System.in);
    static GraphWeightedGrid graph= new GraphWeightedGrid(size,size);


    public boolean[][] flow(boolean[][] open) {
        int N = open.length;
    
        boolean[][] full = new boolean[N][N];
        for (int j = 0; j < N; j++) {
            flow(open, full, 0, j);
        }
    	
        return full;
    }
    
    // determine set of queue/blocked cells using depth first search
    public  void flow(boolean[][] open, boolean[][] full, int i, int j) {
        int N = open.length;

        // base cases
        if (i < 0 || i >= N) return;    // invalid row
        if (j < 0 || j >= N) return;    // invalid column
        if (!open[i][j]) return;        // not an queue cell
        if (full[i][j]) return;         // already marked as queue

        full[i][j] = true;

        flow(open, full, i+1, j);   // down
        flow(open, full, i, j+1);   // right
        flow(open, full, i, j-1);   // left
        flow(open, full, i-1, j);   // up
    }

    /*
    // does the system percolate?
    public boolean percolates(boolean[][] queue) {
        int N = queue.length;
    	
        boolean[][] full = flow(queue);
        for (int j = 0; j < N; j++) {
            if (full[N-1][j]) return true;
        }
    	
        return false;
    }
    
 // does the system percolate vertically in a direct way?
    public boolean percolatesDirect(boolean[][] queue) {
        int N = queue.length;
    	
        boolean[][] full = flow(queue);
        int directPerc = 0;
        for (int j = 0; j < N; j++) {
        	if (full[N-1][j]) {
        		// StdOut.println("Hello");
        		directPerc = 1;
        		int rowabove = N-2;
        		for (int i = rowabove; i >= 0; i--) {
        			if (full[i][j]) {
        				// StdOut.println("i: " + i + " j: " + j + " " + full[i][j]);
        				directPerc++;
        			}
        			else break;
        		}
        	}
        }
    	
        // StdOut.println("Direct Percolation is: " + directPerc);
        if (directPerc == N) return true; 
        else return false;
    }
    */
    
    // draw the N-by-N boolean matrix to standard draw
    /*
    public void show(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                	StdDraw.square(j, N-i-1, .5);
                else StdDraw.filledSquare(j, N-i-1, .5);
    }
    */

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public void show(boolean[][] matrixSort, boolean which) {

        int N = matrixSort.length;
        StdDraw.setXscale(-1, N);;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
/*
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (matrixSort[i][j] == which)
                	try {
                        if ((i == graph.startNodeX && j == graph.startNodeY) || (i == graph.endNodeX && j == graph.endNodeY)) {
                            StdDraw.circle(j, N - i - 1, .5);
                        } else StdDraw.square(j, N - i - 1, .5);
                    }catch (NullPointerException r){

                    }
                else StdDraw.filledSquare(j, N-i-1, .5);
*/

       for (int i = 0; i <size ; i++) {
            for (int j = 0; j < size; j++) {
                if (matrixSort[i][j]) {
                    System.out.println(i+","+ j);
                    StdDraw.filledSquare(i, j, 0.5);
                }
                else
                    StdDraw.square(i, j, 0.5);
            }
        }
    }

    //
    public void inputFromUser(boolean[][] matrix){

    for(boolean bo=false;!bo;) {
        StdOut.println("Enter i for A > ");
        vi = in.nextInt();
        StdOut.println("Enter j for A > ");
        vj = in.nextInt();
        graph.initializeStartNode(vi, vj);


        if(matrix[Ai][Aj] || Ai>size ||
                Aj>size){
            bo=false;
            StdOut.println("Node Does not Exist");
        }   else {
            StdOut.println("Enter i for B > ");
            vi = in.nextInt();
            StdOut.println("Enter j for B > ");
            vj = in.nextInt();
            graph.initializeEndNode(vi, vj);
            if(matrix[Bi][Bj] || Bi>size ||Bj>size){
                bo=false;
                StdOut.println("The Cell you have mentioned does exit");
            }else{
                bo=true;
            }
        }
        }
    }

    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    // This has to always static
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }


    // test client
    public static void main(String[] args) {
        // boolean[][] queue = StdArrayIO.readBoolean2D();
    	PathFindingOnSquaredGrid find= new PathFindingOnSquaredGrid();
    	// The following will generate a 10x10 squared grid with relatively few obstacles in it
    	// The lower the second parameter, the more obstacles (black cells) are generated
    	Scanner input= new Scanner(System.in);
        System.out.println("Imput the Grid Size");
        size=input.nextInt();

        System.out.println("Input the Probability");
        double probab=in.nextDouble();

        boolean[][] randomlyGenMatrix = random(size, probab);

    	StdArrayIO.print(randomlyGenMatrix);


        // Showing the Randomly Generated Matri
    	find.show(randomlyGenMatrix, true);
        // Getting the Inputs


    	Stopwatch timerFlow = new Stopwatch();

        find.inputFromUser(randomlyGenMatrix);
        // Creating the Weighted Graph
        graph = new GraphWeightedGrid(size,size);
        graph.createGrid(randomlyGenMatrix);    // First Check
        graph.costForMovingDiagonal =10;

        graph=new GraphWeightedGrid(size,size);
        graph.createGrid(randomlyGenMatrix);    // Second Initialization
        graph.costForMovingDiagonal =20;

        graph.booleanpath =false;

        graph=new GraphWeightedGrid(size,size);
        graph.createGrid(randomlyGenMatrix);    // Third Initialization

        StdOut.println("Elapsed time = " + timerFlow.elapsedTime());
    }
}



