public class Qsort {

    public void quickSort(int a[], int n) {
        QSort(a, 0, n - 1);
    }

    public void QSort(int a[], int start, int end) {
        if (start >= end)
            return;
        int mid = partion(a, start, end);
        QSort(a, start, mid - 1);
        QSort(a, mid + 1, end);
    }

    public int partion(int a[], int start, int end) {
        System.out.println("" + start);
        int mid = a[start];
        int left = start;
        int right = end;
        while (left < right) {
            while (left < right && a[right] >= mid)
                right--;
            int tmp = a[right];
            a[right] = a[left];
            a[left] = tmp;
            while (left < right && a[left] <= mid)
                left++;
            tmp = a[right];
            a[right] = a[left];
            a[left] = tmp;
            System.out.println(String.format("left = [%s]  right = [%s]", left, right));
        }
        a[left] = mid;
        return left;
    }
}
