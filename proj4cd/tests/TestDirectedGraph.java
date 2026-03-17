import main.DirectedGraph;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * DirectedGraph DFS 测试
 *
 * 覆盖场景：
 * 1. 单节点 DFS
 * 2. addEdge 自动创建顶点
 * 3. 多层 DFS
 * 4. 空图抛异常
 * 5. 不存在顶点抛异常
 * 6. 循环/环图 DFS 正确性
 */
public class TestDirectedGraph {

	/**
	 * 测试单节点 DFS 是否正确
	 * 场景：图只有一个顶点，DFS 应返回自身
	 */
	@Test
	public void testSingleVertexDFS() {
		DirectedGraph graph = new DirectedGraph();
		graph.addVertex(11);
		assertThat(graph.searchDFS(11)).isEqualTo(Set.of(11));
	}

	/**
	 * 测试 addEdge 是否能自动创建不存在的顶点
	 * 场景：添加边 11 -> 12 时，顶点 12 不存在，DFS 应包含两者
	 */
	@Test
	public void testAddEdgeCreatesVertex() {
		DirectedGraph graph = new DirectedGraph();
		graph.addVertex(11);
		graph.addEdge(11, 12);
		assertThat(graph.searchDFS(11)).isEqualTo(Set.of(11, 12));
	}

	/**
	 * 测试多层 DFS 遍历
	 * 场景：图有多个层级和分支，DFS 应返回所有可达节点
	 */
	@Test
	public void testMultiLevelDFS() {
		DirectedGraph graph = new DirectedGraph();
		graph.addEdge(12, 13);
		graph.addEdge(12, 14);
		graph.addEdge(12, 15);
		graph.addEdge(12, 161);
		graph.addEdge(12, 187);
		graph.addEdge(12, 154);
		graph.addEdge(187, 199);
		graph.addEdge(187, 44);

		assertThat(graph.searchDFS(12))
				.isEqualTo(Set.of(12, 13, 14, 15, 161, 187, 199, 44, 154));
	}

	/**
	 * 测试空图 DFS 是否抛出异常
	 * 场景：图为空，任何 DFS 调用都应该抛出 IllegalArgumentException
	 */
	@Test
	public void testSearchDFSEmptyGraphThrows() {
		DirectedGraph graph = new DirectedGraph();
		assertThrows(IllegalArgumentException.class, () -> {
			graph.searchDFS(5);
		});
	}

	/**
	 * 测试传入不存在顶点 DFS 是否抛出异常
	 * 场景：顶点 2 不存在，DFS 应抛异常
	 */
	@Test
	public void testSearchDFSNonExistentVertexThrows() {
		DirectedGraph graph = new DirectedGraph();
		graph.addVertex(1);
		assertThrows(IllegalArgumentException.class, () -> {
			graph.searchDFS(2);
		});
	}

	/**
	 * 测试循环/环图 DFS 是否正确
	 * 场景：图存在环（1->2->3->1），DFS 应正常返回所有可达节点，不无限递归
	 */
	@Test
	public void testDFSWithCycle() {
		DirectedGraph graph = new DirectedGraph();
		graph.addEdge(1, 2);
		graph.addEdge(2, 3);
		graph.addEdge(3, 1); // 形成环

		// DFS 从 1 出发，应返回 1,2,3，不重复，不无限递归
		assertThat(graph.searchDFS(1)).isEqualTo(Set.of(1, 2, 3));
	}
}