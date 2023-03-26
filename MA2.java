import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class MA2 {

    static int courses = 0;
    static int lectureHalls = 0;
    static int size = 0;
    
    public static void main(String[] args) throws FileNotFoundException{

        //create scanner and gather input
        File f      = new File("input.txt");
        Scanner s   = new Scanner(f);

        courses         = s.nextInt();
        lectureHalls    = s.nextInt(); 

        size = courses + lectureHalls + 2;
        int[][] adjMatrix = new int[size][size];

        //Pre-fill with zeroes
        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                adjMatrix[x][y] = 0;
            }
        }
        s.nextLine(); //skip


        //Populate adjacency Matrix
        String line = s.nextLine();
        Scanner lineScanner = new Scanner(line);
        for(int x = 0; x < courses; x++){

            lineScanner.next(); //skip first (its the course which is just x in this for loop since courses are in order)
            while(lineScanner.hasNextInt()){
                adjMatrix[x][lineScanner.nextInt()-1+courses] = 1;
            }
            if(s.hasNextLine())
            {
                line = s.nextLine();
                lineScanner = new Scanner(line);
            }
        }

        //Add node that connects to all courses (second to last node)
        for(int x = 0; x < courses; x++){
            adjMatrix[size-2][x] = 1;
        }

        //Add node that connects all lecture halls to itself (last node)
        for(int x = lectureHalls; x < size-2; x++){
            adjMatrix[x][size-1] = 1;
        }

        //size-2 is the source, size-1 is the sink
        System.out.println(maxFlow(adjMatrix, size-2, size-1));

        lineScanner.close();
        s.close(); //  end main  :)
    }

    //returns max flow for any given graph from source to sink
    public static int maxFlow(int graph[][], int source, int sink){
        int u, v;
        int residual[][] = new int[size][size];

        //to start, residual is just the original graph
        for (u = 0; u < size; u++)
            for (v = 0; v < size; v++)
                residual[u][v] = graph[u][v];
 
        //place to store path found in the bfs search
        int parent[] = new int[size];
        int max_flow = 0; //no flow to start
 
        //while there is a path from source to sink, add it to the flow
        while (bfs(residual, source, sink, parent)) {

            //we need the minimum value along the path (the bottleneck)
            //this will be the flow
            int path_flow = Integer.MAX_VALUE;
            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, residual[u][v]);
            }
 
            //traverse and update residual graph along parent[]
            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                residual[u][v] -= path_flow;
                residual[v][u] += path_flow;
            }
 
            // Add path flow to overall flow
            max_flow += path_flow;
        }
 
        // Return the overall flow
        return max_flow;
    }


    //Breadth First Search for path from source to sink, returns true if path exists
    //Also puts path in parent[] as it goes along
    public static boolean bfs(int residual[][], int source, int sink, int parent[]){

        boolean seen[] = new boolean[size];

        //all start false initially
        for(int i = 0; i < size; i++){
            seen[i] = false;
        }

        //useful tools for bfs
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(source);
        seen[source] = true;
        parent[source] = -1;
 
        //some standard bfs loop
        while (queue.size() != 0) {
            int u = queue.poll();
            
            //goes through everything
            for (int v = 0; v < size; v++) {

                //if node is connected and not seen
                if (seen[v] == false && residual[u][v] > 0) {
                    
                    //if found sink, return true
                    if (v == sink) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    seen[v] = true;
                }
            }
        }
        
        //no path found return false
        return false;
    }
    
}
