import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    
    static CharacterComparator offByOne = new OffByOne();
    @Test
    public void testOffByOne() {
        char c1 = 65535, c2 = (char) 0;
        assertFalse(offByOne.equalChars(c1, c2));
        assertFalse(offByOne.equalChars(c2, c1));
        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('a', 'd'));
        assertFalse(offByOne.equalChars('e', 'b'));
        assertTrue(offByOne.equalChars('c', 'b'));
    }
}
