import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;


public class TestBoggle {
    @Test
    public void testExampleBoard() {
        List<String> actual = Boggle.solve(7, "exampleBoard.txt");
        LinkedList<String> expected = new LinkedList<>();
        expected.add("thumbtacks");
        expected.add("thumbtack");
        expected.add("setbacks");
        expected.add("setback");
        expected.add("ascent");
        expected.add("humane");
        expected.add("smacks");
        assertEquals(expected, actual);
    }
    @Test
    public void testSmallBoard1() {
        List<String> actual = Boggle.solve(7, "smallBoard.txt");

    }
    @Test
    public void testSmallBoard2() {
        List<String> actual = Boggle.solve(7, "smallBoard2.txt");

    }
}
