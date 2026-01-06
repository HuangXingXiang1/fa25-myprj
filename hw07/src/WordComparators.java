import java.util.Comparator;
import java.util.List;

public class WordComparators {

    /** Returns a comparator that orders strings by the number of lowercase 'x' characters (ascending). */
    public static Comparator<String> getXComparator() {
        return getcharHelper(List.of('x'));
    }

    /** Returns a comparator that orders strings by the count of the given character (ascending). */
    public static Comparator<String> getCharComparator(char c) {
        return getcharHelper(List.of(c));
    }

    /** Returns a comparator that orders strings by the total count of the given characters (ascending). */
    public static Comparator<String> getCharListComparator(List<Character> chars) {
        return getcharHelper(chars);
    }

    private static Comparator<String> getcharHelper(List<Character> chars) {
       return (s1, s2) -> {
            int count_s1 = count(s1, chars);
            int count_s2 = count(s2, chars);

            return count_s1 - count_s2;
        };
    }

    public static int count(String s, List<Character> chars) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            for (char c : chars) {
                if (s.charAt(i) == c) {
                    count++;
                }
            }
        }
        return count;
    }
}
