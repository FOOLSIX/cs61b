public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> ans = new LinkedListDeque<>();
        int len = word.length();
        for (int i = 0; i < len; ++i) {
            ans.addLast(word.charAt(i));
        }
        return ans;
    }
    public boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            return true;
        }

        Deque<Character> d = wordToDeque(word);
        while (d.size() > 1) {
            char c1 = d.removeFirst();
            char c2 = d.removeLast();
            if (c1 != c2) {
                return false;
            }
        }

        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() <= 1) {
            return true;
        }

        Deque<Character> d = wordToDeque(word);
        while (d.size() > 1) {
            char c1 = d.removeFirst();
            char c2 = d.removeLast();
            if (!cc.equalChars(c1, c2)) {
                return false;
            }
        }

        return true;
    }
}
