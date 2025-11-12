package io.github.cadnunsdimir.android.duceiapp.models

import java.math.BigDecimal

data class OrderState(
    val customerName: String = "",
    val addressLine: String = "",
    val flavorName: String = "",
    val amount: Int = 0,
    val itemPrice: BigDecimal= BigDecimal(10),
    val cellphone: String = "",
    val orderTotalPrice: BigDecimal = BigDecimal(0),
    override val errors: List<ValidationError> = mutableListOf()): IState {
}
