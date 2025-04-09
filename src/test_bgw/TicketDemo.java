package test_bgw;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/1
 */
public class TicketDemo {

    static Object lock = new Object();
    int nums = 10;

    public void ticket() {
        synchronized (lock) {
            if (nums <= 0) {
                return;
            }
            nums--;
            System.out.println(Thread.currentThread().getName() + "抢到一张票" + "，还剩" + nums);
        }

    }


    public static void main(String[] args) {
        TicketDemo ticketDemo = new TicketDemo();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                ticketDemo.ticket();
            }).start();
        }
    }

}
