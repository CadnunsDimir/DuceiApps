package io.github.cadnunsdimir.android.duceiapp.models

import android.R
import java.math.BigDecimal

data class Flavor (
    var id: Int,
    val title: String,
    val urlImage: String,
    val price: BigDecimal,
    val stock: Int
)

object FakeData{
    var urlImageFake = "https://bolosparavender.com/wp-content/uploads/2018/10/sabores-de-bolo-no-pote-mais-vendidos-750x750.jpg";
    val flavors: List<Flavor> = listOf(
        Flavor(1, "Brigadeiro", urlImageFake, BigDecimal(10), 10),
        Flavor(2, "Leite Ninho", urlImageFake, BigDecimal(10), 10),
        Flavor(3, "Leite Ninho com morango", urlImageFake, BigDecimal(10), 10)
    )
}