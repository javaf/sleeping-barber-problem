import java.util.concurrent.*;

class Main {
  static Semaphore barber;
  static Semaphore customer;
  static Semaphore accessSeats;
  static int seats = 4;
  static int N = 20;

  static void barber() {
    new Thread(() -> {
      try {
      while(true) {
        log("barber: sleeping");
        customer.acquire();
        log("barber: got customer");
        accessSeats.acquire();
        seats++;
        barber.release();
        accessSeats.release();
        log("barber: cutting hair");
        Thread.sleep(1000);
        log("barber: cutting done");
      }
    }
    catch(InterruptedException e) {}
    }).start();
  }

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
