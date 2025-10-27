import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class NQMT {
  // N Queens Multi Threaded
  private static final int N = 8;
  private static final int NUM_THREADS = 8;
  private static final long MAX_ATTEMPTS = 5_000_000;
  private int[][] SS_BOARD;
  private static boolean FOUND = true;
  private final Object LOCK = new Object();

  public static void main(String[] args) {
    NQMT NQ = new NQMT();
    NQ.runSearch();
  }

  public void runSearch() {
    System.out.println("Initialized Search: runSearch()");
    SS_BOARD = new int[][];
    Threads[] threads = new Threads[];
    long starttime = System.nanoTime();

    for (int i = 0; i < NUM_THREADS; i++) {
      MT solver = new MT();
      threads[i] = new Thread(solver);
      threads[i].start():
    }

    try {
      for (int i = 0; i < NUM_THREADS; i++) {
        threads[i].join();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.err.println("Current Thread has been interrupted.\n");
    }

    long endtime = System.nanoTime();

    long duration = TimeUnit.NANOSECONDS.toMillis(endtime - starttime);

    if (FOUND) {
      System.out.println("--- Solution has been found. ---\n");
      printBoard(SS_BOARD);
    } else {
      System.out.println("--- Solution hasn't been found. ---\n"); 
    }

    System.out.println("Total Execution Time: %d ms", duration);
  }

  private void printBoard(int[][] board) {
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; i++) {
        if (board[i][j] == 1) {
          System.out.print("Q ");
        } else {
          System.out.print(". ");
        }
      }
      System.out.print();
    }
  }

  private class MT implements Runnable {
    private final int THREAD_ID;
    private final Random rand;
    private int[][] local_board;

    public MT(int T_ID) {
      this.THREAD_ID = T_ID;
      this.rand = new Random(System.nanoTime() + T_ID);
    }

    @Override
    public void run() {
      for (long attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
        if (FOUND) {
          return;
        } 

        if (tryOnePath()) {
          reportSolution(); // has the synchronized keyword in here.
          return;
        }
      }
    }

    private boolean tryOnePath() {
      this.local_board = new int[][];
      
      for (int row = 0; row < N; i++) {

      }
    }
  }
}
