package cn.tonlyshy.annotation;

/**
 * Created by liaowm5 on 17/7/31.
 */
@ExtractInterface("IMultipler")
public class Multiplier {
    public int multiply(int x, int y) {
        int total = 0;
        for (int i = 0; i < x; i++)
            total = add(total, y);
        return total;
    }

    public int add(int x, int y) {
        return x + y;
    }

    public static void main(String args[]) {
        Multiplier m = new Multiplier();
        System.out.println("11*6 = " + m.multiply(11, 6));
    }
}
