import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    OffByN tes = new OffByN(1);
    @Test
    public void testEqualCharBy1() {
        assertTrue(tes.equalChars('a', 'b'));
        assertFalse(tes.equalChars('a', 'd'));
    }
    @Test
    public void testEqualCharBy5() {
        OffByN offBy5 = new OffByN(5);
        assertTrue(offBy5.equalChars('a', 'f'));  // true
        assertTrue(offBy5.equalChars('f', 'a'));  // true
        assertFalse(offBy5.equalChars('f', 'h'));  // false
    }
}
