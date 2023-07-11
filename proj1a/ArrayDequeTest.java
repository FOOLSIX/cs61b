public class ArrayDequeTest {
    public static void main(String[] args) {
        ArrayDeque<Integer> tes = new ArrayDeque<>();
        tes.printDeque();
        for(int i=0;i<64;++i) {
            tes.addLast(i);
            tes.addFirst(i);
        }
        tes.printDeque();
        for(int i=0;i<62;++i) {
            tes.removeLast();
            tes.removeFirst();
        }
        tes.printDeque();
    }
}
