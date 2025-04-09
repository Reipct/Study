package Code.hot150;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.CompareGenerator;

import java.util.Arrays;
import java.util.Collections;

/**
 * @Description 274. H 指数
 * @Author 12919
 * @Date 2025/3/6
 */
public class Test_12 {

    public static void main(String[] args) {
        int[] citations = {3, 0, 6, 1, 5};
        Test_12 test_12 = new Test_12();
        System.out.println(test_12.hIndex(citations));
    }


    public int hIndex_BiSerach(int[] citations) {
        int len = citations.length;
        int left = 0;
        int right = len;
        while (left < right) {
            int mid = (left + right + 1) / 2;
            int cnt = 0;
            for (int i = 0; i < len; i++) {
                if (citations[i] >= mid)
                    cnt++;
            }
            if (cnt >= mid)
                left = mid;
            else {
                right = mid - 1;
            }
        }
        return left;
    }

    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int len = citations.length;
        int H = 0;
        for (int i = 0; i < len; i++) {
            int n = len - i;
            if (citations[i] >= n)
                return n;
        }
        return H;
    }

}
