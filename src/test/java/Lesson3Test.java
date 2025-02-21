import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Lesson3Test {

    @Test
    public void testTextLength() {

        String textMessage = "11111111";
        int textLength = textMessage.length();
        assertTrue(textLength > 15, "It less than you need, add " + (16 - textLength) + " symbols");

        {


        }

    }

    @Test
    public void testActualCookie() {

        String expectedKey = "HomeWork";
        String expectedValue = "hw_value";

        Response response = given()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        Map<String, String> actualCookie = response.cookies();

        assertTrue(actualCookie.containsKey(expectedKey), "There is no cookie named " + expectedKey);
        assertEquals(expectedValue, actualCookie.get(expectedKey), "Value is not correct");

    }


}
