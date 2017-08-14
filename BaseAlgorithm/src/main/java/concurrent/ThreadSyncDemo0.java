package concurrent;

public class ThreadSyncDemo0 {
    private static Integer num = 0;


    Thread odd = new Thread(() -> {
        while (true) {
            try {
                synchronized (this) {
                    System.out.println("odd:" + ++num);
                    notify();
                    Thread.sleep(10);
                    wait();
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
    });

    Thread even = new Thread(() -> {
        try {
            while (true) {
                synchronized (this) {
                    System.out.println("eve:" + ++num);
                    notify();
                    Thread.sleep(10);
                    wait();
                }
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    });

    public static void main(String... args) {

        final ThreadSyncDemo0 syncDemo0 = new ThreadSyncDemo0();

        syncDemo0.odd.start();
        syncDemo0.even.start();
    }
}
