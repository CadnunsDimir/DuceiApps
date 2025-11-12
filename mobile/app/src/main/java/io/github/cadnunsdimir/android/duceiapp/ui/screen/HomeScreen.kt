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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.github.cadnunsdimir.android.duceiapp.R
import io.github.cadnunsdimir.android.duceiapp.models.FakeData
import io.github.cadnunsdimir.android.duceiapp.service.translate
import io.github.cadnunsdimir.android.duceiapp.ui.component.FlavorItem
import io.github.cadnunsdimir.android.duceiapp.ui.component.ScreenTopBar
import io.github.cadnunsdimir.android.duceiapp.ui.theme.DuceiAppTheme

@Composable
fun HomeScreen(
    customerName: String,
    navController: NavController,
    modifier: Modifier = Modifier) {
    val flavors = FakeData.flavors

    Scaffold (
        topBar = {
            ScreenTopBar(
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
                contentDescription = translate("app_logo_description"),
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
            Text(
                text = translate("home_hello|$customerName"),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                translate("home_text")
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

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    DuceiAppTheme {
        HomeScreen("xxx", navController)
    }
}