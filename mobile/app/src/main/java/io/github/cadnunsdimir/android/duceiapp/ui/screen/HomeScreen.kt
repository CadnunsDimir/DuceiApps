package io.github.cadnunsdimir.android.duceiapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.cadnunsdimir.android.duceiapp.AppTopBar
import io.github.cadnunsdimir.android.duceiapp.FlavorItem
import io.github.cadnunsdimir.android.duceiapp.R
import io.github.cadnunsdimir.android.duceiapp.models.FakeData

@Composable
fun HomeScreen(
    customerName: String,
    navController: NavController,
    modifier: Modifier = Modifier) {
    var flavors = FakeData.flavors

    Scaffold (
        topBar = {
            AppTopBar(
                title = null,
                navController = navController
            )
        }
    ){  innerPadding ->
        Column(
            modifier = modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = "Dulceí Logo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "Olá, $customerName!",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                "Qual vai sua opção de hoje?"
            )
            LazyColumn {
                items(flavors){ f->
                    FlavorItem(
                        flavor = f,
                        onClick = { flavor ->
                            navController.navigate("new-order/${flavor.id}")
                        }
                    )
                }
            }
        }
    }
}