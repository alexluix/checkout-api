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
        assertThat(subject.checkout(listOf("001", "002", "003"))).isEqualTo(230)
    }

    @Test
    fun `should calculate duplicate watches`() {
        assertThat(subject.checkout(listOf("001", "002", "003", "001"))).isEqualTo(330)
    }
}
