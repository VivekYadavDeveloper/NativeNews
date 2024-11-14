package com.create.nativenews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.create.nativenews.model.NewsViewModel
import com.create.nativenews.routing.HomePageScreen
import com.create.nativenews.routing.NewsArticleScreen
import com.create.nativenews.ui.theme.NativeNewsTheme
import com.create.nativenews.view.HomePage
import com.create.nativenews.view.NewsArticlePage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        setContent {

            val navController = rememberNavController()

            NativeNewsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "Native News",
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                            color = Color.Red,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )

                        NavHost(
                            navController = navController,
                            startDestination = HomePageScreen
                        ) {
                            composable<HomePageScreen> {
                                HomePage(newsViewModel, navController)
                            }
                            composable<NewsArticleScreen> {
                                /*To get the arguments from the route screen*/
                                val args = it.toRoute<NewsArticleScreen>()
                                NewsArticlePage(args.url, args.title ?: "")
                            }

                        }
                    }
                }
            }
        }
    }
}




