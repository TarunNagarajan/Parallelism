import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class KnightSolver {
  private static final int N = 6;
  private static final int num_threads = 8;
  private static final long MAX_ATTEMPTS_PER_THREAD = 5_000_000;

  private static final int[] MOVE_X = {2, 1, -1, -2, -2, -1, 1, 2};
  private static final int[] MOVE_Y = {1, 2, 2, 1, -1, -2, -2, -1};

  private volatile boolean is_found = false;

  private int[][] board_success; // holds the successful path, written by the winning num_threads
  
  // this is for the shared state
  private final Object lock = new Object();
  
  public static void main(String[] args) {
    KnightSolver tour = new KnightSolver();
    tour.runSearch();
  }

  // STAGE 01: this method manages the threads
  public void runSearch() {
    System.out.println("Starting the Search...\n");
    board_success = new int[N][N];
    Thread[] threads = new Thread[NUM_THREADS];

    long start_time = System.nanoTime();

    for (int i = 0; i < NUM_THREADS; i++) {
      KnightSolverThread solver = new KnightSolverThread(i);
      threads[i] = new Thread(solver);
      threads[i].start();
    }

    try {
      for (int i = 0; i < NUM_THREADS; i++) {
        threads[i].join();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.err.println("Main thread interrupted.");
    }

    long endtime = System.nanoTime();
    long duration_ms = TimeUnit.NANOSECONDS.toMillis(endtime - starttime)
  
}
