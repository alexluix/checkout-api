package io.maha.checkout.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.whenever
import io.maha.checkout.controller.dto.Price
import io.maha.checkout.domain.CheckoutService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(controllers = [CheckoutController::class])
class CheckoutControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var checkoutService: CheckoutService

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `should calculate total price`() {
        val watchIds = listOf("001", "002", "001", "004", "003")
        val expectedPrice = 360
        whenever(checkoutService.checkout(watchIds)).thenReturn(expectedPrice)

        mockMvc.post("/checkout") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(watchIds)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(objectMapper.writeValueAsString(Price(expectedPrice))) }
        }
    }
}