import java.util.concurrent.*;

// There is a barber sleeping in his shop. When a
// customer comes, he checks if the barber is
// sleeping and wakes him up. If there are no
// other customers in the waiting room, the barber
// cuts his hair. Else the customer takes a set in
// the waiting room. But if there are no seats, he
// leaves. Once a barber finishes cutting hair, he
// checks if there are any customers in the
// waiting room. If not, he goes back to sleep
// again. The idea is the barber works only when a
// customer arrives, and sleeps otherwise.

class Main {
  static Semaphore barber;
  static Semaphore customer;
  static Semaphore accessSeats;
  static int seats = 4;
  static int N = 20;
  // accessSeats: only one sit / get up at once
  // seats: no. of seats in the waiting room
  // N: no .of customers

  // The barber is providing 24x7 service:
  // 1. Sleeps until a customer wakes him up
  // 2. He picks first customer in room (b.release)
  // 3. One seat is freed up (seats++)
  // 4. Cuts customer's hair (sleep 1s)
  // ... Goes back to sleep
  static void barber() {
    new Thread(() -> {
      try {
      while(true) {
        log("barber: sleeping");
        customer.acquire();
        log("barber: got customer");
        accessSeats.acquire();
        barber.release();
        seats++;
        accessSeats.release();
        log("barber: cutting hair");
        Thread.sleep(1000);
        log("barber: cutting done");
      }
    }
    catch(InterruptedException e) {}
    }).start();
  }

  // Each customer requires a haircut:
  // 1. Occupies a seat, if available
  // 2. Wakes up the barber (c.release)
  // 3. Waits for barber to indicate his turn
  // ... His hair is cut.
  static void customer(int i) {
    new Thread(() -> {
      try {
      log("customer "+i+": checking seats");
      accessSeats.acquire();
      if(seats<=0) {
        log("customer "+i+": no seats!");
        accessSeats.release();
        return;
      }
      seats--;
      customer.release();
      accessSeats.release();
      log("customer "+i+": sat, seats="+seats);
      barber.acquire();
      log("customer "+i+": having hair cut");
      }
      catch(InterruptedException e) {}
    }).start();
  }

  // 1. Barber is sleeping
  // 2. There are no customers
  // 3. No one is accessing seats
  // 4. Baber is started (sleeping)
  // 5. After random intervals, customers arrive
  public static void main(String[] args) {
    try {
    barber = new Semaphore(0);
    customer = new Semaphore(0);
    accessSeats = new Semaphore(1);
    barber();
    for(int i=0; i<N; i++) {
      Thread.sleep((long)(Math.random()*1000));
      customer(i);
    }
    }
    catch(InterruptedException e) {}
  }
  static void log(String x) {
    System.out.println(x);
  }
}
