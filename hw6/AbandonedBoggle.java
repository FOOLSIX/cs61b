import java.util.LinkedList;
import java.util.List;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashSet;

public class AbandonedBoggle {

    // File path of dictionary file
    static String dictPath = "words.txt";
    private static class SearchNode {
        int x;
        int y;
        String val;
        boolean[][] mark;
        public SearchNode(String str, int posX, int posY, boolean[][] mk) {
            val = str;
            x = posX;
            y = posY;
            mark = new boolean[mk.length][mk[0].length];
            for (int i = 0; i < mk.length; ++i) {
                System.arraycopy(mk[i], 0, mark[i], 0, mk[0].length);
            }
        }
    }
    private static final int[][] NEXT = {{0, 1}, {1, 0}, {0, -1}, {-1, 0},
                                         {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    private static final Comparator<String> BOGGLE_COMPARATOR = (str1, str2) -> {
        int lengthComparison = Integer.compare(str1.length(), str2.length());
        if (lengthComparison == 0) {
            return str1.compareTo(str2);
        }
        return -lengthComparison;
    };
    /**
     * Solves a Boggle puzzle.
     *
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        if (k <= 0) {
            throw new IllegalArgumentException();
        }
        In in = new In(dictPath);
        In boardIn = new In(boardFilePath);
        PriorityQueue<String> ans = new PriorityQueue<>(BOGGLE_COMPARATOR);
        HashSet<String> ansSet = new HashSet<>();
        char[][] board;
        String[] strings = boardIn.readAllStrings();
        int m = strings.length;
        int n = strings[0].length();
        board = new char[m][];
        for (int i = 0; i < m; ++i) {
            if (strings[i].length() != n) {
                throw new IllegalArgumentException();
            }
            board[i] = strings[i].toCharArray();
        }
        Trie mytrie = new Trie();
        for (String s : in.readAllStrings()) {
            if (s.length() > 2) {
                mytrie.add(s);
            }
        }
        PriorityQueue<SearchNode> pq =
                new PriorityQueue<>(Comparator.comparingInt(node -> -node.val.length()));
        //initialize

        boolean[][] mk = new boolean[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                char ch = board[i][j];
                String string = String.valueOf(ch);
                if (mytrie.contain(string)) {
                    mk[i][j] = true;
                    pq.add(new SearchNode(string, i, j, mk));
                    mk[i][j] = false;
                }
            }
        }

        while (!pq.isEmpty()) {
            SearchNode node = pq.remove();

            if (mytrie.containWord(node.val) && !ansSet.contains(node.val)) {
                ans.add(node.val);
                ansSet.add(node.val);
            }

            for (int i = 0; i < 8; ++i) {
                int tx = node.x + NEXT[i][0];
                int ty = node.y + NEXT[i][1];
                if (tx >= 0 && ty >= 0 && tx < m && ty < n) {
                    String nextString = node.val + board[tx][ty];
                    if (!node.mark[tx][ty] && mytrie.contain(nextString)) {
                        SearchNode nextNode = new SearchNode(nextString, tx, ty, node.mark);
                        nextNode.mark[tx][ty] = true;
                        pq.add(nextNode);
                    }
                }
            }
        }
        LinkedList<String> ret = new LinkedList<>();

        while (!ans.isEmpty() && ret.size() < k) {
            ret.add(ans.remove());
        }

        return ret;
    }
}
