package test_bgw;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/2/28
 */
public class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("MyThread run");
    }

    public static void main(String[] args) {
        MyThread mythread1 = new MyThread();
        MyThread mythread2 = new MyThread();
        mythread1.start();
        mythread2.start();

    }
}
