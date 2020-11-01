package thread;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicTask task = new AtomicTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    task.incrementVolatile();
                    task.incrementAtomic();
                }
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        //20000
        System.out.println("原子类的结果:" + task.mAtomicInteger.get());
        //小于20000
        System.out.println("volatile的结果:" + task.volatileCount);
    }

    static class AtomicTask {
        AtomicInteger mAtomicInteger = new AtomicInteger();
        volatile int volatileCount = 0;

        void incrementAtomic() {
            mAtomicInteger.incrementAndGet();
        }

        void incrementVolatile() {
//            volatileCount = volatileCount + 1;
            volatileCount++;
        }
    }
}
