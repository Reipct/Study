package Code.hot150;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/4/10
 */
public class Test_37 {
    public static void main(String[] args) {
        Test_37 test_37 = new Test_37();
        int[] gas = {5, 5, 1, 3, 4};
        int[] cost = {8, 1, 7, 1, 1};
        System.out.println(test_37.canCompleteCircuit_pro(gas, cost));

    }

    public int canCompleteCircuit_pro(int[] gas, int[] cost) {
        int len = gas.length;
        int sum = 0;
        int MinPart = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < len; i++) {
            sum += gas[i] - cost[i];
            if (sum < MinPart) {
                MinPart = sum;
                index = i;
            }
        }
        return sum < 0 ? -1 : (index + 1) % len;
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int len = gas.length;
        int sum = 0;
        for (int i = 0; i < len; i++) {
            gas[i] -= cost[i];
            sum += gas[i];
        }
        if (sum < 0)
            return -1;
        int part = 0;
        int index = 0;
        for (int i = 0; i < len; i++) {
            part += gas[i];
            if (part <= 0) {
                index = i + 1;
                part = 0;
            }
        }
        return index == len ? 0 : index;
    }

}
