package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;


public class HyponymsHandler extends NgordnetQueryHandler {
	private final WordNet wordNet;
	private NGramMap nGramMap;


	public HyponymsHandler(WordNet wordNet, NGramMap nGramMap) {
		this.wordNet = wordNet;
		this.nGramMap = nGramMap;
	}

	public HyponymsHandler(WordNet wordNet) {
		this.wordNet = wordNet;
	}

	@Override
	public String handle(NgordnetQuery q) {
		if (q.k() == 0) {
			return wordNet.getHyponymsByWords(q.words()).toString();
		} else {
			System.out.println(q.k());
			return nGramMap.topKWords(wordNet.getHyponymsByWords(q.words()),
					q.startYear(), q.endYear(), q.k()
			).toString();
		}
	}
}
