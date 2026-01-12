import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// 引入普林斯顿算法库中的加权快速并查集（Union-Find）实现

public class Percolation {
    private final int virtualTop;
    private final int virtualBottom;
    boolean[][] grid;
    WeightedQuickUnionUF ufisFull;
    WeightedQuickUnionUF ufPercolates;
    int open_Number;
    int length;


    // 构造函数：创建一个 N×N 的渗流系统
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        ufisFull = new WeightedQuickUnionUF(N*N+1);
        ufPercolates = new WeightedQuickUnionUF(N*N+2);
        grid = new boolean[N][N];
        open_Number = 0;
        length = N;
        virtualTop = length * length;
        virtualBottom = length * length+1;
    }

    // 打开站点
    public void open(int row, int col) {
        // 验证参数是否合法
        validate(row, col);
        // 检查是否已经打开
        if (!grid[row][col]) {
            grid[row][col] = true;
            // 将当前的开放网格与周围开放网格进行连通
            passageway(row, col);
            // 顺势增加打开数
            open_Number++;
            // 将打开的顶部节点与虚拟节点相连
            if (row == 0) {
                ufisFull.union(position(0, col), virtualTop);
                ufPercolates.union(position(0, col), virtualTop);
            }
            if (row == length - 1) {
                ufPercolates.union(position(length-1, col), virtualBottom);
            }
        }
    }
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    }

    // 判断位于 (row, col) 的站点是否是“满的”
    // （通常定义为：该站点已打开，且与顶部连通）
    public boolean isFull(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) return false;
	    return ufisFull.connected(position(row, col), virtualTop);
    }

    // 返回当前系统中打开的站点总数
    public int numberOfOpenSites() {
        return open_Number;
    }

    // 判断系统是否发生渗流
    // （即：是否存在一条从顶部到底部的连通路径）
    public boolean percolates() {
        return ufPercolates.connected(virtualBottom, virtualTop);
    }

    // 验证参数是否合法
    private void validate(int row, int col) {
        if (row >= length || row < 0 || col >= length || col < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    // 将当前的开放网格与周围开放网格进行连通
    private void passageway(int row, int col) {
        // 声明数组涵盖上下左右的所有情况
        int[][] covert = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        // 获取目标开放方格位于一维形式的位置
        int curr = position(row, col);
        for (int i = 0; i < 4; i++) {
            int temprow = row + covert[i][0];
            int tempcol = col + covert[i][1];
            // 处理边界情况
            if (temprow < length && temprow >= 0 && tempcol < length && tempcol >= 0) {
                // 合法并且是开放节点即相连
                if (isOpen(temprow, tempcol)) {
                    // 获取目标周围开放方格位于一维形式的位置
                    int connector = position(temprow, tempcol);
                    ufisFull.union(curr, connector);
                    ufPercolates.union(curr, connector);
                }
            }
        }

    }
    private int position(int row, int col) {
        return row * length + col;
    }
}
