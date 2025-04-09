package test_bgw;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/2/28
 */
public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("MyCallable run");
        return "ok";
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable myCallable = new MyCallable();

        FutureTask<String> futureTask = new FutureTask<String>(myCallable);

        Thread thread1 = new Thread(futureTask);
        Thread thread2 = new Thread(futureTask);
        thread1.start();
        thread2.start();

    }


}
