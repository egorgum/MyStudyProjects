package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgs
import androidx.navigation.navArgument
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.myapplication.first.ui.*
import com.example.myapplication.theme.MyApplicationTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ListViewModel>{ Factory() }
    private val pageData by lazy { RickAndMortyPagingSource.pager(viewModel) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }

    @Composable
    fun Greeting() {
        val navController = rememberNavController()
        val items: LazyPagingItems<Characters> = pageData.flow.collectAsLazyPagingItems()
        NavHost(navController = navController, startDestination = Routes.Main.route) {

            // First route : Home
            composable(Routes.Main.route) {

                // Lay down the Home Composable
                // and pass the navController
                ListViewReal(items = items, navController = navController)
            }
            composable(Routes.Person.route + "/{name}" + "/{status}" + "/{location}" + "/{species}" + "/{gender}" + "/{origin}" + "/{image}") {
                val  image = it.arguments?.getString("image")
                val  name = it.arguments?.getString("name")
                val  status = it.arguments?.getString("status")
                val  location = it.arguments?.getString("location")
                val  species = it.arguments?.getString("species")
                val  gender = it.arguments?.getString("gender")
                val  origin = it.arguments?.getString("origin")

                // Profile Screen
                PersonView( name = name, status = status, location = location, species = species, gender = gender, origin = origin, image = image)
            }
        }
    }
}
