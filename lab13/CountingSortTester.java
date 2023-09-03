import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;

public class CountingSortTester {

    /**
     * Array that will cause CountingSort.naiveCountingSort to fail, but
     * CountingSort.betterCountingSort can handle.
     **/
    private static int[] someNegative = {9, 5, -4, 2, 1, -2, 5, 3, 0, -2, 3, 1, 1};

    /**
     * Array that both sorts should sort successfully.
     **/
    private static int[] nonNegative = {9, 5, 2, 1, 5, 3, 0, 3, 1, 1};

    private static int[] allNegative = {-9, -5, -2, -1, -5, -3, -3, -1, -1};


    public static void assertIsSorted(int[] a) {
        int previous = Integer.MIN_VALUE;
        for (int x : a) {
            assertTrue(x >= previous);
            previous = x;
        }
    }

    @Test
    public void testNaiveWithNonNegative() {
        int[] sortedNonNegative = CountingSort.naiveCountingSort(nonNegative);
        assertIsSorted(sortedNonNegative);
    }

    // This test should PASS to indicate that the naive solution is in fact WRONG
    @Test
    public void testNaiveWithSomeNegative() {
        try {
            int[] sortedSomeNegative = CountingSort.naiveCountingSort(someNegative);
            assertTrue("Naive counting sort should not sort arrays with negative numbers.",
                    false);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Great! Exception happened as we expected,"
                    + "since this sort is broken for negative numbers.");
        }
    }

    @Test
    public void testBetterWithNonNegative() {
        int[] sortedNonNegative = CountingSort.betterCountingSort(nonNegative);
        assertIsSorted(sortedNonNegative);
    }

    @Test
    public void testBetterWithSomeNegative() {
        int[] sortedSomeNegative = CountingSort.betterCountingSort(someNegative);
        assertIsSorted(sortedSomeNegative);
    }
    @Test
    public void testBetterWithAllNegative() {
        int[] sortedSomeNegative = CountingSort.betterCountingSort(allNegative);
        assertIsSorted(sortedSomeNegative);
    }

    @Test
    public void testLSD1() {
        String[] tes = new String[]{"", "\0", "ÿ"};
        String[] actual = RadixSort.sort(tes);
        for (String s : actual) {
            System.out.println(s);
        }
        Arrays.sort(tes);
        assertArrayEquals(tes, actual);
    }
    @Test
    public void testLSD2() {
        String[] tes = new String[]{};
        String[] actual = RadixSort.sort(tes);
        for (String s : actual) {
            System.out.println(s);
        }
        Arrays.sort(tes);
        assertArrayEquals(tes, actual);
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(CountingSortTester.class);
    }
}
