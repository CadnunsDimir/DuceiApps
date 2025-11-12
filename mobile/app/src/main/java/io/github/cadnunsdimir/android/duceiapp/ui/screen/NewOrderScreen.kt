package io.github.cadnunsdimir.android.duceiapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.github.cadnunsdimir.android.duceiapp.R
import io.github.cadnunsdimir.android.duceiapp.models.FakeData
import io.github.cadnunsdimir.android.duceiapp.models.IState
import io.github.cadnunsdimir.android.duceiapp.service.OrderService
import io.github.cadnunsdimir.android.duceiapp.service.translate
import io.github.cadnunsdimir.android.duceiapp.ui.component.ScreenTopBar
import io.github.cadnunsdimir.android.duceiapp.ui.component.SendButton
import io.github.cadnunsdimir.android.duceiapp.ui.theme.DuceiAppTheme
import io.github.cadnunsdimir.android.duceiapp.ui.viewmodel.OrderViewModel
import io.github.cadnunsdimir.android.duceiapp.ui.viewmodel.OrderViewModel.Companion.ADDRESS_LINE
import io.github.cadnunsdimir.android.duceiapp.ui.viewmodel.OrderViewModel.Companion.AMOUNT
import io.github.cadnunsdimir.android.duceiapp.ui.viewmodel.OrderViewModel.Companion.CELLPHONE
import io.github.cadnunsdimir.android.duceiapp.ui.viewmodel.OrderViewModel.Companion.CUSTOMER_NAME

@Composable
fun NewOrderScreen(
    id: Int?,
    navController: NavController,
    modifier: Modifier = Modifier,
    newOrderViewModel: OrderViewModel = viewModel()
) {
    val flavor = FakeData.flavors
        .stream()
        .filter { flavor -> flavor.id == id }
        .findFirst()
        .orElseThrow { NullPointerException() }

    var orderService = OrderService(navController.context)

    val orderUiState by newOrderViewModel.uiState.collectAsState()

    newOrderViewModel.updateFlavor(flavor)

    Scaffold (
        topBar = {
            ScreenTopBar(
                title = translate("new_order_topbar"),
                navController = navController
            )
        }
    ) { innerPadding ->
        Column (
            modifier = modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp)
        ){
            Box (
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = translate("app_logo_description"),
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(10.dp))
                    )
            }

            Text(
                text = translate("new_order_flavor_selected|${orderUiState.flavorName}")
            )

            if (orderUiState.amount > 0) {
                Text(
                    text = translate("new_order_order_price|${orderUiState.orderTotalPrice}")
                )
            }

            Spacer(
                modifier = modifier
                    .height(20.dp)
            )

            TextField(
                value = "${orderUiState.amount}",
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newOrderViewModel.updateAmount(it) },
                label = { Text(text = translate("new_order_label_amount")) },
                isError = isError(orderUiState, AMOUNT),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            FieldErrorMessage(orderUiState, AMOUNT)
            Spacer(
                modifier = modifier
                    .height(10.dp)
            )

            TextField(
                value = orderUiState.cellphone,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newOrderViewModel.updateCellphone(it) },
                label = { Text(text = translate("new_order_label_cellphone")) },
                isError = isError(orderUiState, CELLPHONE)
            )
            FieldErrorMessage(orderUiState, CELLPHONE)
            Spacer(
                modifier = modifier
                    .height(10.dp)
            )


            TextField(
                value = orderUiState.customerName,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newOrderViewModel.updateCustomerName(it) },
                label = { Text(text = translate("new_order_label_customer_name")) },
                isError = isError(orderUiState, CUSTOMER_NAME)
            )
            FieldErrorMessage(orderUiState, CUSTOMER_NAME)
            Spacer(
                modifier = modifier
                    .height(10.dp)
            )
            TextField(
                value = orderUiState.addressLine,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newOrderViewModel.updateAddressLine(it) },
                label = { Text(text = translate("new_order_label_address_line")) },
                isError = isError(orderUiState, ADDRESS_LINE)
            )
            FieldErrorMessage(orderUiState, ADDRESS_LINE)
            Spacer(
                modifier = modifier
                    .height(20.dp)
            )
            SendButton(
                enabled = orderUiState.formValid,
                onClick = {
                    if (orderUiState.errors.isEmpty())
                        orderService.sendNewOrder(orderUiState)
                }
            )
        }
    }
}

fun isError(state: IState, field: String): Boolean {
    return state.errors.any { it.field == field }
}

@Composable
fun FieldErrorMessage(state: IState, field: String) {
    state.errors
        .filter { it.field == field }
        .forEach {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = translate(it.error),
                color = Color.Red
            )
        }
}

@Preview(showBackground = true)
@Composable
fun NewOrderScreenPreview() {
    val navController = rememberNavController()
    DuceiAppTheme {
        NewOrderScreen(1, navController)
    }
}