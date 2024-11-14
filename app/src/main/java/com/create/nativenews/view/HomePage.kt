package com.create.nativenews.view


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.create.nativenews.model.NewsViewModel
import com.create.nativenews.routing.NewsArticleScreen
import com.kwabenaberko.newsapilib.models.Article


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun DemoView(modifier: Modifier = Modifier) {
//    HomePage(newsViewModel = NewsViewModel())
    CategoryBar(newsViewModel = NewsViewModel())

}

@Composable
fun HomePage(newsViewModel: NewsViewModel, navController: NavHostController) {

    val articles by newsViewModel.articles.observeAsState(emptyList())

    val filteredArticles = articles.filter { article ->
        article.title != "[Removed]" && article.source.name != "[Removed]"
        // Add more conditions if needed, depending on other fields to check
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CategoryBar(newsViewModel)

        LazyColumn(modifier = Modifier.fillMaxSize()) {


            items(filteredArticles) { article ->
                ArticleItems(article, navController)
            }
        }

    }
}


/*Article List*/
@Composable
fun ArticleItems(article: Article, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate(NewsArticleScreen(article.url, article.title))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = article.urlToImage
                    ?: "data:image/png;base64,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",
                contentDescription = "Article Image",
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Text(
                    textAlign = TextAlign.Start,
                    text = article.title,
                    fontSize = 15.sp,
                    maxLines = 2,
                    color = Color.Black
                )
                Text(
                    text = article.source.name, fontSize = 10.sp, maxLines = 1, color = Color.Gray
                )
            }

        }
    }
}


@Composable
fun CategoryBar(newsViewModel: NewsViewModel) {

    var searchQuery by remember {
        mutableStateOf("")
    }

    var isSearchExpanded by remember {
        mutableStateOf(false)
    }
    val categories = listOf(
        "GENERAL",
        "BUSINESS",
        "ENTERTAINMENT",
        "HEALTH",
        "SCIENCE",
        "SPORTS",
        "TECHNOLOGY",
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        if (isSearchExpanded) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(5.dp)
                    .border(1.dp, color = Color.Gray, CircleShape)
                    .clip(CircleShape),
                value = searchQuery,
                onValueChange = { searchQuery = it }, trailingIcon = {
                    IconButton(onClick = {
                        isSearchExpanded = false
                        if (searchQuery.isNotEmpty()) {
                            newsViewModel.searchEveryThingWithQuery(searchQuery)
                        }
                    }) {

                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }

                }

            )
        } else {
            IconButton(onClick = {
                isSearchExpanded = true
            }) {

                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
        categories.forEach { category ->
            Button(onClick = {
                newsViewModel.fetchNewsTopHeadline(category)

            }, modifier = Modifier.padding(5.dp)) {
                Text(text = category)
            }
        }
    }
}
