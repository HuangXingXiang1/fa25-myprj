public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    /* 树的根节点 */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * 创建一个红黑树节点，节点存储 ITEM，节点颜色由 ISBLACK 决定。
         *
         * @param isBlack
         * 节点是否为黑色（true 表示黑色，false 表示红色）
         *
         * @param item
         * 节点存储的数据
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * 创建一个红黑树节点，包含：
         * ITEM 作为节点数据，
         * ISBLACK 指定节点颜色，
         * LEFT 作为左子节点，
         * RIGHT 作为右子节点。
         *
         * @param isBlack
         * 节点是否为黑色
         *
         * @param item
         * 节点存储的数据
         *
         * @param left
         * 左子节点
         *
         * @param right
         * 右子节点
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     * 创建一个空的红黑树。
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * 翻转当前节点及其两个子节点的颜色。
     * 假设 NODE 一定同时拥有左子节点和右子节点。
     *
     * @param node
     * 需要进行颜色翻转的节点
     */
    void flipColors(RBTreeNode<T> node) {
       node.isBlack = !node.isBlack;
       node.left.isBlack = !node.left.isBlack;
       node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * 对给定节点执行右旋操作，并返回旋转后子树的新根节点。
     * 在该实现中，需要交换新根节点和旧根节点的颜色。
     *
     * @param node
     * 需要进行右旋的节点
     *
     * @return
     * 旋转后子树的新根节点
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> newFatherNode = node.left;
        RBTreeNode<T> originalRightNode = newFatherNode.right;
        newFatherNode.right = node;
        node.left = originalRightNode;
        // 交换颜色后返回
        boolean template = node.isBlack;
        node.isBlack = newFatherNode.isBlack;
        newFatherNode.isBlack = template;
        return newFatherNode;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * 对给定节点执行左旋操作，并返回旋转后子树的新根节点。
     * 在该实现中，需要交换新根节点和旧根节点的颜色。
     *
     * @param node
     * 需要进行左旋的节点
     *
     * @return
     * 旋转后子树的新根节点
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> newFatherNode = node.right;
        RBTreeNode<T> originalLeftNode = newFatherNode.left;
        newFatherNode.left = node;
        node.right = originalLeftNode;
        // 交换颜色后返回
        boolean template = node.isBlack;
        node.isBlack = newFatherNode.isBlack;
        newFatherNode.isBlack = template;
        return newFatherNode;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * 辅助方法：判断给定节点是否为红色。
     * 注意：null 节点（即空子节点或叶子外部节点）默认视为黑色。
     *
     * @param node
     * 需要判断的节点
     *
     * @return
     * 如果节点为红色返回 true，否则返回 false
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * 将元素 item 插入红黑树，并确保最终根节点为黑色。
     *
     * @param item
     * 需要插入的元素
     */
    public void insert(T item) {
        root = insertHelper(root, item);
        root.isBlack = true;
    }

    /**
     * Helper method to insert the item into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * 插入操作的辅助方法。
     * 注释已经将问题拆分为多个步骤，每一步都对应红黑树插入中的一种情况。
     * 需要根据当前节点结构判断应该执行的操作。
     * 同时需要结合本类中的其他方法一起完成实现。
     *
     * @param node
     * 当前递归处理的节点
     *
     * @param item
     * 需要插入的元素
     *
     * @return
     * 插入并调整后的子树根节点
     */
    private RBTreeNode<T> insertHelper(RBTreeNode<T> node, T item) {
        if (node == null) {
            return new RBTreeNode<>(false, item, null, null);
        }

        if (node.item.compareTo(item) < 0) {
            node.right = insertHelper(node.right, item);
        }

        if (node.item.compareTo(item) > 0) {
            node.left = insertHelper(node.left, item);
        }

        // 修正-新插入节点位于右节点
        if (isRed(node.right) && !isRed(node.left)){
            node = rotateLeft(node);
        }

        // 修正-连续的红节点
        if (isRed(node.left) && isRed(node.left.left)) {
           node = rotateRight(node);
        }

        // 翻转颜色
        if (isRed(node.right) && isRed(node.left)) {
            flipColors(node);
        }
        return node; //fix this return statement
    }

}