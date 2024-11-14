package com.create.nativenews.routing

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
object HomePageScreen

@Serializable
/*Without Argument*/

//object  NewsArticleScreen


/*With Argument*/
data class NewsArticleScreen(
    val url: String,
    val title: String? = null,
    val description: String? = null,
    val content: String? = null,
    val  source: String? = null,
    val image: String? = null,
)
