import main.NGramMap;
import main.WordNet;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * 测试-给定时间区间最受欢迎的K个word
 * 覆盖:
 * 1.K为负数
 * 2.传入不存在单词
 * 3.在列表但时间序列不存在
 * 4.功能
 */
public class TestTopKWords {
	public static final String PREFIX = "./data/";
	/** NGrams Files */
	public static final String WORD_HISTORY_EECS_FILE = PREFIX + "word_history_eecs.csv";
	public static final String WORD_HISTORY_SIZE3_FILE = PREFIX + "word_history_size3.csv";
	public static final String WORD_HISTORY_SIZE4_FILE = PREFIX + "word_history_size4.csv";
	public static final String WORD_HISTORY_SIZE1291_FILE = PREFIX + "word_history_size1291.csv";
	public static final String WORD_HISTORY_SIZE14377_FILE = PREFIX + "word_history_size14377.csv";
	public static final String YEAR_HISTORY_FILE = PREFIX + "year_history.csv";

	/** Wordnet Files */
	public static final String SYNSETS_EECS_FILE = PREFIX + "synsets_eecs.txt";
	public static final String HYPONYMS_EECS_FILE = PREFIX + "hyponyms_eecs.txt";
	public static final String SYNSET_SIZE16_FILE = PREFIX + "synsets16.txt";
	public static final String HYPONYM_SIZE16_FILE = PREFIX + "hyponyms16.txt";
	public static final String SYNSET_SIZE1000_FILE = PREFIX + "synsets1000-subgraph.txt";
	public static final String HYPONYM_SIZE1000_FILE = PREFIX +  "hyponyms1000-subgraph.txt";

	@Test
	public void testWhenKisNegative() {
		NGramMap nGramMap = new NGramMap(WORD_HISTORY_SIZE4_FILE, YEAR_HISTORY_FILE);
		WordNet wordNet = new WordNet(SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE);
		List<String> beach = wordNet.getHyponymsByWords(Collections.singletonList("beach"));
		assertThat(nGramMap.topKWords(beach, 2000, 2020, -1)).isEmpty();
	}

	@Test
	public void testInvalidWord() {
		NGramMap nGramMap = new NGramMap(WORD_HISTORY_SIZE4_FILE, YEAR_HISTORY_FILE);
		WordNet wordNet = new WordNet(SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE);
		List<String> beach = wordNet.getHyponymsByWords(Collections.singletonList("bagayala"));
		assertThat(nGramMap.topKWords(beach, 2000, 2020, 15)).isEmpty();
	}

	@Test
	public void testHasXNotY() {
		NGramMap nGramMap = new NGramMap(WORD_HISTORY_SIZE4_FILE, YEAR_HISTORY_FILE);
		WordNet wordNet = new WordNet(SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE);
		List<String> beach = wordNet.getHyponymsByWords(Collections.singletonList("variation"));
		assertThat(nGramMap.topKWords(beach, 2000, 2020, 15)).isEmpty();
	}

	@Test
	public void testFunctional() {
		NGramMap nGramMap = new NGramMap(WORD_HISTORY_SIZE4_FILE, YEAR_HISTORY_FILE);
		WordNet wordNet = new WordNet(SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE);
		List<String> beach = wordNet.getHyponymsByWords(Collections.singletonList("beach"));
		assertThat(nGramMap.topKWords(beach, 2000, 2020, 2)).isEqualTo(List.of("beach" ,"flashback"));
		assertThat(nGramMap.topKWords(beach, 2000, 2020, 100)).isEqualTo(List.of("beach", "change", "flashback")); // 根据数据
		}

	@Test
	public void testKisZero() {
		NGramMap nGramMap = new NGramMap(WORD_HISTORY_SIZE4_FILE, YEAR_HISTORY_FILE);
		WordNet wordNet = new WordNet(SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE);
		List<String> beach = wordNet.getHyponymsByWords(Collections.singletonList("beach"));
		assertThat(nGramMap.topKWords(beach, 2000, 2020, 0)).isEmpty();
	}
}
