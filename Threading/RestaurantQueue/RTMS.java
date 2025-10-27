import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Queue;
import java.util.stream.Collectors;

class Table {
  final int id;
  final int capacity;
  volatile boolean isBusy;
  
  public Table(int id, int capacity) {
    this.id = id;
    this.capacity = capacity;
  }

  public String toString() {
    return "Table [" + id + "]: | Capacity: [" + capacity + "]";
  }
}

class Host {
  private final List<Table> tables;
  private final List<Party> waitingQueue = new ArrayList<>(); 
  private final Object lock = new Object();

  public Host(List<Table> tables) {
    this.tables = tables;
  }

  public Table requestTable(Party party) throws InterruptedException {
    sychronized (lock) {
      Table bestTable = findBestTable(party.getPartySize());
      while (bestTable == null) {
        System.out.println("There is a party waiting for a table.");
        if (!waitingQueue.contains(party)) {
          waitingQueue.add(party);
        }

        lock.wait();

        if (isOurTurn(party)) {
          bestTable = findBestTable(party.getPartySize());
        }
      }

      waitingQueue.remove(party);
      bestTable.isBusy = true;
      return bestTable;
    }
  }

  public void releaseTable(Table table, Party party) {
    synchronized (lock) {
      table.isBusy = false;
      System.out.printf("% is freed.", party.getName());
      lock.notifyAll();
    }
  }

  private Table findBestTable(int partySize) {
    Table bestTable = null;
    for (Table table: tables) {
      if (!table.isBusy && table.capacity > partySize) {
        if (bestFit == null || table.capacity < bestTable.capacity) {
          bestTable = table;
        }
      }
    }
    return bestTable;
  }

  private boolean isOurTurn(Party party) {
    return !waitingQueue.isEmpty() && waitingQueue.get(0) == party;
  }
}
class Party implements Runnable {
  private final String partyName;
  private final int partySize;
  private final Host hostStand;
  private final Random rand = new Random();

  public Party(String name, int size, Host host) {
    this.name = partyName;
    this.size = partySize;
    this.host = hostStand;
  }

  public String getName() {
    return partyName;
  }

  public int getPartySize() {
    return partySize;
  }

  public void run() {
      try {
      Table table = host.requestTable(this);
      System.out.printf("Party %s is eating at %s...%n", partyName, table);
      Thread.sleep(rand.nextInt(3000) + 2000);

      host.releaseTable(table, this);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.err.println("A party was interrupted.");
      }
  }
}

public class RTMS {
  public static void main(String[] args) throws InterruptedException {
    List<Table> tables = new ArrayList<>();
    // add the rest of the testing, simulation code.
  }
}
