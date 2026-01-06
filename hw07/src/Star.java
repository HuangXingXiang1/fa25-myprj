import edu.princeton.cs.algs4.In;

import java.util.Collections;
import java.util.List;

/**
 * 表示恒星的类 (Class representing a star)
 */
public class Star implements Comparable<Star> {
    private final String name;       // 恒星名称 (Star name)
    private final double distanceLy; // 距离（光年）(Distance in light years)
    private final double massMsun;   // 质量（太阳质量）(Mass in solar masses)
    private final double mV;         // 视星等 (Apparent visual magnitude)

    /**
     * 构造函数 (Constructor)
     * @param name 恒星名称 (Star name)
     * @param distanceLy 距离（光年）(Distance in light years)
     * @param massMsun 质量（太阳质量）(Mass in solar masses)
     * @param mV 视星等 (Apparent visual magnitude)
     */
    public Star(String name, double distanceLy, double massMsun, double mV) {
        this.name = name;
        this.distanceLy = distanceLy;
        this.massMsun = massMsun;
        this.mV = mV;
    }

    /**
     * 返回格式化字符串表示 (Returns formatted string representation)
     * @return 恒星信息字符串 (String representation of star data)
     */
    @Override
    public String toString() {
        return String.format("Star{name='%s', distanceLy=%.6f, massMsun=%s, mV=%.2f}",
                name, distanceLy, String.format("%.3f", massMsun), mV);
    }

    // 无需修改此行以上代码
    // (You do not need to modify anything above this line)
    // 建议：实现Comparable<Star>接口并重写compareTo方法
    // Suggestion: Implement Comparable<Star> interface and override compareTo method
    @Override
    public int compareTo(Star x) {
        if (massMsun > x.massMsun) {
            return 1;
        } else if (massMsun < x.massMsun) {
            return -1;
        }
        return 0;
    }


    public static void main(String[] args) {
        // 初始化输入流 (Initialize input stream)
        In in = new In("data/stars20.txt");
        // 读取CSV文件中的恒星数据 (Read star data from CSV file)
        List<Star> stars = ParseUtils.readCsv(in);

        // 示例逻辑：使用Collections.max()并提供自定义比较器
        // Example logic: Use Collections.max() with custom comparator
        System.out.println("已加载 " + stars.size() + " 颗恒星。");
        int show = 5;
        for (int i = 0; i < show; i += 1) {
            System.out.println(stars.get(i));
        }
        System.out.println(Collections.max(stars));
    }
}