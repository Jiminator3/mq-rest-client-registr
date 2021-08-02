package ru.jiminator.mqrestclientregistr

import io.restassured.RestAssured.get
import io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.jdbc.SqlGroup


@SpringBootTest(classes = [MqRestClientRegistrApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup(
    Sql(
        "/import-clients.sql",
        config = SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
    ),
    Sql(
        "/delete-import-clients.sql",
        config = SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
)
class WebMvcClientResourcesTest {

    @Test
    fun `01 - Test index route of client resource`(@Autowired restTemplate: TestRestTemplate) {
        val url = restTemplate.rootUri
        get("$url/clients")
            .then().assertThat()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("getSchema.json"))
    }

    @Test
    fun `02 - Test find client resource by id`(@Autowired restTemplate: TestRestTemplate) {
        val url = restTemplate.rootUri
        get("$url/client?id=2")
            .then().assertThat()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("getSchema1.json"))
            .body("id", `is` (2))
            .body("factAddress", `is` ("45mkr"))
            .body("regAddress", `is` ("44mkr"))
            .body("phone", `is` (89889778865))
    }

    @Test
    fun `03 - Test incorrect response findById`(@Autowired restTemplate: TestRestTemplate) {
        val url = restTemplate.rootUri
        get("$url/client?id=asd")
            .then().assertThat()
            .statusCode(400)
    }

    @Test
    fun `04 - Test post client`(@Autowired restTemplate: TestRestTemplate) {
        val url = restTemplate.rootUri
        get("$url/post?fAddress=asd&rAddress=dsa&phone=989")
            .then().assertThat()
            .body(containsString("Post Client Successful"))
            .statusCode(200)
    }

    @Test
    fun `05 - Test incorrect post client`(@Autowired restTemplate: TestRestTemplate) {
        val url = restTemplate.rootUri
        get("$url/post?fAddress=123&rAddress=123&phone=qweasd")
            .then().assertThat()
            .statusCode(400)
    }
}
