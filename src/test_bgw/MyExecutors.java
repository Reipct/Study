package test_bgw;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/2/28
 */
public class MyExecutors implements Runnable {

    @Override
    public void run() {
        System.out.println("MyRunnable run");
    }

    public static void main(String[] args) {
        ExecutorService threadPool= Executors.newFixedThreadPool(3);
        threadPool.submit(new MyExecutors());
        threadPool.shutdown();
    }
}