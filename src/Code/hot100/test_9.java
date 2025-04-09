package Code.hot100;

/**
 * @Description 234.回文链表
 * @Author 12919
 * @Date 2025/2/19
 */
//给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
//输入：head = [1,2,2,1]
//输出：true
public class test_9 {
    public static void main(String[] args) {
        ListNode head = new ListNode();
        ListNode p = head;
        for (int i = 0; i < 2; i++) {
            ListNode q = new ListNode();
            q.val = i;
            q.next = null;
            head = q;
            head = head.next;
        }
        for (int i = 1; i > 0; i--) {
            ListNode q = new ListNode();
            q.val = i;
            q.next = null;
            head = q;
            head = head.next;
        }
        System.out.println(isPalindrome(p));
    }

    public static boolean isPalindrome(ListNode head) {
        if (head == null)
            return false;
        int[] nums = new int[100000];
        int index = 0;
        while (head != null) {
            nums[index++] = head.val;
            head = head.next;
        }
        for (int i = 0; i < index / 2; i++) {
            if (nums[i] != nums[index - i - 1])
                return false;
        }
        return true;
    }
    //反转链表
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }
}

