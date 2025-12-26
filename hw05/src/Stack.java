public class Stack {
    // 定义静态的内部类，链表的核心
    public static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    // 哨兵值
    private IntNode sentinel;
    private int size;
    private int sum;

    // 初始化栈
    public Stack() {
        sentinel = new IntNode(141, null);
        size = 0;
        sum = 0;
    }

    // 压栈
    public void push(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
        size++;
        sum += x;
    }

    // 弹栈
    public int pop() {
        int stack_top = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        size--;
        sum -= stack_top;
        return stack_top;
    }

    // 返回大小
    public int size() {
        return size;
    }

    // 返回总数
    public int sum() {
        return sum;
    }

    // 索引取值
    public int get(int x) {
        IntNode p = sentinel.next;
        for (; x > 0; x--) {
            p = p.next;
        }
        return p.item;
    }

    public static void main(String[] args) {
        Stack o = new Stack();

        long star_1 = System.currentTimeMillis();
        o.push(1823);
        long end_1 = System.currentTimeMillis();
        System.out.println(end_1 - star_1);

        long star = System.currentTimeMillis();

        for (int i = 0; i < 10000000; i++) {
            o.push(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - star);
        long star_0 = System.currentTimeMillis();
        o.push(183);
        long end_0 = System.currentTimeMillis();
        System.out.println(end_0 - star_0);
    }
}
