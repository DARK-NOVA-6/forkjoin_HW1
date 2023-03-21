import java.util.HashMap;
import java.util.concurrent.RecursiveTask;


/*
However, besides being a dumb way to compute Fibonacci functions (there is a simple fast linear algorithm that you'd use in practice),
 this is likely to perform poorly because the smallest subtasks are too small to be worthwhile splitting up.
 Instead, as is the case for nearly all fork/join applications,
 you'd pick some minimum granularity size (for example 10 here) for which you always sequentially solve rather than subdividing.
 */

public class Fibonacci extends RecursiveTask<Integer> {
    final int n;
    private final HashMap<Integer,Integer> memo;
    public Fibonacci(int n) {
        this.n = n;
        this.memo = new HashMap<>();
    }

    public Integer compute() {
        if (n <= 1)
            return n;

        // Check if we have already computed the result for this n
        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        int result;
        if(n > 20) {
            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            result = f2.compute() + f1.join();
        }else{
            result = computeSeq();
        }
        // Store the result in the memo for future use
        memo.put(n, result);

        return result;
    }

    public Integer computeSeq() {
        if (n <= 1)
            return n;

        // Check if we have already computed the result for this n
        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        Fibonacci f1 = new Fibonacci(n - 1);
        Fibonacci f2 = new Fibonacci(n - 2);

        int result = f2.computeSeq() + f1.computeSeq();
        // Store the result in the memo for future use
        memo.put(n, result);

        return result;
    }

}
