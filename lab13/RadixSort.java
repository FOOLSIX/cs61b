/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        String[] sorted = asciis.clone();
        int len = 0;
        for (String s : asciis) {
            len = Math.max(len, s.length());
        }
        for (int i = len - 1; i >= 0; --i) {
            int[] bucket = new int[256];
            for (String s : asciis) {
                char c = i < s.length() ? s.charAt(i) : 0;
                bucket[c]++;
            }
            for (int j = 1; j < 256; ++j) {
                bucket[j] += bucket[j - 1];
            }
            sorted = lsdHelper(sorted, bucket, i);
        }
        return sorted;
    }
    private static String[] lsdHelper(String[] asciis, int[] bucket, int i) {
        String[] sorted = new String[asciis.length];
        for (int j = asciis.length - 1; j >= 0; --j) {
            char c = i < asciis[j].length() ? asciis[j].charAt(i) : 0;
            sorted[--bucket[c]] = asciis[j];
        }
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
