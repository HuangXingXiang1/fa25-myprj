import edu.princeton.cs.algs4.In;

import java.util.Comparator;
import java.util.List;

public class WordFinder {
    /**
     *  Returns the maximum string according to the provider comparator.
     *  If multiple strings are considered equal by c, return the first in
     *  the array.
     *  Use loops. Don't use Collections.max or similar.
     */
    public static String findMax(String[] strings, Comparator<String> c) {
        if (strings.length <= 0) {
            System.out.println("字符数组不能为空");
            return null;
        }
        int max = 0;
        for (int i = 0; i < strings.length; i++) {
            if (c.compare(strings[i], strings[max]) > 0) {
                max = i;
            }
        }
        return strings[max];
    }

    public static void main(String[] args) {
        In in = new In("data/mobydick.txt");
        String[] words = in.readAllStrings();

        Comparator<String> vowelComparator = WordComparators.getCharListComparator(List.of('a', 'e', 'i', 'o', 'u'));
        String max = findMax(words, vowelComparator);
        System.out.println(max);
//         Optional task: Play around with lists of words from Wikipedia articles.
         String[] zebraWords = ParseUtils.fetchWords("https://en.wikipedia.org/wiki/zebra");
         System.out.println(findMax(zebraWords, vowelComparator));
    }
}
