package ru.jiminator.mqrestclientregistr

import io.restassured.RestAssured.get
import io.restassured.RestAssured.given
import io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.hasItem
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest(classes = [MqRestClientRegistrApplication::class])
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
    fun `01 - Test index route of client resource`() {
        get("/client")
            .then().assertThat()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("getSchema.json"))
    }

    @Test
    fun `02 - Test find route of client resource by id`() {
        get("/client/2")
            .then().assertThat()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("getSchema1.json"))
            .body("id", `is` (2))
            .body("factAddress", `is` ("45mkr"))
            .body("regAddress", `is` ("44mkr"))
            .body("phone", `is` (89889778865))
    }
}