package test_bgw;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/2
 */
public class test_ABC123 {


    public static final Object lock = new Object();
    public static boolean flag = true;


    public static class object_Dig implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                try {
                    for (int i = 1; i <= 3; i++) {
                        while (!flag) {
                            lock.wait();
                        }
                        System.out.println(i);
                        flag = false;
                        lock.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    public static class object_letter implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                try {
                    for (char c = 'A'; c <= 'C'; c++) {
                        while (flag) {
                            lock.wait();
                        }
                        System.out.println(c);
                        flag = true;
                        lock.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    public static void main(String[] args) {
        Thread thread_Dig = new Thread(new object_Dig());
        Thread thread_letter = new Thread(new object_letter());
        thread_Dig.start();
        thread_letter.start();
    }
}
