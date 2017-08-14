package algorithm.sort;

import algorithm.util.ArrayUtil;
import com.sun.tools.javac.util.ArrayUtils;

import java.util.Vector;

public class ShellSort {

    Vector<Integer> G = new Vector<>();

    private void insertSort(int a[], int n, int g) {
        for (int i = g; i < n; i++) {
            int tmp = a[i];
            int j = i - g;
            while (j >= 0 && a[j] > tmp) {
                a[j + g] = a[j];
                j -= g;
            }
            a[j + g] = tmp;
        }
    }

    private void shellSort(int a[], int n) {
        for (int h = 1; h <= n; ) {
            G.add(h);
            h = 3 * h + 1;
        }

        for (int i = G.size() - 1; i >= 0; i--) {
            insertSort(a, n, G.get(i));
        }
    }

    public static void main(String... args) {
        int a[] = {9, 2, 4, 7, 5, 3, 1, 5, 6};
        ShellSort shellSort = new ShellSort();
        shellSort.shellSort(a, a.length);
        ArrayUtil.print(a);
    }
}
