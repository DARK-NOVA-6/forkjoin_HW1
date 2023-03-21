import junit.framework.TestCase;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class FreqElementInArrayTest extends TestCase {
    private int seed = 10;
    private int[] randomArray(int size) {
        Random rand = new Random(seed);
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(size);
        }
        /* Check  random array
        Arrays.stream(arr).forEach(System.out::println);*/
        return arr;
    }

    private int[] intStream(int size) {
        IntStream intStream = IntStream.range(1, size);
        return intStream.toArray();
    }

    public void testArrayFreqSeq() {
        Random rand = new Random(seed);
        int size = 1000_000_000;
        int []arr = randomArray(size);
        int key = rand.nextInt(size);
        FreqElementInArray array = new FreqElementInArray(arr, 0, arr.length - 1, key);
        long start = System.currentTimeMillis();
        long freq = array.computeSeq();
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Sequential Time execution for Random Array of size %d is %d ms freq of %d is %d\n",
                size, endTimer, key, freq);
//        assertEquals(15,res);
    }

    public void testArrayFreqPara() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","7");

        Random rand = new Random(seed);
        int size = 1000_000_000;
        int []arr = randomArray(size);
        int key = rand.nextInt(size);
        FreqElementInArray array = new FreqElementInArray(arr, 0, arr.length - 1, key);
        long start = System.currentTimeMillis();
        ForkJoinPool.commonPool().invoke(array);
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Parallel Time execution for Random Array of size %d is %d ms freq of %d is %d\n",
                size, endTimer, key, array.freq);
//        assertEquals(15,res);
    }

    public void testArrayFreqStreamSeq() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","7");

        Random rand = new Random(seed);
        int size = 1000_000_000;
        int []arr = randomArray(size);
        int key = rand.nextInt(size);
        FreqElementInArray array = new FreqElementInArray(arr, 0, arr.length - 1, key);
        long start = System.currentTimeMillis();
        array.computeStreamSeq();
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Sequential Stream Time execution for Random Array of size %d is %d ms freq of %d is %d\n",
                size, endTimer, key, array.freq);
//        assertEquals(15,res);
    }

    public void testArrayFreqStreamPara() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","7");

        Random rand = new Random(seed);
        int size = 1000_000_000;
        int []arr = randomArray(size);
        int key = rand.nextInt(size);
        FreqElementInArray array = new FreqElementInArray(arr, 0, arr.length - 1, key);
        long start = System.currentTimeMillis();
        array.computeStreamPara();
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Parallel Stream Time execution for Random Array of size %d is %d ms freq of %d is %d\n",
                size, endTimer, key, array.freq);
//        assertEquals(15,res);
    }

    public void resource() {
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(ForkJoinPool.commonPool().getParallelism());
    }

}
