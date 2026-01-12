import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * 渗透问题测试类 (Percolation Test Class)
 * 用于验证Percolation类的功能实现是否符合预期
 */
public class PercolationTest {

    /**
     * 单元格状态枚举 (Cell State Enumeration)
     * 用于表示网格中单元格的四种状态组合：
     * (0) CLOSED: isOpen()返回false, isFull()返回false（未打开）
     * (1) OPEN:   isOpen()返回true,  isFull()返回false（已打开）
     * (2) INVALID: isOpen()返回false, isFull()返回true（无效状态）
     *              （这不应该发生！只有打开的单元格才可能满）
     * (3) FULL:   isOpen()返回true,  isFull()返回true（已充满）
     */
    private enum Cell {
        CLOSED, OPEN, INVALID, FULL
    }

    /**
     * 从Percolation实例生成单元格状态矩阵 (Generate Cell State Matrix)
     * 通过遍历网格检查isOpen和isFull返回值组合，转换为对应的Cell枚举值
     *
     * @param N 网格边长 (Grid size)
     * @param p Percolation实例 (Percolation instance)
     * @return Cell状态矩阵 (Cell state matrix)
     */
    private static Cell[][] getState(int N, Percolation p) {
        Cell[][] state = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                // 通过位掩码组合生成Cell索引
                // 00: CLOSED (0)
                // 01: OPEN   (1)
                // 10: INVALID(2)
                // 11: FULL   (3)
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = Cell.values()[open + full];
            }
        }
        return state;
    }

    /**
     * 基础功能测试 (Basic Functionality Test)
     * 测试5x5网格的基本操作，验证单元格状态和渗透判断
     */
    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(N);

        // 定义要打开的单元格坐标 (Coordinates to open)
        int[][] openSites = {
                {0, 1}, // (行, 列) = (0, 1)
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };

        // 预期的单元格状态矩阵 (Expected cell states)
        Cell[][] expectedState = {
                {Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };

        // 执行打开操作 (Open specified sites)
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }

        // 验证状态矩阵和渗透结果 (Verify states and percolation result)
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    /**
     * 1x1网格测试 (1x1 Grid Test)
     * 验证最小规模网格的渗透情况
     */
    @Test
    public void oneByOneTest() {
        int N = 1;
        Percolation p = new Percolation(N);

        // 打开唯一单元格 (Open the only cell)
        p.open(0, 0);

        // 预期状态 (Expected state)
        Cell[][] expectedState = {
                {Cell.FULL}
        };

        // 验证状态和渗透结果 (Verify states and percolation result)
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    /**
     * 自定义测试模板 (Custom Test Template)
     * 用户应在此添加自己的测试用例
     */
    @Test
    public void yourFirstTestHere() {
        int N = 5;
        Percolation p = new Percolation(N);
        int[][] OpenSites = {
                {4, 0},
                {3, 0},
                {2, 0},
                {1, 0},
                {0, 0},
                {4, 2},
                {3, 2},
                {2, 2},
                {4, 3},
                {4, 4},
                {3, 4},
                {2, 4},
                {3, 3},
                {2, 3}
        };
        Cell[][] expectedState = {
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.OPEN, Cell.OPEN, Cell.OPEN},
                {Cell.FULL, Cell.CLOSED, Cell.OPEN, Cell.OPEN, Cell.OPEN},
                {Cell.FULL, Cell.CLOSED, Cell.OPEN, Cell.OPEN, Cell.OPEN}
        };
        for (int[] site : OpenSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }
}
