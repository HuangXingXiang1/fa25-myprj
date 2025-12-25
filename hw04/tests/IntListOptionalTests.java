import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntListOptionalTests {
     public static void main(String[] args) {
        IntList text = new IntList(18, null);
        text = new IntList(32, text);
        text = new IntList(98, text);
        text.addLast(2);
        text.addFirst(350);
        System.out.println(text.get(0));
        System.out.println(text.get(4));
        System.out.println(text.sum());
    }
}
