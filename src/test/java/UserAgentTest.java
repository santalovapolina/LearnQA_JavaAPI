import io.restassured.path.json.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UserAgentTest {

    static Stream<Arguments> userAgentProvider() {
        return Stream.of(
                Arguments.of(
                        UserAgent.GALAXY_NEXUS.getUserAgent(), "Mobile", "No", "Android"
                ),
                Arguments.of(
                        UserAgent.IPAD_CHROME.getUserAgent(), "Mobile", "Chrome", "iOS"
                ),
                Arguments.of(
                        UserAgent.GOOGLEBOT.getUserAgent(), "Googlebot", "Unknown", "Unknown"
                ),
                Arguments.of(
                        UserAgent.WINDOWS_EDGE.getUserAgent(), "Web", "Chrome", "No"
                ),
                Arguments.of(
                        UserAgent.IPHONE_SAFARI.getUserAgent(), "Mobile", "No", "iPhone"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("userAgentProvider")
    void testUserAgent(String userAgent, String expectedPlatform, String expectedBrowser, String expectedDevice) {

        JsonPath response = given()
                .header("User-Agent", userAgent)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();

        String actualPlatform = response.getString("platform");
        String actualBrowser = response.getString("browser");
        String actualDevice = response.getString("device");


        List<String> errors = new ArrayList<>();

        if (!actualPlatform.equals(expectedPlatform)) {
            errors.add("For User Agent: " + userAgent + " platform was expected: " + expectedPlatform + ", but was: " + actualPlatform);
        }
        if (!actualBrowser.equals(expectedBrowser)) {
            errors.add("For User Agent: " + userAgent + " browser was expected: " + expectedBrowser + ", but was: " + actualBrowser);
        }
        if (!actualDevice.equals(expectedDevice)) {
            errors.add("For User Agent: " + userAgent + " device was expected: " + expectedDevice + ", but was: " + actualDevice);
        }

        assertTrue(errors.isEmpty(), String.valueOf(errors));
    }
}
