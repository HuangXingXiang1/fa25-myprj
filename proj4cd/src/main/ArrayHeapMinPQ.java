package main;

import java.util.ArrayList;

public class ArrayHeapMinPQ<T extends Comparable<T>> {

	// 根节点
	private static final int ROOTINDEX = 1;
	// 堆的底层存储结构（完全二叉树的数组表示）
	private final ArrayList<T> arrayList;
	// 大小
	private int size;

	public ArrayHeapMinPQ() {
		this.arrayList = new ArrayList<>();
		this.size = 0;
		arrayList.add(null);
	}

	/**
	 * Adds the item to the priority queue.
	 * 向优先队列中添加一个元素。
	 *
	 * @param x
	 */
	public void add(T x) {
		arrayList.add(x);
		size++;
		swim(size);
	}

	/**
	 * Returns the smallest item in the priority queue.
	 * 返回优先队列中最小的元素（但不删除）。
	 */
	public T getSmallest() {
		if (size == 0) {
			return null;
		}
		return arrayList.get(ROOTINDEX);
	}

	/**
	 * Removes the smallest item from the priority queue.
	 * 从优先队列中删除并返回最小的元素。
	 */
	public T removeSmallest() {
		if (size == 0) {
			return null;
		}
		T smallest = arrayList.get(ROOTINDEX);
		arrayList.set(ROOTINDEX, arrayList.get(size));
		arrayList.remove(size);
		size--;
		if (size >= 2) {
			sink(ROOTINDEX);
		}
		return smallest;
	}

	/**
	 * Returns the size of the priority queue.
	 * 返回优先队列中的元素数量。
	 * 会比实际存储元素要小一，刚好对应数组最后一个元素的索引
	 */
	public int size() {
		return size;
	}

	// 上浮
	private void swim(int current) {
		int parent = parent(current);
		if (current == 1) return;
		T parent_value = arrayList.get(parent);
		T current_value = arrayList.get(current);
		if (current_value.compareTo(parent_value) >= 0 ) {
			return;
		}
		exchange(current, parent);
		swim(parent);
	}
	// 下移
	private void sink(int current) {
		int leftChild = leftChild(current);
		int rightChild = rightChild(current);
		int smallest = leftChild;
		if (leftChild > size) {
			return;
		}
		T currentValue = arrayList.get(current);
		T leftChildValue = arrayList.get(leftChild);

		if (rightChild <= size) {

			T rightChildValue = arrayList.get(rightChild);
			if (leftChildValue.compareTo(rightChildValue) > 0) {
				smallest = rightChild;
			}
			if (currentValue.compareTo(arrayList.get(smallest)) <= 0) {
				return;
			}
		}
		else {
			if (currentValue.compareTo(arrayList.get(smallest)) <= 0) {
				return;
			}
		}
		exchange(current, smallest);
		sink(smallest);
	}

	// 寻找左子孩
	private int leftChild(int k) {
		return  k*2;
	}

	// 寻找右子孩
	private int rightChild(int k) {
		return  k*2+1;
	}

	// 寻父
	private int parent(int k) {
		return  k/2;
	}

	// 交换
	private void exchange(int a, int b) {
		T temp = arrayList.get(a);
		arrayList.set(a, arrayList.get(b));
		arrayList.set(b, temp);
	}
}
