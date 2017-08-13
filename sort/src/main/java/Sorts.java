public class Sorts {
    public void bubbleSort(int a[], int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
        }
    }

    public void insertSort(int a[], int n) {
        for (int i = 1; i < n; i++) {
            int j = i;
            int tmp = a[i];
            for (; j > 0; j--) {

                if (tmp > a[j - 1]) {
                    break;
                }
            }

            for (int x = i; x > j; x--) {
                a[x] = a[x - 1];
            }
            a[j] = tmp;
        }
    }

    public void selectSort(int a[], int n) {
        for (int i = 0; i < n; i++) {
            int min_i = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[min_i]) {
                    min_i = j;
                }

                int tmp = a[min_i];
                a[min_i] = a[i];
                a[i] = tmp;
            }
        }
    }

    public void quickSort(int a[], int n) {
        qSort(a, 0, a.length - 1);
    }

    public int partion(int a[], int start, int end) {
        int target = a[start];
        int left = start;
        int right = end;

        while (left < right) {

            while (left < right && a[right] >= target)
                right--;

            int tmp = a[right];
            a[right] = a[left];
            a[left] = tmp;

            while (left < right && a[left] <= target)
                left++;

            tmp = a[right];
            a[right] = a[left];
            a[left] = tmp;
        }

        a[left] = target;

        return left;
    }

    public void qSort(int a[], int start, int end) {
        if (start >= end)
            return;
        int mid = partion(a, start, end);

        qSort(a, start, mid - 1);
        qSort(a, mid + 1, end);
    }


    public void minHeapFixDown(int a[], int i, int limit) {

        int tmp = a[i];
        int j = 2 * i + 1;
        while (j < limit) {
            if (j + 1 < limit && a[j + 1] < a[j])
                j++;
            if (a[j] > tmp)
                break;

            a[i] = a[j];
            i = j;
            j = 2 * i + 1;
        }
        a[i] = tmp;
    }

    public void maxHeapFixDown(int a[], int i, int limit) {

        int tmp = a[i];
        int j = 2 * i + 1;
        while (j < limit) {
            if (j + 1 < limit && a[j + 1] > a[j])
                j++;
            if (a[j] < tmp)
                break;

            a[i] = a[j];
            i = j;
            j = 2 * i + 1;
        }
        a[i] = tmp;
    }


    public void makeMinHeap(int a[], int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            minHeapFixDown(a, i, n);
        }
    }

    public void makeMaxHeap(int a[], int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            maxHeapFixDown(a, i, n);
        }
    }

    public void heapSort(int a[], int n) {
        makeMaxHeap(a, n - 1);

        for (int i = n - 1; i >= 1; i--) {
            int tmp = a[i];
            a[i] = a[0];
            a[0] = tmp;
            maxHeapFixDown(a, 0, i);
        }
    }

}
