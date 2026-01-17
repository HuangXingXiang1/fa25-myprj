import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {
    @Test
    public void addFirstTebxBasic() {
        ArrayDeque61B<Integer> lld1 = new ArrayDeque61B<>();
        lld1.addFirst(12);
        assertThat(lld1.toList()).containsExactly(12).inOrder();
        lld1.addFirst(13);
        assertThat(lld1.toList()).containsExactly(13, 12).inOrder();
        lld1.addFirst(14);
        assertThat(lld1.toList()).containsExactly(14, 13, 12).inOrder();
        lld1.addFirst(15);
        assertThat(lld1.toList()).containsExactly(15 ,14, 13, 12).inOrder();
        assertThat(lld1.size()).isEqualTo(4);
    }
    @Test
    public void addLastTebxBasic() {
        ArrayDeque61B<Integer> lld1 = new ArrayDeque61B<>();
        lld1.addLast(12);
        assertThat(lld1.toList()).containsExactly(12).inOrder();
        lld1.addLast(13);
        assertThat(lld1.toList()).containsExactly(12, 13).inOrder();
        lld1.addLast(14);
        assertThat(lld1.toList()).containsExactly(12, 13, 14).inOrder();
        lld1.addLast(15);
        assertThat(lld1.toList()).containsExactly(12, 13, 14, 15).inOrder();
        assertThat(lld1.size()).isEqualTo(4);
    }

    @Test
    public void addLastANDaddFristTebxBasic() {
        ArrayDeque61B<Integer> lld1 = new ArrayDeque61B<>();
        lld1.addLast(12);
        assertThat(lld1.toList()).containsExactly(12).inOrder();
        lld1.addFirst(19);
        assertThat(lld1.toList()).containsExactly(19, 12).inOrder();
        lld1.addLast(13);
        assertThat(lld1.toList()).containsExactly( 19, 12, 13).inOrder();

    }


    @Test
    public void getTextBasic() {
        ArrayDeque61B<String> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.get(0)).isEqualTo(null);
        assertThat(lld1.get(-1)).isEqualTo(null);
        lld1.addLast("身体健康");
        assertThat(lld1.get(0)).isEqualTo("身体健康");
        lld1.addFirst("新年快乐");
        assertThat(lld1.get(0)).isEqualTo("新年快乐");
        assertThat(lld1.get(1)).isEqualTo("身体健康");
        lld1.addLast("万事如意");
        assertThat(lld1.get(2)).isEqualTo("万事如意");
        lld1.addFirst("天天开心");
        lld1.addFirst("平安喜乐");
        assertThat(lld1.get(0)).isEqualTo("平安喜乐");
        assertThat(lld1.get(1)).isEqualTo("天天开心");
        lld1.addLast("福如东海");
        assertThat(lld1.get(5)).isEqualTo("福如东海");
        assertThat(lld1.get(-1)).isEqualTo(null);
        assertThat(lld1.get(6)).isEqualTo(null);
        lld1.addLast(null);
        assertThat(lld1.get(6)).isEqualTo(null);
    }

    @Test
    public void isEmptyTextBasic() {
        ArrayDeque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        lld1.addLast(10);
        assertThat(lld1.isEmpty()).isFalse();
        lld1.removeFirst();
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void getSizeTextBasic() {
        ArrayDeque61B<String > lld1 = new ArrayDeque61B<>();
        assertThat(lld1.size()).isEqualTo(0);
        lld1.addFirst("平安喜乐");
        lld1.addFirst("平安喜乐");
        lld1.addFirst("平安喜乐");
        lld1.addFirst("平安喜乐");
        lld1.addFirst("平安喜乐");
        assertThat(lld1.size()).isEqualTo(5);
        lld1.addFirst("身体健康");
        assertThat(lld1.size()).isEqualTo(6);
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void toListTextBasic() {
        ArrayDeque61B<Integer> lld1 = new ArrayDeque61B<>();
        List<Integer> lld0 = new ArrayList<>();
        assertThat(lld1.toList()).isEqualTo(lld0);
        lld1.addFirst(10);
        lld1.addLast(11);
        lld1.addLast(12);
        lld1.addFirst(15);
        assertThat(lld1.toList()).containsExactly(15, 10, 11, 12).inOrder();
        lld1.addFirst(11);
        lld1.addFirst(10);
        lld1.addLast(10);
        assertThat(lld1.toList()).containsExactly(10, 11, 15, 10 ,11, 12, 10).inOrder();
    }

    @Test
    public void removeFirstTextBasic() {
        ArrayDeque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.removeFirst()).isEqualTo(null);
        lld1.addLast(10); // [10]
        assertThat(lld1.removeFirst()).isEqualTo(10);
        List<Integer> lld0 = new ArrayList<>();
        assertThat(lld1.toList()).isEqualTo(lld0);
        lld1.addFirst(11);
        lld1.addLast(123);
        lld1.addFirst(19);
        lld1.addFirst(20); // [20, 19, 11, 123]
        assertThat(lld1.removeFirst()).isEqualTo(20);
        assertThat(lld1.toList()).containsExactly(19, 11, 123).inOrder();
        assertThat(lld1.size()).isEqualTo(3);
    }

    @Test
    public void removeLastTextBasic() {
        ArrayDeque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.removeLast()).isEqualTo(null);
        lld1.addFirst(10); // [10]
        assertThat(lld1.removeLast()).isEqualTo(10);
        List<Integer> lld0 = new ArrayList<>();
        assertThat(lld1.toList()).isEqualTo(lld0);
        lld1.addFirst(11);
        lld1.addLast(123);
        lld1.addFirst(19);
        lld1.addFirst(20);
        assertThat(lld1.removeLast()).isEqualTo(123);
        assertThat(lld1.toList()).containsExactly(20, 19, 11).inOrder();
        assertThat(lld1.size()).isEqualTo(3);
    }

    @Test
    public void expansionTextBasic() {
        ArrayDeque61B<Integer> lld1 = new ArrayDeque61B<>();
        List<Integer> text = new ArrayList<>();
        for (int i = 0; i < 101; i++) {
            lld1.addFirst(i);
            text.addFirst(i);
        }
        assertThat(lld1.toList()).isEqualTo(text);
        for (int i = 200; i < 301; i++) {
            lld1.addLast(i);
            text.addLast(i);
        }
        assertThat(lld1.toList()).isEqualTo(text);
        assertThat(lld1.get(0)).isEqualTo(text.get(0));
        assertThat(lld1.get(200)).isEqualTo(text.get(200));
        assertThat(lld1.get(5)).isEqualTo(text.get(5));
        assertThat(lld1.get(39)).isEqualTo(text.get(39));
        for (int i = 0; i < 100; i++) {
            lld1.removeFirst();
            text.removeFirst();
        }
        assertThat(lld1.toList()).isEqualTo(text);
        for (int i = 0; i < 1000000; i++) {
            lld1.addFirst(i);
        }
        lld1.get(999999);
    }

    @Test
    public void shrinkageTextBasic() {
        ArrayDeque61B<Integer> lld1 = new ArrayDeque61B<>();
        for (int i = 0; i < 16; i++) {
            lld1.addFirst(i);
        }
        for (int i = 0; i < 15; i++) {
            lld1.removeFirst();
        }
        for (int i = 0; i < 100000; i++) {
            lld1.addFirst(i);
        }
        for (int i = 0; i < 99999; i++) {
            lld1.removeLast();
        }
        assertThat(lld1.length).isEqualTo(13);
    }

    @Test
    public void iterationTextBasic() {
        ArrayDeque61B<String> lld1 = new ArrayDeque61B<>();
        lld1.addFirst("深远");
        lld1.addFirst("平静");
        lld1.addFirst("智慧");
        assertThat(lld1).containsExactly("智慧", "平静", "深远");
        ArrayDeque61B<Integer> lld2 = new ArrayDeque61B<>();
        lld2.addLast(10);
        lld2.addLast(11);
        lld2.addLast(12);
        lld2.addLast(13);
        lld2.addLast(14);
        lld2.addLast(15);
        lld2.addLast(16);
        lld2.addLast(17);
        lld2.addLast(18);
        lld2.addLast(19);
        assertThat(lld2).containsExactly(10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
    }

    @Test
    public void testEqualDeques61B() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        Deque61B<String> ad2 = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        ad2.addLast("front");
        ad2.addLast("middle");
        ad2.addLast("back");

        assertThat(ad).isEqualTo(ad2);
    }

    @Test
    public void toStringTextBasic() {
        ArrayDeque61B<String> lld1 = new ArrayDeque61B<>();
//        assertThat(lld1.toString()).isEqualTo("[]");
        lld1.addFirst("深远");
        lld1.addFirst("平静");
        lld1.addFirst("智慧");
//        assertThat(lld1.toString()).isEqualTo("[智慧, 平静, 深远]");
        ArrayDeque61B<Integer> lld2 = new ArrayDeque61B<>();
        lld2.addLast(10);
        lld2.addLast(11);
        lld2.addLast(12);
        lld2.addLast(13);
        lld2.addLast(14);
        lld2.addLast(15);
        lld2.addLast(16);
        lld2.addLast(17);
        lld2.addLast(18);
        lld2.addLast(19);
//        assertThat(lld2.toString()).isEqualTo("[10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
        System.out.println(lld2);
    }
}

