package io.github.cadnunsdimir.android.duceiapp.service


import android.content.Context
import android.content.Intent
import android.widget.Toast
import io.github.cadnunsdimir.android.duceiapp.models.OrderState
import java.net.URLEncoder
import androidx.core.net.toUri
import io.github.cadnunsdimir.android.duceiapp.BuildConfig

class OrderService(val context: Context) {
    fun sendNewOrder(order: OrderState, openOnWhatsApp: Boolean = true) {
        try {
            val encodedMessage = encodeOrder(order)
            val storeCellNumber = BuildConfig.SEND_MESSAGE_WHATSAPP
            val uri = "https://wa.me/$storeCellNumber?text=$encodedMessage".toUri()
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (openOnWhatsApp) {
                intent.setPackage("com.whatsapp")
            }
            context.startActivity(intent)
        }catch (ex: Exception) {
            if (openOnWhatsApp) {
                sendNewOrder(order, false)
            } else {
                print(ex.message)
                Toast.makeText(context, "Erro ao tentar abrir o WhatsApp.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun encodeOrder(order: OrderState): String {
        val text = "Olá, eu me chamo ${order.customerName} e gostaria de " +
                "Solicitar ${order.amount} ${order.flavorName}. Segue abaixo meus dados de contato:\n\n" +
                "Tel: ${order.cellphone}, \n" +
                "Endereço: ${order.addressLine}, \n" +
                "Quantidade: ${order.amount}, \n" +
                "Valor unitário: R$ ${order.itemPrice}, \n" +
                "Valor Total do Pedido: R$ ${order.orderTotalPrice}\n" +
                "\n" +
                "Atenciosamente,\n" +
                order.customerName
        return URLEncoder.encode(text, "UTF-8")
    }
}