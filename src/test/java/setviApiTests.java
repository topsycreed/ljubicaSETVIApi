import DTO.UploadFreeTextApiRequestDto;
import DTO.UploadUrlHtmlApiRequestDto;
import config.TestPropertiesConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class setviApiTests {
    private static final String UPLOAD_FREE_TEXT_URL = "api/rfq/upload-free-text";
    private static final String UPLOAD_URL_HTML_URL = "api/rfq/upload-url-html";
    TestPropertiesConfig configProperties = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    @Test
    void uploadUrlHtmlSmokeTest() {
        UploadUrlHtmlApiRequestDto body = UploadUrlHtmlApiRequestDto.builder()
                .statusId("string")
                .url("https://www.webstaurantstore.com/choice-24-x-18-x-1-2-green-polyethylene-cutting-board/40724185GN.html")
                .topK(3)
                .threshold(0.5)
                .enablePrivateLabelRanking(false)
                .enableStockProductRanking(false)
                .enableVendorRanking(false)
                .enableProductRanking(true)
                .build();

        Response response = given()
                .baseUri(configProperties.getBaseUrl())
                .filter(new AllureRestAssured())
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .header("Authorization", configProperties.getApiKey())
                .body(body).
                when()
                .post(UPLOAD_URL_HTML_URL)
                .andReturn();
        response.body().prettyPrint();
        String status = response.path("status");

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(status).isEqualTo("Succeeded");
    }

    @Test
    void uploadUrlHtmlTest() {
        UploadUrlHtmlApiRequestDto body = UploadUrlHtmlApiRequestDto.builder()
                .statusId("string")
                .url("https://www.webstaurantstore.com/choice-24-x-18-x-1-2-green-polyethylene-cutting-board/40724185GN.html")
                .topK(3)
                .threshold(0.5)
                .enablePrivateLabelRanking(false)
                .enableStockProductRanking(false)
                .enableVendorRanking(false)
                .enableProductRanking(true)
                .build();

        Response response = given()
                .baseUri(configProperties.getBaseUrl())
                .filter(new AllureRestAssured())
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .header("Authorization", configProperties.getApiKey())
                .body(body).
                when()
                .post(UPLOAD_URL_HTML_URL)
                .andReturn();
        response.body().prettyPrint();
        int totalItems = response.path("result.summary.totalItems");
        String productName = response.path("result.matchedItems[0].productName");
        int percentage = response.path("result.matchedItems[0].matchedInternalProducts[0].percentage");

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(totalItems).isPositive();
        Assertions.assertThat(productName).contains("Green Polyethylene Cutting Board");
        Assertions.assertThat(percentage).isGreaterThanOrEqualTo(99);
    }

    @Test
    void uploadFreeTextSmokeTest() {
        UploadFreeTextApiRequestDto body = UploadFreeTextApiRequestDto.builder()
                .statusId("string")
                .text("Polyethylene")
                .topK(3)
                .threshold(0.5)
                .enablePrivateLabelRanking(false)
                .enableStockProductRanking(false)
                .enableVendorRanking(false)
                .enableProductRanking(true)
                .build();

        Response response = given()
                .baseUri(configProperties.getBaseUrl())
                .filter(new AllureRestAssured())
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .header("Authorization", configProperties.getApiKey())
                .body(body).
                when()
                .post(UPLOAD_FREE_TEXT_URL)
                .andReturn();
        response.body().prettyPrint();

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    void uploadFreeTextTest() {
        UploadFreeTextApiRequestDto body = UploadFreeTextApiRequestDto.builder()
                .statusId("string")
                .text("Polyethylene")
                .topK(3)
                .threshold(0.5)
                .enablePrivateLabelRanking(false)
                .enableStockProductRanking(false)
                .enableVendorRanking(false)
                .enableProductRanking(true)
                .build();

        Response response = given()
                .baseUri(configProperties.getBaseUrl())
                .filter(new AllureRestAssured())
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .header("Authorization", configProperties.getApiKey())
                .body(body).
                when()
                .post(UPLOAD_FREE_TEXT_URL)
                .andReturn();
        response.body().prettyPrint();
        int totalItems = response.path("result.summary.totalItems");
        String productName = response.path("result.matchedItems[0].productName");
        int percentage = response.path("result.matchedItems[0].matchedInternalProducts[0].percentage");

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(totalItems).isPositive();
        Assertions.assertThat(productName).isEqualTo("Polyethylene");
        Assertions.assertThat(percentage).isGreaterThanOrEqualTo(99);
    }
}
