package thread;

public class ConcurrentTest {

    final static Object object = new Object();
    static volatile boolean hasNotify = false;

    public static void main(String[] args) {

        Thread thread1 = new Thread(new Runnable1());
        Thread thread2 = new Thread(new Runnable2());
        thread1.start();
        thread2.start();


        //join


    }

    static class Runnable1 implements Runnable {
        @Override
        public void run() {
            System.out.println("thread1 start");
            synchronized (object) {
                try {
                    if (!hasNotify) {
                        object.wait(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("thread1 end");
        }
    }

    static class Runnable2 implements Runnable {
        @Override
        public void run() {
            System.out.println("thread2 start");
            synchronized (object) {
                object.notify();
                hasNotify = true;
            }

            System.out.println("thread2 end");
        }
    }
}
