import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetCorrectPasswordTest {

    @Test
    public void testFindCorrectPassword() {

        String login = "super_admin";
        String authUrl = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";
        String checkCookieUrl = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";

        List<String> passwords = Arrays.asList(
                "123456", "password", "123456789", "12345678",
                "12345", "qwerty", "abc123", "football", "1234567",
                "monkey", "111111", "letmein", "1234", "1234567890",
                "dragon", "baseball", "sunshine", "iloveyou", "trustno1",
                "princess", "adobe123[a]", "123123", "welcome", "login", "admin",
                "qwerty123", "solo", "1q2w3e4r", "master", "photoshop[a]", "1qaz2wsx",
                "qwertyuiop", "ashley", "mustang", "121212", "starwars", "654321", "bailey",
                "access", "flower", "555555", "passw0rd", "shadow", "lovely", "7777777", "michael",
                "!@#$%^&*", "jesus", "password1", "superman", "hello", "charlie", "888888", "696969",
                "hottie", "freedom", "aa123456", "qazwsx", "ninja", "azerty", "loveme", "whatever", "donald",
                "batman", "zaq1zaq1", "123qwe"
        );

        for (String password : passwords) {

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("login", login);
            requestBody.put("password", password);

            Response getAuthResponse = given()
                    .body(requestBody)
                    .when()
                    .post(authUrl)
                    .andReturn();

            String authCookie = getAuthResponse.getCookie("auth_cookie");

            Response checkAuthResponse = given()
                    .cookies("auth_cookie", authCookie)
                    .when()
                    .get(checkCookieUrl)
                    .andReturn();

            String responseBody = checkAuthResponse.getBody().asString();

            if (!responseBody.contains("You are NOT authorized")) {
                System.out.println("Correct password: " + password);
                System.out.println(responseBody);
                break; //Пароль может быть только один верный
            }
        }
    }
}

