package io.maha.checkout.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CheckoutServiceTest {

    private val subject = CheckoutService()

    @Test
    fun `should fail when at least one watch is not found`() {
        assertThrows<WatchNotFoundException> {
            subject.checkout(listOf("001", "000"))
        }
    }

    @Test
    fun `should handle empty list`() {
        assertThat(subject.checkout(emptyList())).isEqualTo(0)
    }

    @Test
    fun `should calculate single watches`() {
        assertThat(subject.checkout(listOf("001", "002", "003")))
                .isEqualTo(230)
    }

    @Test
    fun `should calculate duplicate watches`() {
        assertThat(subject.checkout(
                listOf("001", "002", "003", "001"))
        ).isEqualTo(330)
    }

    @Test
    fun `should calculate a single discount`() {
        assertThat(subject.checkout(
                listOf("001", "002", "001", "001", "004", "003")
        )).isEqualTo(360)
    }

    @Test
    fun `should calculate discount batches with remaining items`() {
        val discountBatch1 = listOf("001", "001", "001")
        val discountBatch2 = listOf("002", "002")

        assertThat(subject.checkout(
                discountBatch1.plus(discountBatch2)
                        .plus(listOf("001", "002", "004"))
        )).isEqualTo(200 + 120 + 100 + 80 + 30)
    }

    @Test
    fun `should calculate multiple discount batches of same type`() {
        val discountBatch = listOf("001", "001", "001")

        assertThat(subject.checkout(
                discountBatch.plus(discountBatch).plus(discountBatch)
                        .plus(listOf("001", "002"))
        )).isEqualTo(200 * 3 + 100 + 80)
    }
}
