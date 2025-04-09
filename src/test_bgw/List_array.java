package test_bgw;

import java.util.Arrays;
import java.util.List;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/2/28
 */
public class List_array {

    public static void main(String[] args) {

        String[] str = new String[]{"a", "b", "c"};
        List<String> list = Arrays.asList(str);

        String[] str1=list.toArray(new String[list.size()]);

    }


}
