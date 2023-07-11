public class ArrayDeque<T> {
    T[] arr;
    public int size;
    public int volume;

    private int nextFirst;
    private int nextLast;

    private static int factor = 2;
    ArrayDeque() {
        size = 0;
        volume = 8;
        nextFirst = 3;
        nextLast = 4;
        arr = (T[]) new Object[8];
    }

    private void resize(int sz){
        int oldv = volume;
        volume = sz;
        T[] temp = (T[]) new Object[sz];
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
    public void addFirst(T item) {
        if (size >= volume - 1) {
            resize(volume * factor);
        }
        size += 1;
        arr[nextFirst] = item;
        if (nextFirst == 0) {
            nextFirst = volume - 1;
        }
        else {
            --nextFirst;
        }
    }

    public void addLast(T item) {
        if (size >= volume - 1) {
            resize(volume * factor);
        }
        size += 1;
        arr[nextLast] = item;
        if (nextLast == volume - 1) {
            nextLast = 0;
        }
        else {
            ++nextLast;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if(size == 0) {
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

    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        size -= 1;
        int idx = nextFirst + 1;
        if (idx == volume) {
            idx = 0;
        }

        nextFirst = idx;
        T ret = arr[idx];
        if (volume>16 && (double)size/volume <= 0.4) {
            resize(volume / factor);
        }

        return ret;

    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        int idx = nextLast - 1;
        if (idx == -1) {
            idx = volume - 1;
        }
        nextLast = idx;
        T ret = arr[idx];
        if (volume>16 && (double)size/volume <= 0.4) {
            resize(volume / factor);
        }

        return ret;
    }

    public T get(int index) {
        if(index < 0 || index >= size) {
            return null;
        }

        return arr[index];
    }
}
