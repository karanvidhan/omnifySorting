package hire.omnify.com.omnifysorting;

import android.app.IntentService;
import android.content.Intent;
import java.util.ArrayList;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyIntentService extends IntentService {

    private SortedFragment.ResponseReceiver receiver;

    ArrayList<Integer> quicklist;
    ArrayList<Integer> mergelist;

    public static final String PARAM_IN_MSG = "data";


    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        // TODO Auto-generated method stub

        quicklist = intent.getIntegerArrayListExtra(PARAM_IN_MSG);

        mergelist = intent.getIntegerArrayListExtra(PARAM_IN_MSG);

        quicksort();
        mergesort();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(SortedFragment.ResponseReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra("quicklist", quicklist);
        broadcastIntent.putExtra("mergelist", mergelist);
        sendBroadcast(broadcastIntent);

    }


//    ____________________________quickSorting________________________________

    public void quicksort() {
        if (quicklist == null || quicklist.size() <= 0) {
            return;
        }
        quickSorting(quicklist, 0, quicklist.size() - 1);
    }

    public static void quickSorting(ArrayList<Integer> items, int start, int end) {
        if (start >= end) {
            return;
        }

        int pivot = partition(items, start, end);

        if (start < pivot - 1) {
            quickSorting(items, start, pivot - 1);
        }

        if (end > pivot) {

            quickSorting(items, pivot, end);
        }
    }


    private static int partition(ArrayList<Integer> items, int start, int end) {


        int pivot = items.get((start + end) / 2);


        while (start <= end) {
            while (items.get(start) < pivot) {
                start++;
            }
            while (items.get(end) > pivot) {
                end--;
            }

            if (start <= end) {
                swap(items, start, end);
                start++;
                end--;
            }
        }
        return start;

    }

    private static void swap(ArrayList<Integer> array, int firstIndex, int secondIndex) {
        if (array == null || firstIndex < 0 || firstIndex > array.size()
                || secondIndex < 0 || secondIndex > array.size()) {
            return;
        }
        int temp = array.get(firstIndex);
        array.set(firstIndex,array.get(secondIndex));
        array.set(secondIndex,temp);

    }
//____________________________________MergeSorting_______________________________________




    private void mergesort() {

        int[] tempArray = new int[mergelist.size()];

        mergeSorting(mergelist, tempArray, 0, mergelist.size()- 1);

    }

    private static void mergeSorting(ArrayList<Integer> data, int[] tempArray, int low, int high) {
        if (low < high) {

            int middle = (low + high) / 2;

            System.out.println(" Divide Step -  low:" + low + " middle:" + middle + " high:" + high);

            mergeSorting(data, tempArray, low, middle);

            mergeSorting(data, tempArray, middle + 1, high);

            merge(data, tempArray, low, middle, high);
        }
    }


    private static void merge(ArrayList<Integer> data, int[] tempArray, int low, int middle, int high) {

        for (int i = low; i <= high; i++) {
            tempArray[i] = data.get(i);
        }

        int i = low; // Start of Left Array
        int j = middle + 1; // Start of right Array
        int k = low;

        while (i <= middle && j <= high) {
            if (tempArray[i] <= tempArray[j]) {
                data.set(k,tempArray[i]);
                i++;
            } else {
                data.set(k,tempArray[j]);
                j++;
            }
            k++;
        }
        while (i <= middle) {
            data.set(k,tempArray[i]);
            k++;
            i++;
        }
    }


}
