import edu.princeton.cs.algs4.Stack;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
	// 树节点类
	private class BSTNode {

		public K key;
		public V value;
		public BSTNode left;
		public BSTNode right;

		public BSTNode(K k, V v) {
			key = k;
			value = v;
			left = null;
			right = null;
		}
	}

	private BSTNode root;
	private int size;

	public BSTMap() {
		root = new BSTNode(null, null);
		size = 0;
	}

	/**
	 * 将指定的值与指定的键关联到该映射中。
	 * Associates the specified value with the specified key in this map.
	 * <p>
	 * 如果映射中已经包含该键，则用指定的值替换原有的键值映射。
	 * If the map already contains the specified key, replaces the key's mapping
	 * with the value specified.
	 *
	 * @param key   键 / the key
	 * @param value 值 / the value
	 */
	@Override
	public void put(K key, V value) {
		BSTNode find = find(root, key);
		// 寻找是否存在键重复
		if (size == 0) {
			root.key = key;
			root.value = value;
			size++;
		}
		// 如果重复替换该节点值为指定值
		else if (find != null) {
			find.value = value;
		} else {
			putHelp(root, key, value);
			size++;
		}
	}

	private BSTNode putHelp(BSTNode root, K key, V value) {
		if (root == null) {
			return new BSTNode(key, value);
		} else if (root.key.compareTo(key) > 0) {
			root.left = putHelp(root.left, key, value);
		} else {
			root.right = putHelp(root.right, key, value);
		}
		return root;
	}

	private BSTNode find(BSTNode root, K key) {
		if (size == 0) {
			return null;
		}
		if (root == null) {
			return null;
		}
		if (root.key.equals(key)) {
			return root;
		} else if (root.key.compareTo(key) > 0) {
			return find(root.left, key);
		} else return find(root.right, key);
	}

	/**
	 * 返回指定键所映射的值；如果该映射中不包含该键，则返回 null。
	 * Returns the value to which the specified key is mapped, or null if this
	 * map contains no mapping for the key.
	 *
	 * @param key 键 / the key
	 */
	@Override
	public V get(K key) {
		BSTNode find = find(root, key);
		if (find != null) {
			return find.value;
		}
		return null;
	}

	/**
	 * 返回该映射中是否包含指定键的映射关系。
	 * Returns whether this map contains a mapping for the specified key.
	 *
	 * @param key 键 / the key
	 */
	@Override
	public boolean containsKey(K key) {
		if (find(root, key) != null) {
			return true;
		}
		return false;
	}

	/**
	 * 返回该映射中键值对的数量。
	 * Returns the number of key-value mappings in this map.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * 移除该映射中的所有键值映射。
	 * Removes every mapping from this map.
	 */
	@Override
	public void clear() {
		root.right = null;
		root.left = null;
		size = 0;
	}

	/**
	 * 返回该映射中所包含键的 Set 视图。
	 * Returns a Set view of the keys contained in this map.
	 * <p>
	 * 实验 7（Lab 7）不要求实现该方法。
	 * Not required for Lab 7.
	 * <p>
	 * 如果你不实现该方法，应抛出 UnsupportedOperationException。
	 * If you don't implement this, throw an UnsupportedOperationException.
	 */
	@Override
	public Set<K> keySet() {
		Set<K> set = new TreeSet<>();
		keySet(root, set);
		return set;
	}

	private void keySet(BSTNode root, Set<K> set) {
		if (root == null) {
			return;
		}
		keySet(root.left, set);
		set.add(root.key);
		keySet(root.right, set);
	}

	/**
	 * 如果该映射中存在指定键，则移除对应的映射并返回其值；
	 * 如果不存在该键，则返回 null。
	 * Removes the mapping for the specified key from this map if present,
	 * or null if there is no such mapping.
	 * <p>
	 * 实验 7（Lab 7）不要求实现该方法。
	 * Not required for Lab 7.
	 * <p>
	 * 如果你不实现该方法，应抛出 UnsupportedOperationException。
	 * If you don't implement this, throw an UnsupportedOperationException.
	 *
	 * @param key 键 / the key
	 */
	@Override
	public V remove(K key) {
		V value = get(key);
		root =  remove(root, key);
		return value;
	}

	private BSTNode remove(BSTNode root, K key) {
		if (root == null) {
			return null;
		} else if (root.key.compareTo(key) == 0) {
			size--;
			if (root.left == null && root.right == null) {
				return null;
			} else if (root.left == null) {
				return root.right;
			} else if (root.right == null) {
				return root.left;
			} else {
				BSTNode replace = root.right;
				while (replace.left != null) {
					replace = replace.left;
				}
				replace.left = root.left;
				return root.right;
			}
		} else if (root.key.compareTo(key) > 0) {
			root.left = remove(root.left, key);
		} else {
			root.right = remove(root.right, key);
		}
		return root;
	}

	// 迭代器类
	private class PackIteraor implements Iterator<K>{
		private BSTNode curr = root;
		private Stack<BSTNode> stack = new Stack<>();

		private void isitial() {
			while (curr != null) {
				stack.push(curr);
				curr = curr.left;
			}
		}

		/**
		 * Returns {@code true} if the iteration has more elements.
		 * (In other words, returns {@code true} if {@link #next} would
		 * return an element rather than throwing an exception.)
		 *
		 * @return {@code true} if the iteration has more elements
		 */
		@Override
		public boolean hasNext() {
			isitial();
			if (!stack.isEmpty()) {
				return true;
			}
			return false;
		}

		/**
		 * Returns the next element in the iteration.
		 *
		 * @return the next element in the iteration
		 * @throws NoSuchElementException if the iteration has no more elements
		 */
		@Override
		public K next() {
			curr = stack.pop();
			K result = curr.key;
			curr = curr.right;
			return result;
		}
	}
	/**
	 * 返回一个用于遍历该映射中键的迭代器。
	 * Returns an iterator over elements of type {@code K}.
	 *
	 * @return 一个迭代器 / an Iterator
	 */
	@Override
	public Iterator<K> iterator() {
		return new PackIteraor();
	}
	private void em(BSTNode root, List<K> keyList) {
		if (root == null) {
			return;
		}
		em(root.left, keyList);
		keyList.addLast(root.key);
		em(root.right, keyList);
	}
	public List<K> Non_recursive() {
		return Non_recursive(root);
	}
	private List<K> Non_recursive(BSTNode root) {
		 List<K> result = new ArrayList<>();
		Stack<BSTNode> stack = new Stack<>();
		BSTNode curr = root;
		while (curr != null || !stack.isEmpty()) {
			if (curr != null) {
				stack.push(curr);
				curr = curr.left;
			}else {
				curr = stack.pop();
				result.addLast(curr.key);
				curr = curr.right;
				}
			}
		return result;
	}
}
