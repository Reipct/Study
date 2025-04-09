package test_bgw;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/2/28
 */
public class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("MyRunnable run");
    }

    public static void main(String[] args) {

        MyRunnable myRunnable = new MyRunnable();
        Thread thread1 = new Thread(myRunnable);
        Thread thread2 = new Thread(myRunnable);
        thread1.start();
        thread2.start();
    }
}
