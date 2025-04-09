package Code.hot100;

/**
 * @Description 206. 反转链表
 * @Author 12919
 * @Date 2025/3/1
 */
public class test_26 {


    public ListNode reverseList(ListNode head) {
        ListNode p = null;
        while (head != null) {
            ListNode q = head.next;
            head.next = p;
            p = head;
            head = q;
        }
        return p;
    }


}
