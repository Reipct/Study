package Code.hot150;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/4/6
 */
public class Test_35 {

    public static void main(String[] args) {
        int[] num = {2, 2, 1, 1, 2, 2};
        Test_35 test_35 = new Test_35();
        System.out.println(test_35.majorityElement(num));
    }

    public int majorityElement(int[] nums) {
        int flag = 0;
        int count=0;
        for (int i = 0; i < nums.length; i++) {
            if(count==0) {
                flag = nums[i];
                count++;
            }
            else {
                if(flag==nums[i])
                    count++;
                else
                    count--;
            }
        }
        return flag;
    }
}
