package io.maha.checkout.controller

import io.maha.checkout.controller.dto.Price
import io.maha.checkout.domain.CheckoutService
import io.maha.checkout.domain.WatchNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/checkout")
class CheckoutController(
    val checkoutService: CheckoutService
) {

    @PostMapping
    fun checkout(@RequestBody watchIds: List<String>) =
        Price(checkoutService.checkout(watchIds))

    @ExceptionHandler
    fun handleException(exception: WatchNotFoundException): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
}
