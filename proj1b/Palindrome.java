public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> ans = new LinkedListDeque<>();
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
        while (d.size() >= 2) {
            if (d.removeFirst() != d.removeLast()) {
                return false;
            }
        }

        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if(word.length() <= 1) {
            return true;
        }

        Deque<Character> d = wordToDeque(word);
        while(d.size() >= 2) {
            if(!cc.equalChars(d.removeFirst(), d.removeLast())) {
                return false;
            }
        }

        return true;
    }
}
