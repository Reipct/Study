package Code.hot150;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description 36. 有效的数独
 * @Author 12919
 * @Date 2025/3/2
 */
public class Test_3 {

    public static void main(String[] args) {
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        Test_3 test_3 = new Test_3();
        ;
        System.out.println(test_3.isValidSudoku(board));
    }

    public boolean isValidSudoku(char[][] board) {
        int[] row = new int[9];
        int[] col = new int[9];
        int[] box = new int[9];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                char c = board[i][j];
                if (c == '.')
                    continue;
                int x = c - '0';
                int idx = i / 3 * 3 + j / 3;
                int mask = 1 << x;
                if (((row[i] & mask) != 0) || ((col[j] & mask) != 0) || ((box[idx] & mask) != 0))
                    return false;
                row[i] |= mask;
                col[j] |= mask;
                box[idx] |= mask;
            }
        }
        return true;
    }
}
