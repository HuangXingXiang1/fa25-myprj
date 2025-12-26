import edu.princeton.cs.algs4.In;

public class IntList {
    int first;
    IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Return the size of the list using... recursion! */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return 1 + this.rest.size();
    }

    /** Return the size of the list using no recursion! */
    public int iterativeSize() {
        IntList p = this;
        int totalSize = 0;
        while (p != null) {
            totalSize += 1;
            p = p.rest;
        }
        return totalSize;
    }

    /** Returns the ith item of this IntList. */
    public int get(int i) {
        if (i == 0) {
            return first;
        }
        return rest.get(i - 1);
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. L is not allowed
     * to change. Must use recursion.
     * This method is non-destructive, i.e. it must not modify the original list.
     */
    public static IntList incrRecursiveNondestructive(IntList L, int x) {
       if (L == null){
           return null;
       }
       IntList W = new IntList(L.first + x, incrRecursiveNondestructive(L.rest, x));
       return W;
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. Modifies the original list.
     * You are not allowed to use "new" in this method.
     */
    public static IntList incrRecursiveDestructive(IntList L, int x) {
        if (L == null){
            return null;
        }
        L.first = L.first + x;
        incrRecursiveDestructive(L.rest, x);
        return L;
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. Not allowed
     * to use recursion. May not modify the original list.
     */
    public static IntList incrIterativeNondestructive(IntList L, int x) {
        IntList W = new IntList(L.first + x, null);
        IntList p1 = W;
        for (IntList p = L.rest; p != null; p = p.rest ){
            p1.rest = new IntList(p.first + x, null);
            p1 = p1.rest;
        }
        return W;
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. Not allowed
     * to use recursion. Modifies the original list.
     * You are not allowed to use "new" in this method.
     */
    public static IntList incrIterativeDestructive(IntList L, int x) {
        for (IntList p = L; p != null; p = p.rest){
            p.first = p.first + x;
        }
        return L;
    }

    /**
     * Returns an IntList consisting of the elements of L1 followed by the
     * elements of L2.
     */
    public static IntList concatenate(IntList L1, IntList L2) {
        if (L1 == null){
            if (L2 == null){
                return null;
            }
            IntList W = new IntList(L2.first, concatenate(L1, L2.rest));
            return W;
        }
        IntList W = new IntList(L1.first, concatenate(L1.rest, L2));
        return W;
    }

    /*
     * =================================================================
     * OPTIONAL METHODS
     * =================================================================
     */

    /**
     * Returns the sum of all elements in the IntList.
     */
    public int sum() {
        int sum = 0;
        for (IntList p = this; p != null; p = p.rest){
            sum += p.first;
        }
        return sum;
    }

    /**
     * Destructively adds x to the end of the list.
     */
    public void addLast(int x) {
        if (rest == null){
            rest = new IntList(x, null);
            return;
        }
        rest.addLast(x);
    }

    /**
     * Destructively adds x to the front of this IntList.
     * This is a bit tricky to implement. The standard way to do this would be
     * to return a new IntList, but for practice, this implementation should
     * be destructive.
     */
    public void addFirst(int x) {
        rest = new IntList(first, rest);
        first = x;
    }
}
