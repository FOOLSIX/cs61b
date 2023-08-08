package synthesizer;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        arb.enqueue(1);
        arb.enqueue(10);
        arb.enqueue(100);
        arb.enqueue(1000);
        assertTrue(arb.isFull());
        assertEquals(1, (long) arb.dequeue());
        assertEquals(10, (long) arb.dequeue());
        assertEquals(100, (long) arb.dequeue());
        assertEquals(1000, (long) arb.dequeue());
        assertTrue(arb.isEmpty());
        arb.enqueue(1);
        arb.enqueue(10);
        arb.enqueue(100);
        arb.enqueue(1000);
        assertEquals(1, (long) arb.dequeue());
        assertEquals(10, (long) arb.dequeue());
        assertEquals(100, (long) arb.dequeue());
        assertEquals(1000, (long) arb.dequeue());
    }
    @Test
    public void iteratorTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        arb.enqueue(1);
        arb.enqueue(10);
        arb.enqueue(100);
        arb.enqueue(1000);
        int idx = 0;
        for (Integer n : arb) {
            System.out.println(n);
        }

        System.out.println("IteratorTest!");
    }
    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
