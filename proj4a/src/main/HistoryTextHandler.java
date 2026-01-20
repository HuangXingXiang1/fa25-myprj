package main;// This should say "package main" in your own HistoryTextHandler.java,
// since your file will be in the "main" package.

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
	private NGramMap gramMap;
	public HistoryTextHandler(NGramMap map) {
		gramMap = map;
	}

	@Override
	public String handle(NgordnetQuery q) {
		List<String> words = q.words();
		int startYear = q.startYear();
		int endYear = q.endYear();
		StringBuilder intimation = new StringBuilder();
		for (String word : words) {
			TimeSeries wordData = gramMap.weightHistory(word, startYear, endYear);
			if (wordData.isEmpty()) continue;
			intimation.append(word).append(':').append(wordData.toString()).append('\n');
		}

		return intimation.toString();
	}
}
