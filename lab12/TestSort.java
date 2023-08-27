import edu.princeton.cs.algs4.Queue;
import org.junit.Test;

public class TestSort {
    @Test
    public void testMergeSort() {
        Queue<Integer> t = new Queue<>();
        t.enqueue(9);
        t.enqueue(5);
        t.enqueue(6);
        t.enqueue(1);
        t.enqueue(1);
        t.enqueue(1);
        t.enqueue(1);
        Queue<Integer> l = MergeSort.mergeSort(t);
        for (int num : l) {
            System.out.print(num + " ");
        }
        System.out.print("\n" + t.size());
    }
    @Test
    public void testQuickSort() {
        Queue<Integer> t = new Queue<>();
        t.enqueue(9);
        t.enqueue(5);
        t.enqueue(6);
        t.enqueue(1);
        t.enqueue(1);
        t.enqueue(1);
        t.enqueue(1);
        Queue<Integer> l = QuickSort.quickSort(t);
        for (int num : l) {
            System.out.print(num + " ");
        }
        System.out.print("\n" + t.size());
    }
}
