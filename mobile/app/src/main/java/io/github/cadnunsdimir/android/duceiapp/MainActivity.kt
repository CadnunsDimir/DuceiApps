package io.github.cadnunsdimir.android.duceiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.compose.AsyncImage
import io.github.cadnunsdimir.android.duceiapp.models.FakeData
import io.github.cadnunsdimir.android.duceiapp.models.Flavor
import io.github.cadnunsdimir.android.duceiapp.ui.theme.DuceiAppTheme
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import io.github.cadnunsdimir.android.duceiapp.ui.screen.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DuceiAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Chame o Composable que contém o NavHost
                    AppNavigation()
                }
            }
        }
    }
}

// No seu arquivo de UI (ex: MainActivity.kt)
@Composable
fun AppNavigation() {
    // 1. Cria o NavController. rememberNavController() garante que ele sobreviva a recomposições.
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            HomeScreen(customerName = "Cliente", navController = navController)
        }

        composable(
            route = "new-order/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extrai o argumento da rota para passá-lo à tela de detalhes
            val id = backStackEntry.arguments?.getInt("id")
            NewOrderScreen(id)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String?, navController: NavController) {
    TopAppBar(
        title = {
            if (title != null) {
                Text(title)
            }
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

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

@Composable
fun NewOrderScreen(
    id: Int?, // O nome do sabor virá da rota de navegação
    modifier: Modifier = Modifier
) {
    val flavor = FakeData.flavors
        .stream()
        .filter { flavor -> flavor.id == id }
        .findFirst()
        .orElseThrow { NullPointerException() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Você selecionou o item #${flavor.id}",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = flavor.title, // Mostra o nome do sabor
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DuceiAppTheme {
        AppNavigation()
    }
}