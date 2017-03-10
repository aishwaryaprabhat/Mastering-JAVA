import java.io.File;
import java.io.IOException;

public class ProcessManagement {

    //set the working directory
//    private static File currentDirectory = new File("/home/jit/progassignment1/java/");
    //set the instructions file
    private static File instructionSet = new File("testproc.txt");
    public static Object lock=new Object();

    public static void main(String[] args) throws InterruptedException, IOException {

        //parse the instruction file and construct a data structure, stored inside ProcessGraph class
        ParseFile.generateGraph(new File("graph-file1"));

        // Print the graph information
        ProcessGraph.printGraph();
        ProcessBuilder processbuilder = new ProcessBuilder();
//        Process p = processbuilder.start();
//        System.out.println(p.getInputStream());
//        System.out.println(processbuilder.environment());

	// WRITE YOUR CODE


        // Using index of ProcessGraph, loop through each ProcessGraphNode, to check whether it is ready to run
        for(ProcessGraphNode node:ProcessGraph.nodes) {
            System.out.println(node.getCommand());
            if (node.isRunnable()) {
//                System.out.println(node.getCommand());
//                processbuilder.command(node.getCommand());
//                Process p = processbuilder.start();

                // check if all the nodes are executed

                // WRITE YOUR CODE

                //mark all the runnable nodes
                // WRITE YOUR CODE

                //run the node if it is runnable
                // WRITE YOUR CODE


            }
        }
        System.out.println("All process finished successfully");
    }

}
