package io.maha.checkout.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.whenever
import io.maha.checkout.controller.dto.Price
import io.maha.checkout.domain.CheckoutService
import io.maha.checkout.domain.WatchNotFoundException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(controllers = [CheckoutController::class])
class CheckoutControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var checkoutService: CheckoutService

    private val objectMapper = jacksonObjectMapper()

    private val watchIds = listOf("001", "002", "003")
    private val expectedPrice = 360

    @Test
    fun `should calculate total price for given watches`() {
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

    @Test
    fun `should return BadRequest status if watch is not found`() {
        whenever(checkoutService.checkout(watchIds))
            .thenThrow(WatchNotFoundException::class.java)

        mockMvc.post("/checkout") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(watchIds)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest }
        }
    }

    @Test
    fun `should return InternalError status in case of unexpected exception`() {
        whenever(checkoutService.checkout(watchIds))
            .thenThrow(RuntimeException::class.java)

        mockMvc.post("/checkout") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(watchIds)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isInternalServerError }
        }
    }

    @Test
    fun `should return MethodNotAllowed status in case of unsupported HTTP method is used`() {
        mockMvc.get("/checkout") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(watchIds)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isMethodNotAllowed }
        }
    }
}
