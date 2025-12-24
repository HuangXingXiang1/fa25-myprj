
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        Map<Character, Integer> dict = new TreeMap<>();
        for (int i= 97; i < 123; i++){
            dict.put((char) i, i - 96);
        }
        return dict;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
       Map<Integer, Integer> dict = new TreeMap<>();
       for (int i : nums){
           dict.put(i, i*i);
       }
        return dict;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
         Map<String, Integer> dict = new TreeMap<>();
         int count;
         for (String word : words){
             count = 0;
             if (dict.containsKey(word)){
                 continue;
             }
            for (String word_0 : words){
                if (word.equals(word_0)){
                    count++;
                }
            }
            dict.put(word, count);
         }
        return dict;
    }
}
