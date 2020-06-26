package io.maha.checkout.controller

import io.maha.checkout.controller.dto.Price
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/checkout")
class CheckoutController {

    @PostMapping
    fun checkout(@RequestBody watchIds: List<String>) = Price(360)

}
