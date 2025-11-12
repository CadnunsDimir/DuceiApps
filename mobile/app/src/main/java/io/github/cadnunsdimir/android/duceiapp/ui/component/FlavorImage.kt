package io.github.cadnunsdimir.android.duceiapp.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.cadnunsdimir.android.duceiapp.R
import io.github.cadnunsdimir.android.duceiapp.models.Flavor

@Composable
fun FlavorImage(flavor: Flavor){
    AsyncImage(
        model = flavor.urlImage,
        contentDescription = "Imagem do sabor ${flavor.title}",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(75.dp)
            .clip(CircleShape),
        placeholder = painterResource(id = R.drawable.image),
        error = painterResource(id = R.drawable.image)
    )
}