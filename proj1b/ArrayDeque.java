public class ArrayDeque<Item> implements Deque<Item> {
    private Item[] arr;
    private int size;
    private int volume;

    private int nextFirst;
    private int nextLast;

    private static int factor = 2;
    private final int minv = 16;
    private final double loadfactor = 0.4;
    public ArrayDeque() {
        size = 0;
        volume = 8;
        nextFirst = 3;
        nextLast = 4;
        arr = (Item[]) new Object[8];
    }

    private void resize(int sz) {
        int oldv = volume;
        volume = sz;
        Item[] temp = (Item[]) new Object[sz];
        int i = nextFirst + 1, idx = 2;
        nextFirst = idx - 1;

        for (int time = 0; time < size; ++time) {
            if (i == oldv) {
                i = 0;
            }
            temp[idx++] = arr[i];
            ++i;
        }
        nextLast = idx;
        arr = temp;
    }
    @Override
    public void addFirst(Item item) {
        if (size >= volume - 1) {
            resize(volume * factor);
        }
        size += 1;
        arr[nextFirst] = item;
        if (nextFirst == 0) {
            nextFirst = volume - 1;
        } else {
            --nextFirst;
        }
    }

    @Override
    public void addLast(Item item) {
        if (size >= volume - 1) {
            resize(volume * factor);
        }
        size += 1;
        arr[nextLast] = item;
        if (nextLast == volume - 1) {
            nextLast = 0;
        } else {
            ++nextLast;
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (size == 0) {
            return;
        }
        int i = nextFirst + 1;
        if (i == volume) {
            i = 0;
        }

        while (i != nextLast) {
            System.out.print(arr[i] + " ");
            ++i;
            if (i == volume) {
                i = 0;
            }
        }
        System.out.println();
    }

    @Override
    public Item removeFirst() {
        if (size == 0) {
            return null;
        }

        size -= 1;
        int idx = nextFirst + 1;
        if (idx == volume) {
            idx = 0;
        }

        nextFirst = idx;
        Item ret = arr[idx];
        if (volume > minv && (double) size / volume <= loadfactor) {
            resize(volume / factor);
        }

        return ret;

    }

    @Override
    public Item removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        int idx = nextLast - 1;
        if (idx == -1) {
            idx = volume - 1;
        }
        nextLast = idx;
        Item ret = arr[idx];
        if (volume > minv && (double) size / volume <= loadfactor) {
            resize(volume / factor);
        }

        return ret;
    }

    @Override
    public Item get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int idx = nextFirst + 1 + index;
        if (idx >= volume) {
            idx -= volume;
        }
        return arr[idx];
    }
}
