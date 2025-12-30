import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

        /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
           to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
           but not ["front", "middle", "back"].
         */
    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        /* I've decided to add in comments the state after each call for the convenience of the
           person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    //Below, you'll write your own tests for LinkedListDeque61B.

    @Test
    public void iSEmptyTestBasic() {
        LinkedListDeque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        lld1.addLast("你好");
        lld1.addFirst("我好");
        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    public void sizeTextBasic() {
        LinkedListDeque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.size()).isEqualTo(0);
        lld1.addFirst(10);
        assertThat(lld1.size()).isEqualTo(1);
        lld1.addFirst(11);
        lld1.addFirst(132);
        lld1.addLast(29);
        assertThat(lld1.size()).isEqualTo(4);
    }

    @Test
    public void getTextBasic() {
        LinkedListDeque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.get(0)).isEqualTo(null);
        assertThat(lld1.get(-1)).isEqualTo(null);
        lld1.addLast("你好");
        assertThat(lld1.get(0)).isEqualTo("你好");
        lld1.addFirst("1");
        lld1.addLast("wda");
        assertThat(lld1.get(0)).isEqualTo("1");
        assertThat(lld1.get(1)).isEqualTo("你好");
        assertThat(lld1.get(2)).isEqualTo("wda");
        assertThat(lld1.get(-1)).isEqualTo(null);
        assertThat(lld1.get(3)).isEqualTo(null);
    }

    @Test
    public void getRecursiveTextBasic() {
        LinkedListDeque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.getRecursive(0)).isEqualTo(null);
        assertThat(lld1.getRecursive(-1)).isEqualTo(null);
        lld1.addLast("你好");
        assertThat(lld1.getRecursive(0)).isEqualTo("你好");
        lld1.addFirst("1");
        lld1.addLast("wda");
        assertThat(lld1.getRecursive(0)).isEqualTo("1");
        assertThat(lld1.getRecursive(1)).isEqualTo("你好");
        assertThat(lld1.getRecursive(2)).isEqualTo("wda");
        assertThat(lld1.getRecursive(-1)).isEqualTo(null);
        assertThat(lld1.getRecursive(3)).isEqualTo(null);
    }

    @Test
    public void removeFirstTextBasic() {
        LinkedListDeque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.removeLast()).isEqualTo(null);
        lld1.addLast(11);
        lld1.addLast(112);
        lld1.addLast(12);
        lld1.addLast(18);
        List<Integer> except = new ArrayList<>(Arrays.asList(11, 112, 12, 18));
        assertThat(lld1.toList()).isEqualTo(except);
        assertThat(lld1.removeFirst()).isEqualTo(11);
        assertThat(lld1.removeFirst()).isEqualTo(112);
        except.removeFirst();
        except.removeFirst();
        assertThat(lld1.toList()).isEqualTo(except);
        assertThat(lld1.removeFirst()).isEqualTo(12);
        assertThat(lld1.removeFirst()).isEqualTo(18);
        except.removeFirst();
        except.removeFirst();
        assertThat(lld1.toList()).isEqualTo(except);
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void removeLastTextBasic() {
        LinkedListDeque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.removeFirst()).isEqualTo(null);
        lld1.addLast(11);
        lld1.addLast(112);
        lld1.addLast(12);
        lld1.addLast(18);
        List<Integer> except = new ArrayList<>(Arrays.asList(11, 112, 12, 18));
        assertThat(lld1.toList()).isEqualTo(except);
        assertThat(lld1.removeLast()).isEqualTo(18);
        assertThat(lld1.removeLast()).isEqualTo(12);
        except.removeLast();
        except.removeLast();
        assertThat(lld1.toList()).isEqualTo(except);
        assertThat(lld1.removeLast()).isEqualTo(112);
        assertThat(lld1.removeLast()).isEqualTo(11);
        except.removeLast();
        except.removeLast();
        assertThat(lld1.toList()).isEqualTo(except);
        assertThat(lld1.size()).isEqualTo(0);
    }
}
