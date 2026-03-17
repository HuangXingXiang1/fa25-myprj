import main.WordNet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * WordNet 测试类
 * 测试范围：
 * 1. 文件解析正确性
 * 2. getHyponymsByWords 查询功能
 * 3. 边界情况（不存在单词）
 */
public class TestWordNet {
	private static final String PREFIX = "./data/";

	/** WordNet 文件 */
	public static final String SYNSET16_FILE = PREFIX + "synsets16.txt";
	public static final String HYPONYM16_FILE = PREFIX + "hyponyms16.txt";

	/**
	 * 测试 WordNet 文件解析是否正确
	 * 场景：
	 * - 读取 synsets 和 hyponyms 文件
	 * - 查询单词 "change" 的 hyponyms
	 */
	@Test
	public void testFileParsing() {
		WordNet wn = new WordNet(SYNSET16_FILE, HYPONYM16_FILE);
		List<String> expected = List.of(
				"alteration", "change", "demotion", "increase",
				"jump", "leap", "modification", "saltation",
				"transition", "variation"
		);
		assertThat(wn.getHyponymsByWords(List.of("change"))).isEqualTo(expected);
	}

	/**
	 * 测试 getHyponymsByWords 查询多个单词
	 * 场景：
	 * - 查询 ["happening", "transition"] 的 hyponyms
	 * - 验证返回集合是否符合预期
	 */
	@Test
	public void testGetHyponymsByWordsMultiple() {
		WordNet wn = new WordNet(SYNSET16_FILE, HYPONYM16_FILE);
		List<String> except = new ArrayList<>();
		except.add("jump");
		except.add("leap");
		except.add("saltation");
		except.add("transition");
		assertThat(wn.getHyponymsByWords(List.of("happening", "transition"))).isEqualTo(except);
	}

	/**
	 * 测试查询不存在的单词时的行为
	 * 场景：
	 * - 查询 ["nonexistent"] 的 hyponyms
	 * - 期望返回空集合
	 */
	@Test
	public void testQueryNonexistentWord() {
		WordNet wn = new WordNet(SYNSET16_FILE, HYPONYM16_FILE);
		assertThat(wn.getHyponymsByWords(List.of("nonexistent"))).isEmpty();
	}

	/**
	 * 测试传入空列表时的行为
	 * 场景：
	 * - getHyponymsByWords([])
	 * - 期望返回空集合
	 */
	@Test
	public void testQueryEmptyList() {
		WordNet wn = new WordNet(SYNSET16_FILE, HYPONYM16_FILE);
		assertThat(wn.getHyponymsByWords(new ArrayList<>())).isEmpty();
	}

	/**
	 * 测试构造函数传入不存在的文件路径时
	 * 期望抛出异常（如 IllegalArgumentException 或 RuntimeException）
	 */
	@Test
	public void testConstructorWithInvalidFiles() {
		assertThrows(RuntimeException.class, () -> {
			new WordNet("invalid_synsets.txt", "invalid_hyponyms.txt");
		});
	}
}