package main;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.TimeSeries.MAX_YEAR;
import static main.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 * ---
 * 一个用于对 Google NGrams 数据集（或其子集）执行查询的工具类。
 * NGramMap 从“词语文件”（words file）和“计数文件”（counts file）中存储相关数据。
 * 它在严格意义上并不是一个 Map，但提供了额外的功能。
 *
 * @author Josh Hug
 */
public class NGramMap {

    private final TimeSeries yearHistory;
    private final Map<Integer, Map<String, Double>> wordHistory;

    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     * 根据词语历史文件（WORDHISTORYFILENAME）和年份总计文件（YEARHISTORYFILENAME）构造一个 NGramMap。
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        yearHistory = new TimeSeries();
        wordHistory = new HashMap<>();
       try {
           // 创建缓冲区
           BufferedReader readWord = new BufferedReader(new FileReader(wordHistoryFilename));
           BufferedReader readYear = new BufferedReader(new FileReader(yearHistoryFilename));
           String nextLine;
           // 将单词数据写入WordHistory
           while ((nextLine = readWord.readLine()) != null) {
               String[] line = nextLine.split("\t");
               Map<String, Double> old = wordHistory.get(Integer.parseInt(line[1]));
               if (old != null) {
                   old.put(line[0], Double.parseDouble(line[2]));
               } else {
                   Map<String, Double> map = new HashMap<>();
                   map.put(line[0], Double.parseDouble(line[2]));
                   wordHistory.put(Integer.parseInt(line[1]), map);
               }
           }

           // 将年数据读入YearHistory
           while ((nextLine = readYear.readLine()) != null) {
               String[] line = nextLine.split(",");
               yearHistory.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
           }
       } catch (FileNotFoundException e) {
	       throw new RuntimeException(e);
       } catch (IOException e) {
	       throw new RuntimeException(e);
       }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     * 返回词语 WORD 在起始年份 STARTYEAR 到结束年份 ENDYEAR（含两端）之间的出现频次历史。
     * 返回的 TimeSeries 应为副本，而非指向本 NGramMap 内部 TimeSeries 的引用。
     * 换言之，对该方法返回对象所做的修改不应影响本 NGramMap 的内部状态。
     * 这也被称为“防御性拷贝”（defensive copy）。
     * 如果该词语不在数据文件中，则返回一个空的 TimeSeries。
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        return countHistoryHelp(word, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     * 返回词语 WORD 在整个时间范围内的出现频次历史。
     * 返回的 TimeSeries 应为副本，而非指向本 NGramMap 内部 TimeSeries 的引用。
     * 换言之，对该方法返回对象所做的修改不应影响本 NGramMap 的内部状态。
     * 这也被称为“防御性拷贝”（defensive copy）。
     * 如果该词语不在数据文件中，则返回一个空的 TimeSeries。
     */
    public TimeSeries countHistory(String word) {
        return countHistoryHelp(word, MIN_YEAR, MAX_YEAR);
    }

    private TimeSeries countHistoryHelp(String word, int startYear, int endYear) {
        TimeSeries result = new TimeSeries();
        for (int year = startYear; year <= endYear; year++) {
            Double Frequency = getFrequency(year, word);
            if (Frequency != null) {
                result.put(year, Frequency);
            }
        }
        return result;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     * 返回每年所有书籍中记录的单词总数的防御性拷贝。
     */
    public TimeSeries totalCountHistory() {
        TimeSeries result = new TimeSeries();
        List<Integer> years = yearHistory.years();
        List<Double> values = yearHistory.data();
        for (int i = 0, size = years.size(); i < size; i++) {
            result.put(years.get(i), values.get(i));
        }
        return result;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     * 返回词语 WORD 在起始年份 STARTYEAR 到结束年份 ENDYEAR（含两端）之间每年的相对频率（即该词频次除以当年总词数）。
     * 如果该词语不在数据文件中，则返回一个空的 TimeSeries。
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        return weightHistoryHelp(word, startYear, endYear);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     * 返回词语 WORD 相对于每年所有记录词语的相对频率（即该词频次除以当年总词数）的时间序列。
     * 如果该词语不在数据文件中，则返回一个空的 TimeSeries。
     */
    public TimeSeries weightHistory(String word) {
        return weightHistoryHelp(word, MIN_YEAR, MAX_YEAR);
    }

    private TimeSeries weightHistoryHelp(String word, int startYear, int endYear) {
        TimeSeries result = new TimeSeries();
        for (int year = startYear; year <= endYear; year++) {
            Double Frequency = getFrequency(year, word);
            Double total = getYearlyTotal(year);
            if (Frequency != null && total != null && total != 0) {
                result.put(year, Frequency / total);
            }
        }
        return result;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     * 返回集合 WORDS 中所有词语在起始年份 STARTYEAR 到结束年份 ENDYEAR（含两端）之间每年的相对频率之和。
     * 如果某个词语在此时间段内不存在，则忽略它，而不是抛出异常。
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        return summedWeightHistoryHelp(words, startYear, endYear);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     * 返回集合 WORDS 中所有词语在整个时间范围内每年的相对频率之和。
     * 如果某个词语不存在于数据中，则忽略它，而不是抛出异常。
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistoryHelp(words, MIN_YEAR, MAX_YEAR);
    }

    private TimeSeries summedWeightHistoryHelp(Collection<String> words,
                                               int startYear, int endYear) {
        TimeSeries result = new TimeSeries();
        // 获取给定范围的年单词总量
        // 检查是否非空，空则跳过

        // 否则遍历给定单词堆
        // 将其对应年的频率相加后÷于对应年单词重量，加入时间映射
        for (int year = startYear; year <= endYear; year++) {
            Double sums = 0.00;
            Double total = getYearlyTotal(year);
            if (total != null && total != 0) {
                for (String word : words) {
                    Double Frequency = getFrequency(year, word);
                    if (Frequency == null) Frequency = 0.00;
                    sums += Frequency;
                }
                result.put(year, sums/total);
            }
        }
        return result;
    }

   private Double getFrequency(Integer year, String word) {
	   Map<String, Double> Year = wordHistory.get(year);
	   if (Year != null) {
           return Year.get(word);
       }
	   return null;
   }

   private Double getYearlyTotal(Integer year) {
        return yearHistory.get(year);
   }
   }