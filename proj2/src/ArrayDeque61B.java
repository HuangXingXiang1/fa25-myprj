import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T>{

    private T[] content;
    private int nextFirst;
    private int nextLast;
    private int size;
    public int length;

    public ArrayDeque61B() {
        content = (T[]) new Object[8];
        nextFirst = 7;
        nextLast = 0;
        size = 0;
    }

    private class WrapIterator implements Iterator<T>{
        private int current = 0;
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return current < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            int index = (nextFirst+1+current) % content.length;
            current++;
            return content[index];
        }
    }
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        if (size == content.length){
            expansion();
        }
        content[nextFirst] = x;
        nextFirst = nexthelper(nextFirst -1);
        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        if (size == content.length){
            expansion();
        }
        content[nextLast] = x;
        nextLast = nexthelper(nextLast+1);
        size++;
    }

    //扩容
    private void expansion() {
        T[] expansion = (T[]) new Object[size * 2];
        length = expansion.length;
        for (int i = 0; i < size; i++) {
            // 尾部开始添加原数组的首项直到填充完毕
            // 保证在逻辑上是循环数组， 并且以顺时针为开头访问
            expansion[(expansion.length-1+i) % expansion.length] = content[(nextFirst +1+i) % content.length];
        }
        // 新的首位指针会位于第一位之前
        nextFirst = expansion.length-2;
        // 新的末尾指针会位于最后一位之后
        nextLast = size - 1;
        content = expansion;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnlist = new ArrayList<>();
        if (size == 0){
            return returnlist;
        }
        for(int i = 0; i < size; i++){
            T element = content[(nextFirst +1+i) % content.length];
            returnlist.addLast(element);
        }
        return returnlist;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
        return null;
        }
        int tempnext = nexthelper(nextFirst +1);
        return removehelper(tempnext, 1);
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
        return null;
        }
        int templast = nexthelper(nextLast - 1);
        return removehelper(templast, 0);
    }

    private T removehelper(int templast, int stip) {
        T deletvalue = content[templast];
        content[templast] = null;
        if (stip == 1) {
            nextFirst = templast;
        }else {
            nextLast = templast;
        }
        size--;
        float ratio = ( (float) size / content.length);
        if (ratio <= 0.25 && content.length >= 16){
            shrinkage();
        }
        return deletvalue;
    }

    private void shrinkage() {
        int shrinkage = (int) ((size / 0.30));
        T [] container;
        container = (T[]) new Object[shrinkage];
        for (int i = 0; i < size; i++) {
            container[(container.length-1+i) % container.length] = content[(nextFirst +1+i) % content.length];
        }
        // 首部指针永远位于首值之前
        nextFirst = container.length-2;
        // 尾部指针永远位于尾部之后
        nextLast = size - 1;
        content = container;
        length = content.length;
    }

    // 循环辅助函数：处理索引越界
    private int nexthelper(int index) {
        if (index == content.length) {
            return 0;
        }
        if (index < 0) {
            return content.length - 1;
        }
        return index;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size){
            return null;
        }
        return content[(index+ nextFirst +1) % content.length];
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new WrapIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof ArrayDeque61B updateo) {
            if (size != updateo.size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (get(i) != updateo.get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        int count = 0;
        StringBuilder display = new StringBuilder();
        display.append("[");
        for (T x : this) {
            count++;
            display.append(x);
            if (count != size) {
                display.append(", ");
            }
        }
        display.append("]");
        return display.toString();
    }
}
