package thread;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

public class SynchronizedDemo {
    static List<String> tickets = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            tickets.add("票_" + (i + 1));
        }
        sellTickets();
    }

    private static void sellTickets() {
        final SynchronizedTestDemo demo = new SynchronizedTestDemo();
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    demo.printThreadName();
                }
            }).start();
        }
    }

    static class SynchronizedTestDemo {

        public synchronized void printThreadName() {
            String name=Thread.currentThread().getName();
            System.out.println("买票人："+name+"准备好了...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("买票人："+name+"买到了..."+tickets.remove(0));
        }
    }
}
