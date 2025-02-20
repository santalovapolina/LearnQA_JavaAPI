import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShortMessageTest {

    @Test
    public void testTextLength() {

        String textMessage = "11111111";
        int textLength = textMessage.length();

        assertTrue(textLength > 15, "It less than you need, add " + (16 - textLength) + " symbols");


        {


        }

    }


}
