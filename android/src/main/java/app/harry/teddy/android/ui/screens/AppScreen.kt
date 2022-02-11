@file:OptIn(ExperimentalAnimationApi::class)

package app.harry.teddy.android.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import app.harry.teddy.android.ui.screens.details.DetailScreen
import app.harry.teddy.android.ui.screens.home.HomeScreen
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@Composable
fun AppScreen() {

    val navController = rememberAnimatedNavController()

    Scaffold {

        AnimatedNavHost(
            navController,
            "home"
        ) {

            composable("home") {
                HomeScreen(navController)
            }

            composable("detail/{id}") {
                val id = it.arguments?.getString("id")
                DetailScreen(navController, id!!)
            }

        }

    }

}