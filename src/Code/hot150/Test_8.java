package Code.hot150;

/**
 * @Description 82. 删除排序链表中的重复元素 II
 * @Author 12919
 * @Date 2025/3/4
 */
//输入：head = [1,2,3,3,4,4,5]
//输出：[1,2,5]
public class Test_8 {

    public static void main(String[] args) {
        Test_8 test_8 = new Test_8();
        test_8.deleteDuplicates(null);
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode pre = new ListNode(0, head);
        ListNode curr = pre;
        while (curr.next != null && curr.next.next != null) {
            if (curr.next.val == curr.next.next.val) {
                int same = curr.next.val;
                while (curr.next != null && curr.next.val == same)
                    curr.next = curr.next.next;
            } else
                curr = curr.next;
        }
        return pre.next;
    }
}
