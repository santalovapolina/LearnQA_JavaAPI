import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class Lesson3Test {

    @Test
    public void testTextLength() {

        String textMessage = "11111111";
        int textLength = textMessage.length();
        assertTrue(textLength > 15, "It less than you need, add " + (16 - textLength) + " symbols");

    }

    @Test
    public void testCookieExists() {

        String expectedKey = "HomeWork";

        Response response = given()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        Map<String, String> actualCookie = response.cookies();

        assertTrue(actualCookie.containsKey(expectedKey), "There is no cookie named " + expectedKey);

    }


    @Test
    public void testCookieValue() {

        String expectedKey = "HomeWork";
        String expectedValue = "hw_value";

        Response response = given()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        Map<String, String> actualCookie = response.cookies();

        assertEquals(expectedValue, actualCookie.get(expectedKey), "Value is not correct");

    }


    @Test
    public void testHeaderExists() {

        String expectedHeader = "x-secret-homework-header";

        Response response = given()
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();


        Headers headers = response.getHeaders();
        Header header = headers.get(expectedHeader);

        assertNotNull(header, "There is no header named " + expectedHeader);

    }


    @Test
    public void testHeaderValue() {

        String expectedHeader = "x-secret-homework-header";
        String expectedValue = "Some secret value";

        Response response = given()
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        Headers headers = response.getHeaders();
        Header header = headers.get(expectedHeader);

        assertEquals(expectedValue, header.getValue(), "Value is not correct");

    }
}
