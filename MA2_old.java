import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class MA2 {

    static int courses = 0;
    static int lectureHalls = 0;
    public static void main(String[] args) throws FileNotFoundException{

        //create scanner and gather input
        File f      = new File("input.txt");
        Scanner s   = new Scanner(f);

        courses         = s.nextInt();
        lectureHalls    = s.nextInt(); 

        int[][] adjMatrix = new int[courses][lectureHalls];

        //Pre-fill with zeroes
        for(int x = 0; x < courses; x++){
            for(int y = 0; y < lectureHalls; y++){
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
                adjMatrix[x][lineScanner.nextInt()-1] = 1;
            }
            if(s.hasNextLine())
            {
                line = s.nextLine();
                lineScanner = new Scanner(line);
            }
        }

        //Answer
        System.out.println(maxFlow(adjMatrix));

        lineScanner.close();
        s.close(); //  end main  :)
    }
    public static int maxFlow(int graph[][]){

        //keeps track of matches between courses and lecture halls
        int match[] = new int[courses];

        //Mark all as -1 for unmatched
        for(int x = 0; x < courses; x++)
            match[x] = -1;
        
        int totalFlow = 0; //max flow value
        for(int v = 0; v < lectureHalls; v++){

            boolean seen[] =new boolean[courses] ;
            //seen[] starts as all false and changes as pathExists() looks for and finds a path 
            for(int i = 0; i < courses; ++i)
                seen[i] = false;
            
            if(pathExists(graph, v, seen, match))
                totalFlow++;
            //totalFlow can be incremented by one because any path found 
            //will always be of flow one in this case
        }
        return totalFlow;
    }

    //returns true if there is an augmenting path for vertex v
    public static boolean pathExists(int graph[][], int v, boolean seen[], int match[]){
        //try to find an avaiable path
        for(int x = 0; x < courses; x++){

            //looks for next unseen adjacent node
            if((graph[v][x]==1) && !seen[x]){
                seen[x] = true;

                //tries to add that node in the flow
                //match[x]<0 means that vertex v is not matched with the node we are looking at
                if(match[x]<0 || pathExists(graph, match[x], seen, match)){
                    match[x] = v;
                    return true; 
                }
            }
        }
        return false;
    }
}
