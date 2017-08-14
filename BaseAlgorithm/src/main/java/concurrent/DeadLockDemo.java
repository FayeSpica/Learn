package concurrent;

import java.util.concurrent.SynchronousQueue;

public class DeadLockDemo {
    private static String A = "A";
    private static String B = "B";

    public static void main(String... args) {
        new DeadLockDemo().deadLock();
    }

    public void deadLock() {
        Thread t1 = new Thread(() -> {
            synchronized (A) {
                System.out.println("t1 get A");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B) {
                    System.out.println("t1 get B");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (B) {
                System.out.println("t2 get B");
                synchronized (A) {
                    System.out.println("t2 get A");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
