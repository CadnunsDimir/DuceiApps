package io.github.cadnunsdimir.android.duceiapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import io.github.cadnunsdimir.android.duceiapp.models.Flavor
import io.github.cadnunsdimir.android.duceiapp.models.OrderState
import io.github.cadnunsdimir.android.duceiapp.models.ValidationError
import io.github.cadnunsdimir.android.duceiapp.models.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal
import kotlin.collections.MutableList

class OrderViewModel: ViewModel() {
    companion object{
        const val CUSTOMER_NAME = "customerName"
        const val ADDRESS_LINE ="addressLine"
        const val AMOUNT ="amount"
        const val CELLPHONE ="cellphone"
    }
    private val _uiState = MutableStateFlow(OrderState())
    val uiState: StateFlow<OrderState> = _uiState.asStateFlow()

    fun updateFormValidity() {
        _uiState.value = _uiState.value.copy(
            formValid = _uiState.value.errors.isEmpty() && requiredFieldsNotNull()
        )
    }

    private fun requiredFieldsNotNull() : Boolean {
        val state = _uiState.value
        return state.amount > 0 && state.cellphone != null && state.addressLine != null
    }

    fun updateCustomerName(name: String) {
        clearErrors(CUSTOMER_NAME)
        _uiState.value = _uiState.value.copy(
            customerName = name,
            errors = joinErrors(Validator(CUSTOMER_NAME, name)
                .minLen()
                .build())
        )
        updateFormValidity()
    }

    fun updateAddressLine(address: String) {
        clearErrors(ADDRESS_LINE)
        _uiState.value = _uiState.value.copy(
            addressLine = address,
            errors = joinErrors(Validator(ADDRESS_LINE, address)
                .minLen()
                .build())
        )
        updateFormValidity()
    }

    private fun clearErrors(field: String) {
        _uiState.value = _uiState.value.copy(
            errors = _uiState.value.errors.filter { it.field != field }
        )
    }

    fun updateFlavor(flavor: Flavor) {
        _uiState.value = _uiState.value.copy(
            flavorName = flavor.title,
            itemPrice = flavor.price
        )
    }

    fun updateAmount(amount: String) {
        clearErrors(AMOUNT)
        val parsedAmount = formatInt(amount)
        val orderTotalPrice = _uiState.value.itemPrice.multiply(BigDecimal(parsedAmount))
        _uiState.value = _uiState.value.copy(
            amount = formatInt(amount),
            orderTotalPrice = orderTotalPrice,
            errors = joinErrors(Validator(AMOUNT, amount)
                .minValue(1f)
                .maxValue(10f)
                .build())
        )
        updateFormValidity()
    }

    private fun formatInt(value: String?): Int {
        val onlyNumbers = value?.replace(regex = "\\D".toRegex(), replacement = "")?:"0"
        if (onlyNumbers.isEmpty()) return 0
        return onlyNumbers.toInt()
    }

    fun updateCellphone(cellphone: String) {
        clearErrors(CELLPHONE)

        _uiState.value = _uiState.value.copy(
            cellphone = formatCellphone(cellphone),
            errors = joinErrors(Validator(CELLPHONE, cellphone)
                .cellphoneBrLength()
                .build())
        )
        updateFormValidity()
    }

    private fun joinErrors(validationErrors: List<ValidationError>): List<ValidationError> {
        val joinedErrors: MutableList<ValidationError> = mutableListOf()
        joinedErrors.addAll(validationErrors)
        joinedErrors.addAll(_uiState.value.errors)
        return joinedErrors
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
