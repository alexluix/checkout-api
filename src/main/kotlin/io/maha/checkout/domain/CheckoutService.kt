package io.maha.checkout.domain

import org.springframework.stereotype.Service

@Service
class CheckoutService {

    private val watches = mapOf(
            "001" to Watch("Rolex", 100, Discount(3, 200)),
            "002" to Watch("Michael Kors", 80, Discount(2, 120)),
            "003" to Watch("Swatch", 50),
            "004" to Watch("Casio", 30)
    )

    fun checkout(watchIds: List<String>): Int {
        return 0
    }

}
