import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class FreqElementInArray extends RecursiveAction {
    public long freq;
    int arr[], lo, hi;
    public int key;

    public FreqElementInArray(int[] arr, int lo, int hi, int key) {
        this.arr = arr;
        this.lo = lo;
        this.hi = hi;
        this.key = key;
    }

    public long computeSeq() {
        for (int i = lo; i <= hi; ++i) {
            if(arr[i] == key)
                freq ++;
        }
        return freq;
    }

    @Override
    protected void compute() {
        if (hi - lo > 1_000_000) {
            int mid = (lo + hi) / 2;
            FreqElementInArray left = new FreqElementInArray(arr, lo, mid, key);
            FreqElementInArray right = new FreqElementInArray(arr, mid + 1, hi, key);
            left.fork();
            right.compute();
            left.join();
            freq = left.freq + right.freq;
        } else {
            freq = computeSeq();
        }
    }

    public void computeStreamPara() {
        freq = Arrays.stream(arr)
                .asLongStream()
                .parallel()
                .filter(value -> value == key)
                .count();
    }

    public void computeStreamSeq() {
        freq = Arrays.stream(arr)
                .asLongStream()
                .filter(value -> value == key)
                .count();;
    }
}
