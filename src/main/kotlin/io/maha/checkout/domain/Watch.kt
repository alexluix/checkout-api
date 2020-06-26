package io.maha.checkout.domain

data class Watch(
    val name: String,
    val price: Int,
    val discount: Discount? = null
)
