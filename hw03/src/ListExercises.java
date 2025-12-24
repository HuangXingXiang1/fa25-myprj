import java.util.ArrayList;
import java.util.List;

public class ListExercises {
    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        if (L.isEmpty()){
            return 0;
        }
        int summ = 0;
        for (int i : L){
            summ += i;
        }
        return summ;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> after = new ArrayList<>();

        for (int i : L){
            if (i % 2 == 0){
                after.add(i);
            }
        }
        return after;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> after = new ArrayList<>();
        for (int i : L1){
            for (int j : L2){
                if (i == j){
                    after.add(i);
                }
            }
        }
        return after;
    }

    public static int countOccurrencesOfWord(List<String> words, String w) {
        int count = 0;
        for (String word : words){
            if (word.equals(w)){
                count++;
            }
        }
        return count;
    }

    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int count = 0;
        for (String word : words){
            for (char letter : word.toCharArray()){
                if (letter == c){
                    count++;
                }
            }
        }
        return count;
    }
}
