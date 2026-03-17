package main;

import java.util.*;

public class DirectedGraph {

	// 邻接表结构
	private final HashMap<Integer, Set<Integer>> adjacency;

	public DirectedGraph() {
		this.adjacency = new HashMap<>();
	}

	public void addVertex(int vertex) {
		adjacency.put(vertex, new HashSet<>());
	}

	public void addEdge(int vertex1, int vertex2) {
		if (!adjacency.containsKey(vertex1)) {
			addVertex(vertex1);
		}
		if (!adjacency.containsKey(vertex2)) {
			addVertex(vertex2);
		}
		adjacency.get(vertex1).add(vertex2);
	}

	public Iterable<Integer> getAdj(int vertex) {
		return adjacency.get(vertex);
	}

	public Set<Integer> searchDFS(int vertex) {
		verification(vertex);
		Set<Integer> marked = new HashSet<>();
		Set<Integer> set = new HashSet<>();
		return dfs(vertex, marked, set);
	}

	public Set<Integer> dfs(int vertex,Set<Integer> marked, Set<Integer> set) {
		marked.add(vertex);
		set.add(vertex);
		for (int otherVertex : getAdj(vertex)) {
			if (!marked.contains(otherVertex)) {
				// 将可达顶点添加入集合当中
				dfs(otherVertex, marked, set);
			}
		}
		return set;
	}

	private void verification(int vertex) {
		if (!adjacency.containsKey(vertex)) {
			throw new IllegalArgumentException("图中不存在该顶点:"+vertex);
		}
	}
}
