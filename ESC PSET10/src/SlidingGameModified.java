import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class SlidingGameModified {
    //The following models the class sliding game.
    //The following is the board setting.
    // 0 	1 	2
    // 3    4 	5
    // 6 	7   8
    public static void main (String[] args) throws Exception {
        //int[] initialBoardConfig = new int[] {3,5,6,0,2,7,8,4,1};
        //int[] initialBoardConfig = new int[] {5,0,6,4,7,3,2,8,1};
        //int[] initialBoardConfig = new int[] {2,3,8,4,6,0,1,5,7};
        int[] initialBoardConfig = new int[] {2,1,5,3,6,0,7,8,4};
        List<int[]> trace = BFSSearch(new PuzzleNodeModified(initialBoardConfig, null));

        if (trace == null) {
            System.out.println ("No solution");
        }
        else {
            System.out.println ("Solution Found");
            for (int[] i: trace) {
                System.out.println (toString(i));
            }
        }
    }

    public static List<int[]> BFSSearch(PuzzleNodeModified init) {
        final Set<String> seen = new ConcurrentSkipListSet<String>();
        final Queue<PuzzleNodeModified> working = new LinkedBlockingQueue<PuzzleNodeModified>();
        final AtomicBoolean foundFlag = new AtomicBoolean();
        final List<int[]>[] trace = new List[1];
        foundFlag.set(false);
        working.offer(init);

        final ExecutorService exec = Executors.newFixedThreadPool(100);

        while (!foundFlag.get()) {
            final PuzzleNodeModified current = working.poll();
            if (current==null) continue;
//    		System.out.println("One item polled");

            exec.execute(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    String stringConfig = SlidingGameModified.toString(current.config);
                    //System.out.println("exploring " + stringConfig);
                    if (!seen.contains(stringConfig)) {
                        seen.add(stringConfig);
//		                System.out.println(stringConfig);
                        if (isGoal(current.config)) {
                            foundFlag.set(true);
                            trace[0] = current.getTrace();
                            exec.shutdown();
                        }

                        for (int[] next : nextPositions(current.config)) {
                            PuzzleNodeModified child = new PuzzleNodeModified(next, current);
                            working.offer(child);
                        }
                    }
                }
            });
        }

        try {
            exec.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return trace[0];
    }

    private static boolean isGoal (int[] boardConfig) {
        return boardConfig[0] == 1 && boardConfig[1] == 2 && boardConfig[2] == 3 && boardConfig[3] == 4 &&
                boardConfig[4] == 5 && boardConfig[5] == 6 && boardConfig[6] == 7 && boardConfig[7] == 8 && boardConfig[8] == 0;
    }

    private static List<int[]> nextPositions (int[] boardConfig) {
        int emptySlot = -1;

        for (int i = 0; i < boardConfig.length; i++) {
            if (boardConfig[i] == 0) {
                emptySlot = i;
                break;
            }
        }

        List<int[]> toReturn = new ArrayList<int[]>();

        //the empty slot goes right
        if (emptySlot != 2 && emptySlot != 5 && emptySlot != 8) {
            int[] newConfig = boardConfig.clone();
            newConfig[emptySlot]= newConfig[emptySlot+1];
            newConfig[emptySlot+1]=0;
            toReturn.add(newConfig);
        }
        //the empty slot goes left
        if (emptySlot != 0 && emptySlot !=3 && emptySlot != 6) {
            int[] newConfig = boardConfig.clone();
            newConfig[emptySlot]=newConfig[emptySlot-1];
            newConfig[emptySlot-1]=0;
            toReturn.add(newConfig);
        }
        //the empty slot goes down
        if (emptySlot != 6 && emptySlot != 7 && emptySlot != 8) {
            int[] newConfig = boardConfig.clone();
            newConfig[emptySlot]=newConfig[emptySlot+3];
            newConfig[emptySlot+3]=0;
            toReturn.add(newConfig);
        }
        //the empty slot goes up
        if (emptySlot != 0 && emptySlot != 1 && emptySlot != 2) {
            int[] newConfig = boardConfig.clone();
            newConfig[emptySlot] = newConfig[emptySlot-3];
            newConfig[emptySlot-3] = 0;
            toReturn.add(newConfig);
        }

        return toReturn;
    }

    private static String toString(int[] config) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < config.length; i++) {
            sb.append(config[i]);
        }

        return sb.toString();
    }
}

class PuzzleNodeModified {
    final int[] config;
    final PuzzleNodeModified prev;

    PuzzleNodeModified(int[] config, PuzzleNodeModified prev) {
        this.config = config;
        this.prev = prev;
    }

    List<int[]> getTrace() {
        List<int[]> solution = new LinkedList<int[]> ();
        for (PuzzleNodeModified n = this; n.prev != null; n = n.prev) {
            solution.add(0, n.config);
        }

        return solution;
    }

}