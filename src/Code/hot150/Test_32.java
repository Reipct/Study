package Code.hot150;

/**
 * @Description 79. 单词搜索
 * @Author 12919
 * @Date 2025/3/19
 */
public class Test_32 {
    public static void main(String[] args) {
        char[][] board = {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        String word = "ABCCED";
        Test_32 test_32 = new Test_32();
        System.out.println(test_32.exist(board, word));
    }


    public boolean exist(char[][] board, String word) {
        char[] w = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, w, i, j, 0))
                    return true;
            }
        }
        return false;

    }

    boolean dfs(char[][] board, char[] word, int i, int j, int k) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != word[k]) return false;
        if (k == word.length - 1) return true;
        board[i][j] = '\0';
        boolean res = dfs(board, word, i - 1, j, k + 1)
                || dfs(board, word, i + 1, j, k + 1)
                || dfs(board, word, i, j - 1, k + 1)
                || dfs(board, word, i, j + 1, k + 1);
        board[i][j] = word[k];
        return res;
    }
}
