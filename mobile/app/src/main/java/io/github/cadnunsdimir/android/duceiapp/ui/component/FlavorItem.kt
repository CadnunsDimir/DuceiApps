package io.github.cadnunsdimir.android.duceiapp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.cadnunsdimir.android.duceiapp.models.Flavor

@Composable
fun FlavorItem(flavor: Flavor, onClick: (Flavor)-> Unit){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        onClick = { onClick(flavor) }
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ){
            FlavorImage(flavor)
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ){

                Text(flavor.title)
                Text("R$ ${flavor.price}")
                Text(
                    text = "${flavor.stock} disponíveis",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
