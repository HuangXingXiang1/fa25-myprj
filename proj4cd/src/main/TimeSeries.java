package main;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data.
 * Provides utility methods useful for data analysis.
 * 一个将“年份（例如 1996）”映射到数值数据的对象。
 * 提供了一些对数据分析有用的工具方法。
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

	/**
	 * If it helps speed up your code, you can assume year arguments to your
	 * NGramMap are between 1400 and 2100.
	 * We've stored these values as the constants MIN_YEAR and MAX_YEAR here.
	 * 如果有助于提升代码效率，你可以假设传入 NGramMap 的年份参数
	 * 一定位于 1400 到 2100 之间。
	 * 我们在这里将这两个边界值存储为常量 MIN_YEAR 和 MAX_YEAR。
	 */
	public static final int MIN_YEAR = 1400;

	/**
	 * The maximum allowed year (inclusive).
	 * 允许的最大年份（包含该年份）。
	 */
	public static final int MAX_YEAR = 2100;

	/**
	 * Constructs a new empty TimeSeries.
	 * 构造一个新的、空的 TimeSeries 对象。
	 */
	public TimeSeries() {
		super();
	}

	/**
	 * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
	 * inclusive of both end points.
	 * 创建 TS 的一个副本，但只包含从 startYear 到 endYear 的数据，
	 * 且包含起始和结束年份本身。
	 */
	public TimeSeries(TimeSeries ts, int startYear, int endYear) {
		super();
		// 遍历原始时间序列的所有键值对
		for (var entry : ts.entrySet()) {
			int year = entry.getKey();
			// 检查年份是否在目标范围内
			if (year >= startYear && year <= endYear) {
				// 将符合条件的键值对加入映射
				put(year, entry.getValue());
			}
		}
	}

	/**
	 * Returns all years for this time series in ascending order.
	 * 以升序（从小到大）的顺序返回该时间序列中的所有年份。
	 */
	public List<Integer> years() {
		List<Integer> years = new ArrayList<>();
		for (int year : keySet()) {
			years.addLast(year);
		}
		return years;
	}

	/**
	 * Returns all data for this time series.
	 * Must correspond to the order of years().
	 * 返回该时间序列中的所有数值数据。
	 * 返回的数据顺序必须与 years() 返回的年份顺序一一对应。
	 */
	public List<Double> data() {
		List<Double> values = new ArrayList<>();
		for (Double year : values()) {
			values.addLast(year);
		}
		return values;
	}

	/**
	 * Returns the year-wise sum of this TimeSeries with the given TS.
	 * In other words, for each year, sum the data from this TimeSeries
	 * with the data from TS.
	 * Should return a new TimeSeries (does not modify this TimeSeries).
	 * 返回当前 TimeSeries 与给定 TS 按“年份逐年相加”后的结果。
	 * 换言之，对每一个年份，将两个 TimeSeries 中该年份对应的值相加。
	 * 应当返回一个新的 TimeSeries（不修改原有对象）。
	 * If both TimeSeries don't contain any years, return an empty TimeSeries.
	 * 如果两个 TimeSeries 都不包含任何年份，返回一个空的 TimeSeries。
	 * If one TimeSeries contains a year that the other one doesn't,
	 * the returned TimeSeries should store the value from the
	 * TimeSeries that contains that year.
	 * 如果某一年只存在于其中一个 TimeSeries 中，
	 * 则结果中该年份的值应直接取自包含该年份的那个 TimeSeries。
	 */
	public TimeSeries plus(TimeSeries ts) {
		TimeSeries plus = new TimeSeries();
		plus.putAll(this);
		for (var entry : ts.entrySet()) {
			int year = entry.getKey();
			Double value = entry.getValue();

			plus.merge(year, value, Double::sum);
		}
		return plus;
	}

	/**
	 * Returns the quotient of the value for each year in this TimeSeries
	 * divided by the value for the same year in TS.
	 * Should return a new TimeSeries (does not modify this TimeSeries).
	 * 返回一个新的 TimeSeries，其中每个年份的值
	 * 等于当前 TimeSeries 中该年的值除以 TS 中同一年份的值。
	 * 不应修改原有 TimeSeries。
	 * If TS is missing a year that exists in this TimeSeries,
	 * throw an IllegalArgumentException.
	 * 如果 TS 中缺少某个在当前 TimeSeries 中存在的年份，
	 * 则应抛出 IllegalArgumentException。
	 * If TS has a year that is not in this TimeSeries, ignore it.
	 * 如果 TS 中包含某些当前 TimeSeries 不包含的年份，则忽略这些年份。
	 */
	public TimeSeries dividedBy(TimeSeries ts) {
		TimeSeries result = new TimeSeries();
		for (var entry : this.entrySet()) {
			int year = entry.getKey();
			Double value = entry.getValue();
			Double tsValue = ts.get(year);
			if (tsValue == null) {
				throw new IllegalArgumentException();
			}
			result.put(year, value / tsValue);
		}
		return result;
	}
}
