import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Queue;

class DatabaseConnection {
  private final int id;
  volatile boolean inUse;

  public DatabaseConnection(int id) {
    this.id = id;
    this.inUse = false;
  }

  public int getId() {
    return id;
  }

  public void executeQuery(String taskName) throws InterruptedException {
    System.out.printf("[%s] executing a query...%n", taskName);
    Thread.sleep(new Random().nextInt(1500) + 500);
  }
}

class ConnectionPool {
  private final int POOL_SIZE;
  private final List<DatabaseConnection> pool;
  private final Object lock = new Object();

  public ConnectionPool(int size) {
    this.POOL_SIZE = size;
    this.pool = new ArrayList<>(size);
    for (int i = 0; i < POOL_SIZE; i++) {
      pool.add(new DatabaseConnection(i));
    }
  }

  public DatabaseConnection getConnection(String taskName) throws InterruptedException {
    synchronized (lock) {
      DatabaseConnection freeConnection = findConnection();

      while (freeConnection == null) {
        System.out.printf();
        lock.wait();
        freeConnection = findConnection(); // refresh search for connections
      }
    }
  }

  public void releaseConnection(String taskName, DatabaseConnection conn) {
    synchronized (lock) {
      connection.inUse = false;
      System.out.printf("RELEASED CONNECTION...");
      lock.notifyAll();
    }
  }

  private void DatabaseConnection findConnection() {
    for (DatabaseConnection connection: pool) {
      if (!conn.inUse) {
        return conn;
      }
    }
    return null;
  }
}

class QueryTask implements Runnable {
  private final String taskName;
  private final List<DatabaseConnection> pool;

  public QueryTask(String taskName, List<DatabaseConnection> pool) {
    this.taskName = taskName;
    this.pool = pool;
  }

  @Override
  public void run() {
    try {
      // find a request, maybe not in the traditional sense.
      DatabaseConnection conn = findConnection();
      conn.executeQuery(taskName);
      pool.releaseConncetion(taskName, conn);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.out.println("Thread that is executing the Database connection is interrupted.");
    }
  }
}

public class DBCP {
  private static final int POOL_SIZE = 5;
  private static final int NUM_CONN = 15;

  public static void main(String[] args) {
    System.out.println("Starting Database Connections...");
    ConnectionPool pool = new ConnectionPool(POOL_SIZE);
    Thread[] tasks = new Thread[NUM_CONN];

    for (int i = 0; i < NUM_CONN; i++) {
     // TODO: add the tasks[i].start();, and the tasks[i] = new Thread(new QueryTask()) and stuff like that.
    }

    for (Thread task: tasks) {
      task.join();
    }

    System.out.println("All Querying Tasks have been completed.");
  }
}
