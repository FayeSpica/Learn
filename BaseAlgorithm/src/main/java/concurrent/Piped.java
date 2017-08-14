package concurrent;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Piped {


    public static void main(String... args) throws Exception {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
    }

    static class Print implements Runnable {
        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.println((char) receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
