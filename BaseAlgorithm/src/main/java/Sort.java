///**
// * Created by liaowm5 on 17/8/7.
// */
//public class Sort {
//    public static void InsertSort(int a[], int n) {
//        for (int i = 1; i < n; i++) {
//            int j = i - 1;
//            for (; j >= 0; j--) {
//                if (a[i] > a[j]) {
//                    break;
//                }
//            }
//
//            int tmp = a[i];
//            int x = i - 1;
//            for (; x > j; x--) {
//                a[x + 1] = a[x];
//            }
//            a[j + 1] = tmp;
//        }
//    }
//
//    public static void BubbleSort(int a[], int n) {
//        for (int i = 0; i < n; i++) {
//            for (int j = 1; j < n - i; j++) {
//                if (a[j - 1] > a[j]) {
//                    int temp = a[j - 1];
//                    a[j - 1] = a[j];
//                    a[j] = temp;
//                }
//            }
//        }
//    }
//
//    public static void SelectSort(int a[], int n) {
//        for (int i = 0; i < n; i++) {
//            int min = a[i];
//            int minI = i;
//            for (int j = i; j < n; j++) {
//                if (a[j] < min) {
//                    min = a[j];
//                    minI = j;
//                }
//            }
//            int temp = a[i];
//            a[i] = a[minI];
//            a[minI] = temp;
//        }
//    }
//
//    Qsort()
//
//    public static void QuickSort(int a[], int n) {
//
//    }
//
//
//    public static void HeapSort(int a[], int n) {
//
//    }
//}
