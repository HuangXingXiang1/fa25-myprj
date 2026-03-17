package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
	private final NGramMap proxy;
	public HistoryHandler(NGramMap map) {
		proxy = map;
	}

	@Override
	public String handle(NgordnetQuery q) {
		List<String> words = q.words();
		int startYear = q.startYear();
		int endYear = q.endYear();
		List<TimeSeries> wordWeights = new ArrayList<>();
		List<String> wordNamme = new ArrayList<>();
		for (String word : words) {
			TimeSeries temp = proxy.weightHistory(word, startYear, endYear);
			if (temp.isEmpty()) continue;
			wordWeights.add(temp);
			wordNamme.add(word);
		}
		// 转成图表对象
		XYChart chart = Plotter.generateTimeSeriesChart(wordNamme, wordWeights);
		// 将图标对象编码为base-64后返回
		return Plotter.encodeChartAsString(chart);
	}
}
