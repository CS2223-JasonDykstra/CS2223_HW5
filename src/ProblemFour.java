public class ProblemFour {
    //graph size
    static int V = 10;

    int minDistance(int dist[], Boolean sptSet[]) {
        //initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        return min_index;
    }

    // A utility function to print the constructed distance array
    void printSolution(int dist[], int n, int end) {
        System.out.println("Vertex " + end + " is " + dist[end] + " units of distance from the source!");
    }

    //dijkstra's algorithm using a 2d array as input representing a graph
    void dijkstra(int graph[][], int source, int end) {
        //array of distances from source to i, with the index being the node #
        int dist[] = new int[V];

        //true if vertex i is included in shortest path tree or shortest distance from source to i is finalized
        Boolean sptSet[] = new Boolean[V];

        //initialize all distances as max int value and stpSet[] as false
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        //distance of source vertex from itself is always 0
        dist[source] = 0;

        //find shortest path for all vertices
        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, sptSet);

            //mark the picked vertex as visited
            sptSet[u] = true;

            //update dist value of the adjacent vertices for the desired vertex
            for (int v = 0; v < V; v++)
                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
        }

        //print the solution!
        printSolution(dist, V, end);
    }

    public static void main(String[] args) {
        /* Let us create the example graph discussed above */
        int graph[][] = new int[][]{{0, 50, 7, 10, 0, 0, 0, 0, 0, 0},
                {50, 0, 30, 0, 3, 0, 99, 0, 0, 0},
                {7, 30, 0, 6, 27, 15, 0, 0, 0, 0},
                {10, 0, 6, 0, 0, 11, 0, 0, 4, 0},
                {0, 3, 27, 0, 0, 12, 120, 105, 0, 0},
                {0, 0, 15, 11, 12, 0, 0, 119, 5, 0},
                {0, 99, 0, 0, 120, 0, 0, 2, 0, 67},
                {0, 0, 0, 0, 105, 119, 2, 0, 122, 66},
                {0, 0, 0, 4, 0, 5, 0, 122, 0, 190},
                {0, 0, 0, 0, 0, 0, 67, 66, 190, 0}};


        ProblemFour t = new ProblemFour();
        t.dijkstra(graph, 0, 8);
    }
}
