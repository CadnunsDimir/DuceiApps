package io.github.cadnunsdimir.android.duceiapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.core.os.BuildCompat
import io.github.cadnunsdimir.android.duceiapp.ui.screen.HomeScreen
import io.github.cadnunsdimir.android.duceiapp.ui.screen.NewOrderScreen

class MainActivity : ComponentActivity() {
    companion object{
        var languageId: String = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        i18n(this)
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
    // Dentro de uma Activity ou Context
    fun i18n(context: Context) {
        val configuration = context.resources.configuration
        val localeList = configuration.locales
        val principalLocale = localeList.get(0)
        languageId = principalLocale.language
    }
}

// No seu arquivo de UI (ex: MainActivity.kt)
@Composable
fun AppNavigation() {
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
            NewOrderScreen(id, navController)
        }
    }
}
