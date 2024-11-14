package com.create.nativenews.view

import android.icu.text.CaseMap.Title
import android.webkit.WebView
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun NewsArticlePage(url: String, title: String) {
//    Text(title)

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadsImagesAutomatically = true
                settings.mixedContentMode = 0
                loadUrl(url)
            }
        })

//    Text("Hello +$url")
}

