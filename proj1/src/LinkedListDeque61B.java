import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    // 定义一个双向的链表
    private class Node {

        public Node front;
        public T item;
        public Node next;

        public Node(Node f, T i, Node n) {
            front = f;
            item = i;
            next = n;
        }
    }

    private int size;
    private Node sentinel;

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.front = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**
     * 将 {@code x} 添加到双端队列的前端。假设 {@code x} 永不为 null。
     *
     * @param x 要添加的元素
     */
    @Override
    public void addFirst(T x) {
        if (size == 0) {
            sentinel.next = new Node(sentinel, x, sentinel);
            sentinel.front = sentinel.next;
        } else {
            sentinel.next = new Node(sentinel, x, sentinel.next);
            sentinel.next.next.front = sentinel.next;
        }
        size++;
    }

    /**
     * 将 {@code x} 添加到双端队列的后端。假设 {@code x} 永不为 null。
     *
     * @param x 要添加的元素
     */
    @Override
    public void addLast(T x) {
        sentinel.front.next = new Node(sentinel.front, x, sentinel);
        sentinel.front = sentinel.front.next;
        size++;
    }

    /**
     * 返回双端队列的列表副本。不会修改双端队列。
     *
     * @return 一个新的列表副本
     */
    @Override
    public List<T> toList() {
        List<T> copy = new ArrayList<>();
        if (size == 0) {
            return copy;
        }
        for (Node p = sentinel.next; p != sentinel; p = p.next) {
            copy.add(p.item);
        }
        return copy;
    }

    /**
     * 判断双端队列是否为空。不会修改双端队列。
     *
     * @return 若双端队列无元素则返回 {@code true}，否则返回 {@code false}
     */
    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * 返回双端队列的大小。不会修改双端队列。
     *
     * @return 双端队列中的元素数量
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 移除并返回双端队列前端的元素（若存在）。
     *
     * @return 被移除的元素，若不存在则返回 {@code null}
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T oldvalue = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.front = sentinel;
        size--;
        return oldvalue;
    }

    /**
     * 移除并返回双端队列后端的元素（若存在）。
     *
     * @return 被移除的元素，若不存在则返回 {@code null}
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T oldvalue = sentinel.front.item;
        sentinel.front = sentinel.front.front;
        sentinel.front.next = sentinel;
        size--;
        return oldvalue;
    }

    /**
     * 双端队列 ADT 通常不包含 get 方法，但为了编程练习我们额外添加此方法。
     * 迭代获取元素。索引越界时返回 null。不会修改双端队列。
     *
     * @param index 要获取的索引
     * @return 双端队列中索引为 {@code index} 的元素
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node p = sentinel.next;
        for (; index > 0; index--) {
            p = p.next;
        }
        return p.item;
    }

    /**
     * 此方法本不应存在于接口中，但为了测试方便而添加。递归获取元素。
     * 索引越界时返回 null。不会修改双端队列。
     *
     * @param index 要获取的索引
     * @return 双端队列中索引为 {@code index} 的元素
     */
    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(index, sentinel);
    }

    private T getRecursiveHelper(int index, Node currentNode) {
        Node NextNode = currentNode.next;
        if (index == 0) {
            return NextNode.item;
        }
        return getRecursiveHelper(index - 1, NextNode);
    }

}