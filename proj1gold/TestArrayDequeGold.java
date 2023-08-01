import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void test1() {
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> d = new StudentArrayDeque<>();
        String meshead = "";
        while (true) {
            int randnum = StdRandom.uniform(100);
            int opt = StdRandom.uniform(4);
            switch (opt) {
                case 0:
                    solution.addLast(randnum);
                    d.addLast(randnum);
                    meshead = meshead.concat("addLast(" + randnum + ")\n");
                    assertEquals(meshead, solution.getLast(), d.get(solution.size() - 1));
                    break;
                case 1:
                    solution.addFirst(randnum);
                    d.addFirst(randnum);
                    meshead = meshead.concat("addFirst(" + randnum + ")\n");
                    assertEquals(meshead, solution.getFirst(), d.get(0));
                    break;
                case 2:
                    if(solution.size() < 1) {
                        break;
                    }
                    Integer s1 = solution.removeFirst();
                    Integer s2 = d.removeFirst();
                    meshead = meshead.concat("removeFirst()\n");
                    assertEquals(meshead, s1, s2);
                    break;
                case 3:
                    if(solution.size() < 1) {
                        break;
                    }
                    Integer s3 = solution.removeLast();
                    Integer s4 = d.removeLast();
                    meshead = meshead.concat("removeLast()\n");
                    assertEquals(meshead, s3, s4);
                    break;
                default:
                    break;
            }
        }
    }
}
