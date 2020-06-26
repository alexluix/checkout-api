package io.maha.checkout

import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppTest {

    @LocalServerPort
    var localPort: Int = 0

    @BeforeAll
    fun setup() {
        RestAssured.port = localPort
    }

    @Test
    fun `should perform checkout using HTTP`() {
        val price =
            Given {
                header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                body("[\"001\", \"002\", \"003\"]")
            } When {
                post("/checkout")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                path<Int>("price")
            }

        assertThat(price).isEqualTo(230)
    }
}
