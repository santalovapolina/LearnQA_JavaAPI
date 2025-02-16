import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LongTimeJobTest {

    @Test
    public void testLongTimeJob() throws InterruptedException {

        String jobUrl = "https://playground.learnqa.ru/ajax/api/longtime_job";

        //СОздаем задачу
        Response createNewJobResponse = given()
                .when()
                .get(jobUrl)
                .andReturn();

        String token = createNewJobResponse.jsonPath().getString("token");
        int waitTime = createNewJobResponse.jsonPath().getInt("seconds");

        System.out.println("New task created. Token: " + token + ", Wait time: " + waitTime + " seconds");

        //Делаем запрос до готовности задачи
        Response beforeReadyResponse = given()
                .queryParam("token", token)
                .when()
                .get(jobUrl)
                .andReturn();

        String actualBeforeStatus = beforeReadyResponse.jsonPath().getString("status");
//        System.out.println("Status before job ends" + actualBeforeStatus);

        //Проверяем, что задача еще не готова
        assertEquals("Job is NOT ready", actualBeforeStatus);

        //Ждем нужное количество секунд
        Thread.sleep(waitTime * 1000L);

        //Делаем запрос после готовности задачи
        Response afterReadyResponse = given()
                .queryParam("token", token)
                .when()
                .get(jobUrl)
                .andReturn();

        String actualAfterStatus = afterReadyResponse.jsonPath().getString("status");
        String result = afterReadyResponse.jsonPath().getString("result");

//        System.out.println("Status after job ends: " + actualAfterStatus);
//        System.out.println("Result: " + result);

        //Проверяем, что задача завершена и есть результат
        assertEquals("Job is ready", actualAfterStatus);
        assertNotNull(result);

    }


}
