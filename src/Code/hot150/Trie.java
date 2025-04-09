package Code.hot150;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/3
 */
public class Trie {

    private Trie[] childern;
    private boolean isEnd;

    public Trie() {
        childern = new Trie[26];
        isEnd = false;
    }

    public void insert(String word) {
        Trie node = this;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            if (node.childern[index] == null)
                node.childern[index] = new Trie();
            node = node.childern[index];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {

        Trie node = serachPrefix(word);
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        return serachPrefix(prefix) != null;
    }

    private Trie serachPrefix(String prefix) {
        Trie node = this;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            int index = c - 'a';
            if (node.childern[index] == null)
                return null;
            node = node.childern[index];
        }
        return node;

    }
}
