package concurrent;

public class LiveLockDemo {
    public static String A = "A";
    public static String B = "B";

    public void liveLock() {
        Thread t1 = new Thread(() -> {
            synchronized (A){
                synchronized (B){
                    Integer a;
                    Object c;
                }
            }
        });
    }
}
