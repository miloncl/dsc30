/*
 * NAME: Milon Lonappan
 * PID:  A15231106
 */
import java.util.ArrayList;

/**
 * Sorts class
 * @param <T> Generic type
 * @author Milon Lonappan
 * @since
 */
public class Sorts<T extends Comparable<? super T>> {

    private static final int HALF_LIST = 2;

    /**
     * This method performs insertion sort on the input arraylist
     *
     * @param list The arraylist we want to sort
     * @param start The inital index on subsection of Arraylist we want to sort
     * @param end The final index of the subsection of Arraylist we want to sort
     */
    public void InsertionSort(ArrayList<T> list, int start, int end) {
        for (int i = start + 1; i < end; ++i) {
            int j = i;
            while (j > 0 && list.get(j).compareTo(list.get(j - 1)) > 0) {
                T temp = list.get(j);
                list.set(j, list.get(j - 1));
                list.set(j - 1, temp);
                --j;
            }
        }
    }

    /**
     * This method performs merge sort on the input arraylist
     *
     * @param list The arraylist we want to sort
     * @param start The inital index on subsection of Arraylist we want to sort
     * @param end The final index of the subsection of Arraylist we want to sort
     */
    public void MergeSort(ArrayList<T> list, int start, int end) {

        if (start < end)
        {
            int mid = start + (end - start) / HALF_LIST;
            MergeSort(list, start, mid);
            MergeSort(list , mid + 1, end);

            merge(list, start, mid, end);
        }
    }

    /**
     * merge helper function for MergeSort
     *
     * @param arr The arraylist we want to sort
     * @param l left-most index we want to merge
     * @param m the middle index we want to merge
     * @param r right-most index we want to merge
     */
    private void merge(ArrayList<T> arr, int l, int m, int r) {

        int mergedSize = r - l + 1;

        ArrayList<T> mergedNums = new ArrayList<>();
        int left = l, right = m + 1;
        while (left <= m && right <= r) {
            if (arr.get(left).compareTo(arr.get(right)) <= 0) {
                mergedNums.add(arr.get(left));
                left++;
            }
            else {
                mergedNums.add(arr.get(right));
                right++;
            }
        }

        while (left <= m) {
            mergedNums.add(arr.get(left));
            left++;
        }
        while (right <= r) {
            mergedNums.add(arr.get(right));
            right++;
        }
        for (int i = 0; i < mergedSize; i++) {
            arr.set(l + i, mergedNums.get(i));
        }
    }

    /**
     * This method performs quick sort on the input arraylist
     *
     * @param list The arraylist we want to sort
     * @param start The inital index on subsection of Arraylist we want to sort
     * @param end The final index of the subsection of Arraylist we want to sort
     */
    public void QuickSort(ArrayList<T> list, int start, int end) {
        if (start >= end) {
            return;
        }

        int x = partition(list, start,end);
        QuickSort(list, start, x);
        QuickSort(list, x+1, end);
    }
    }

    /**
     * partition helper function for QuickSort
     *
     * @param arr The arraylist we want to sort
     * @param l left-most index we want to merge
     * @param h right-most index we want to merge
     */
    private int partition(ArrayList<T> arr, int l, int h) {
        int middle = l + (h-l)/HALF_LIST;
        T pivot = arr.get(middle);

        boolean finished = false;

        while(!finished){
            while (arr.get(l).compareTo(pivot) < 0) {
                l+=1;
            }
            while(pivot.compareTo(arr.get(h)) < 0) {
                h -=1;
            }

            if (l >= h) {
                finished = true;
            } else {
                T temp = arr.get(l);
                arr.set(l, arr.get(h));
                arr.set(h, temp);

                l +=1;
                h -=1;
            }
        }
        return h;
    }

    /**
     * This method performs a modified QuickSort that switches to insertion sort after a certina cutoff
     *
     * @param list The arraylist we want to sort
     * @param start The inital index on subsection of Arraylist we want to sort
     * @param end The final index of the subsection of Arraylist we want to sort
     * @param cutoff the minimum length of an subsection of the arraylist such that we switch to Insertion Sort
     */
    public void Modified_QuickSort(ArrayList<T> list, int start, int end, int cutoff) {
        if (start >= end) {
            return;
        }

        if(end-start <= cutoff) {
            InsertionSort(list,start,end);
        } else {
            int x = partition(list, start,end);
            QuickSort(list, start, x);
            QuickSort(list, x+1, end);
        }
    }

    /**
     * This method performs a modified QuickSort that switches to insertion sort after a certina cutoff
     *
     * @param list The arraylist we want to sort
     * @param start The inital index on subsection of Arraylist we want to sort
     * @param end The final index of the subsection of Arraylist we want to sort
     * @param param The length of the initial splits that are sorted prior to merging
     */
    public void TimSort(ArrayList<T> list, int start, int end, int param){
        if (param > end-start) {
            InsertionSort(list, start, end);
        }

        for (int x = start; x <= end; x += param) {
            InsertionSort(list, x, Math.min(i+param-1, end));
        }


        for (int y = param; y < (end-start+1); y *= 2 ) {
            for (int z = start; z < end; z += 2 * y) {
                int right = Math.min((z + 2 * y - 1), end);
                int mid = z + y -1;
                if(mid > end) {
                    break;
                }

                merge(list, z, mid, right);
            }
        }
    }
}