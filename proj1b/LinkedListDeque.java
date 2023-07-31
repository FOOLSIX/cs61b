public class LinkedListDeque<Item> implements Deque<Item> {
    private class Node {
        private Item val;
        private Node nxt;
        private  Node pre;
        public Node() {
            nxt = null;
            pre = null;
        }
        public Node(Item v, Node n, Node p) {
            nxt = n;
            pre = p;
            val = v;
        }
    }

    private int size;
    private Node dummy;
    private Node tail;

    public LinkedListDeque() {
        this.size = 0;
        this.dummy = new Node();
        this.tail = new Node();
        this.dummy.nxt =  tail;
        this.tail.pre = dummy;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void addFirst(Item item) {
        this.size += 1;
        Node nextnode = dummy.nxt;
        dummy.nxt = new Node(item, nextnode, dummy);
        nextnode.pre = dummy.nxt;
    }

    @Override
    public void addLast(Item item) {
        this.size += 1;
        Node prenode = tail.pre;
        tail.pre = new Node(item, tail, prenode);
        prenode.nxt = tail.pre;
    }

    @Override
    public void printDeque() {
        if (dummy.nxt == tail) {
            return;
        }
        Node p = dummy;
        for (int i = 0; i < size; ++i) {
            p = p.nxt;
            if (p.val != null) {
                System.out.print(p.val + " ");
            }
        }
        System.out.println();
    }

    @Override
    public Item removeFirst() {
        if (size == 0) {
            return null;
        }
        this.size -= 1;
        Item ret = dummy.nxt.val;
        dummy.nxt.nxt.pre = dummy;
        dummy.nxt = dummy.nxt.nxt;

        return ret;
    }

    @Override
    public Item removeLast() {
        if (size == 0) {
            return null;
        }
        this.size -= 1;
        Item ret = tail.pre.val;
        tail.pre.pre.nxt = tail;
        tail.pre = tail.pre.pre;

        return ret;
    }

    @Override
    public Item get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node p = dummy.nxt;
        for (int i = 0; i < index; ++i) {
            p = p.nxt;
        }

        return p.val;
    }

    private Item recursiver(int index, Node node) {
        if (index == 0) {
            return node.val;
        }

        return recursiver(index - 1, node.nxt);
    }
    public Item getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }

        return recursiver(index, dummy.nxt);
    }

}
