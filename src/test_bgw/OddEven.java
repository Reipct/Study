package test_bgw;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/2/28
 */
public class OddEven {

    public static final Object lock = new Object();
    private static int number = 1;
    private static final int MAX_NUMBER = 100;

    public static void main(String[] args) {
        Thread OddThread = new Thread(new OddPrinter());
        Thread EvenThread = new Thread(new EvenPrinter());

        OddThread.start();
        EvenThread.start();
    }


    static class OddPrinter implements Runnable {
        @Override
        public void run() {
            while (number <= MAX_NUMBER) {
                synchronized (lock) {
                    if (number % 2 == 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println(number);
                        number++;
                        lock.notify();
                    }
                }
            }
        }
    }

    static class EvenPrinter implements Runnable {
        @Override
        public void run() {
            while (number <= MAX_NUMBER) {
                synchronized (lock) {
                    if (number % 2 == 1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println(number);
                        number++;
                        lock.notify();
                    }
                }
            }
        }
    }
}