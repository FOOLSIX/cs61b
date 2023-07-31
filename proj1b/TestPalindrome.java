import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    } //Uncomment this class once you've created your Palindrome class.

    @Test
    public  void testIsPalindrome() {
        OffByOne odo = new OffByOne();
        assertTrue(palindrome.isPalindrome("assa"));
        assertTrue(palindrome.isPalindrome("qqq"));
        assertFalse(palindrome.isPalindrome("deeaeeed"));
        assertFalse(palindrome.isPalindrome("cat"));

        assertTrue(palindrome.isPalindrome("assa", odo));
        assertTrue(palindrome.isPalindrome("qqq", odo));
        assertFalse(palindrome.isPalindrome("deeaeeed", odo));
        assertFalse(palindrome.isPalindrome("cat", odo));
    }
}
