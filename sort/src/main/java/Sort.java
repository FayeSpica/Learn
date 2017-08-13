import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class Sort {

//    public static void bubbleSort(int a[], int n) {
//        for (int i = 0; i < n; i++) {
//            for (int j = 1; j < n - i; j++) {
//                if (a[j - 1] > a[j]) {
//                    int tmp = a[j - 1];
//                    a[j - 1] = a[j];
//                    a[j] = tmp;
//                }
//            }
//        }
//    }
//
//    //!!!
//    public static void insertSort(int a[], int n) {
//        for (int i = 1; i < n; i++) {
//            int target = a[i];
//            int j = i;
//            for (; j > 0; j--) {
//                if (a[j - 1] < a[i]) {
//                    break;
//                }
//            }
//
//            for (int x = i; x > j; x--) {
//                a[x] = a[x - 1];
//            }
//
//            a[j] = target;
//        }
//    }
//
//    public static void selectSort(int a[], int n) {
//        for (int i = 0; i < n; i++) {
//            int min_i = i;
//            for (int j = i; j < n; j++) {
//                if (a[j] < a[min_i]) {
//                    min_i = j;
//                }
//            }
//            int tmp = a[i];
//            a[i] = a[min_i];
//            a[min_i] = tmp;
//        }
//    }
//
//    public static void mergeSort(int a[], int n) {
//        merge(a, 0, n - 1);
//    }
//
//    public static void merge(int a[], int start, int end) {
//        if (start >= end)
//            return;
//        int mid = (end + start) / 2;
//        merge(a, start, mid);
//        merge(a, mid + 1, end);
//
//        List<Integer> iList = new ArrayList<Integer>();
//        List<Integer> jList = new ArrayList<Integer>();
//
//        for (int ii = start; ii <= mid; ii++) {
//            iList.add(a[ii]);
//        }
//
//        for (int ii = mid + 1; ii <= end; ii++) {
//            jList.add(a[ii]);
//        }
//
//        int index = start;
//
//        int i = 0, j = 0;
//
//        while (i < iList.size() && j < jList.size()) {
//            if (iList.get(i) < jList.get(j)) {
//                a[index++] = iList.get(i);
//                i++;
//            } else {
//                a[index++] = jList.get(j);
//                j++;
//            }
//        }
//        while (i < iList.size()) {
//            a[index++] = iList.get(i++);
//        }
//        while (j < jList.size()) {
//            a[index++] = jList.get(j++);
//        }
//    }
//
//    public static void maxHeapFixDown(int a[], int i, int limit) {
//        int tmp = a[i];
//        int j = 2 * i + 1;
//        while (j < limit) {
//            if (j + 1 < limit && a[j + 1] > a[j])
//                j++;
//            if (a[j] < tmp)
//                break;
//            a[i] = a[j];
//            i = j;
//            j = i * 2 + 1;
//        }
//        a[i] = tmp;
//    }
//
//    public static void minHeapFixDown(int a[], int i, int limit) {
//        int tmp = a[i];
//        int j = 2 * i + 1;
//        while (j < limit) {
//            if (j + 1 < limit && a[j + 1] < a[j])
//                j++;
//            if (a[j] > tmp)
//                break;
//            a[i] = a[j];
//            i = j;
//            j = i * 2 + 1;
//        }
//        a[i] = tmp;
//    }
//
//    public static void makeMaxHeap(int a[], int n) {
//        for (int i = n / 2 - 1; i >= 0; i--)
//            maxHeapFixDown(a, i, n);
//    }
//
//    public static void makeMinHeap(int a[], int n) {
//        for (int i = n / 2 - 1; i >= 0; i--)
//            minHeapFixDown(a, i, n);
//    }
//
//
//
//    public static void heapSort(int a[], int n) {
//        makeMinHeap(a, n);
//        for (int i = n - 1; i >= 1; i--) {
//            int tmp = a[i];
//            a[i] = a[0];
//            a[0] = tmp;
//            minHeapFixDown(a, 0, i);
//        }
//    }

//    public static void main(String args[]) {
//        int a[] = {1, 3, 8, 6, 6, 4, 3, 5, 9, 8, 9};
//        Sorts sorts = new Sorts();
//        sorts.heapSort(a, a.length);
//        for (int i : a) {
//            System.out.print(i + " ");
//        }
//        System.out.println();
//
//        Stack<Integer> stack;
//        Vector<Integer> vector;
//    }
}
