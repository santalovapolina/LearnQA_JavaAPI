import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class LongRedirectTest {

    @Test
    public void testPrintFinalRedirect(){

        String url = "https://playground.learnqa.ru/api/long_redirect";

        while (true) {
            Response response = given()
                    .redirects().follow(false) // Отключаем автоматическое следование
                    .when()
                    .get(url)
                    .andReturn();

            String nextUrl = response.getHeader("Location");

            if (nextUrl == null) { // Если нет заголовка Location получим финальный адрес
                System.out.println("Final URL" + url);
                break;
            } else {
                System.out.println("Redirect to" + nextUrl);
                url = nextUrl; // Переходим на следующий URL
            }
        }
    }


    @Test
    public void testPrintNextRedirect() {

        Response response = given()
                .redirects().follow(false) // Отключаем автоматическое следование
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        String nextUrl = response.getHeader("Location");
        System.out.println("Redirect to:" + nextUrl);
    }

}
