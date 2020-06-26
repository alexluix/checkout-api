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
        if (!watchIds.all { watches.containsKey(it) }) {
            throw WatchNotFoundException()
        }

        val byCountWatches: Map<Watch, Int> = watchIds
            .mapNotNull { watches[it] }
            .groupingBy { it }.eachCount()

        val prices = byCountWatches.entries.map { watchGroup ->
            val watch = watchGroup.key
            val count = watchGroup.value

            if (watch.discount != null) {
                val discountQuantity = watch.discount.quantity
                val discountPrice = watch.discount.price

                val noOfDiscounts = count / discountQuantity
                val remainingItems = count % discountQuantity

                noOfDiscounts * discountPrice + remainingItems * watch.price
            } else {
                watch.price * count
            }
        }

        return prices.sum()
    }
}
