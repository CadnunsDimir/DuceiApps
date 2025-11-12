package io.github.cadnunsdimir.android.duceiapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import io.github.cadnunsdimir.android.duceiapp.models.Flavor
import io.github.cadnunsdimir.android.duceiapp.models.OrderState
import io.github.cadnunsdimir.android.duceiapp.models.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal

class OrderViewModel: ViewModel() {
    companion object{
        const val CUSTOMER_NAME = "customerName"
        const val ADDRESS_LINE ="addressLine"
        const val AMOUNT ="amount"

        const val CELLPHONE ="cellphone"

    }
    private val _uiState = MutableStateFlow(OrderState())
    val uiState: StateFlow<OrderState> = _uiState.asStateFlow()

    fun updateCustomerName(name: String) {
        _uiState.value = _uiState.value.copy(
            customerName = name,
            errors = Validator(CUSTOMER_NAME, name)
                .minLen()
                .build()
        )
    }

    fun updateAddressLine(address: String) {
        _uiState.value = _uiState.value.copy(
            addressLine = address,
            errors = Validator(ADDRESS_LINE, address)
                .minLen()
                .build()
        )
    }

    fun updateFlavor(flavor: Flavor) {
        _uiState.value = _uiState.value.copy(
            flavorName = flavor.title,
            itemPrice = flavor.price
        )
    }

    fun updateAmount(amount: String) {
        val parsedAmount = formatInt(amount)
        val orderTotalPrice = _uiState.value.itemPrice.multiply(BigDecimal(parsedAmount))
        _uiState.value = _uiState.value.copy(
            amount = formatInt(amount),
            orderTotalPrice = orderTotalPrice,
            errors = Validator(AMOUNT, amount)
                .minValue(1f)
                .maxValue(10f)
                .build()
        )
    }

    private fun formatInt(value: String?): Int {
        val onlyNumbers = value?.replace(regex = "\\D".toRegex(), replacement = "")?:"0"
        if (onlyNumbers.isEmpty()) return 0
        return onlyNumbers.toInt()
    }

    fun updateCellphone(cellphone: String) {
        _uiState.value = _uiState.value.copy(
            cellphone = formatCellphone(cellphone),
            errors = Validator(CELLPHONE, cellphone)
                .cellphoneBrLength()
                .build()
        )
    }

    private fun formatCellphone(cellphone: String): String {
        val onlyNumbers = cellphone.replace(regex = "\\D".toRegex(), replacement = "")
        if(onlyNumbers.length < 11) return cellphone

        val regexBrPhone = "(\\d{2})(\\d{5})(\\d{4})".toRegex()
        return regexBrPhone.replace(
                onlyNumbers,
                "($1) $2-$3"
            )
    }
}
