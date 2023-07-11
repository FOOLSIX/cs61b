public class LinkedListDeque<T> {
    public class Node {
        public T val;
        public  Node nxt;
        public  Node pre;
        Node() {
            this.nxt = null;
            this.pre = null;
        }
        Node(T v, Node n, Node p) {
            this.nxt = n;
            this.pre = p;
            this.val = v;
        }
    }

    private int size;
    private Node dummy;
    private Node tail;

    LinkedListDeque() {
        this.size = 0;
        this.dummy = new Node();
        this.tail = new Node();
        this.dummy.nxt =  tail;
        this.tail.nxt = dummy;
    }

    LinkedListDeque(T val) {
        this.size = 1;
        this.dummy = new Node();
        this.tail = new Node();
        Node node = new Node(val, dummy, tail);
        this.tail.nxt = dummy;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(T item) {
        this.size += 1;
        dummy.nxt = new Node(item, dummy.nxt, null);
    }

    public void addLast(T item) {
        this.size += 1;
        tail.pre = new Node(item, null, tail.pre);
    }

    public void printDeque(){
        if(dummy.nxt == tail) {
            return;
        }
        Node p = dummy;
        for(int i = 0; i < size; ++i) {
            p = p.nxt;
            if(p.val != null) {
                System.out.print(p.val + " ");
            }
        }
        System.out.println();
    }

    public T removeFirst() {
        if(size == 0) {
            return null;
        }
        this.size -= 1;
        T ret = dummy.nxt.val;
        dummy.nxt = dummy.nxt.nxt;

        return ret;
    }

    public T removeLast() {
        if(size == 0) {
            return null;
        }
        this.size -= 1;
        T ret = tail.pre.val;
        tail.pre = tail.pre.pre;

        return ret;
    }

    public T get(int index) {
        if(index >= size || index <0) {
            return null;
        }
        Node p = dummy.nxt;
        for(int i = 0; i < index; ++i) {
            p = p.nxt;
        }

        return p.val;
    }

    public T Recursiver(int index, Node node) {
        if (index == 0) {
            return node.val;
        }

        return Recursiver(index - 1, node.nxt);
    }
    public T getRecursive(int index) {
        if (index >= size || index <0) {
            return null;
        }

        return Recursiver(index, dummy.nxt);
    }

}
