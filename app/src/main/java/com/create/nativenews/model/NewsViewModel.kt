package com.create.nativenews.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.create.nativenews.Constant

/*Library for News Api*/
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class NewsViewModel : ViewModel() {
    /*MutableLiveData is a type of observable data holder.
    Here, it holds a list of Article objects and allows the
    data to be updated and observed*/

    /*We use "_articles" as a private variable
        so that this data is modifiable only within the ViewModel.
        Other classes cannot change it directly, but they can observe it.*/
    private val _articles = MutableLiveData<List<Article>>()


    /*This articles variable is a LiveData that wraps _articles,
    allowing external classes to observe but not modify it.
    Any changes to _articles will automatically notify any observers of articles.*/
    val articles: LiveData<List<Article>> = _articles

    /*This Init Function Use for call the News Api When we call the NewsViewModel*/
    init {
        fetchNewsTopHeadline()

    }

    fun fetchNewsTopHeadline(category: String = "GENERAL") {
        /*Create a NewsApiClient instance*/
        val newsApiClient = NewsApiClient(Constant.apiKey)

        val request = TopHeadlinesRequest.Builder().language("en").category(category).build()
        /*Here object is use for call back function*/
        newsApiClient.getTopHeadlines(request, object : NewsApiClient.ArticlesResponseCallback {
            override fun onSuccess(response: ArticleResponse?) {

                /*When the API call succeeds, onSuccess is triggered.
                If response.articles is not null, we update _articles
                with the fetched list of articles using postValue(it).
                The postValue method is used here because this may be
                called from a background thread.This update will notify
                observers, such as the UI, to refresh with the new data.*/

                response?.articles?.let {
                    _articles.postValue(it)
                }
                /*This line perform the response for every item we get from News Api */
                response?.articles?.forEach {
                    Log.i(
                        "NewsApi Response 1",
                        "Result: ${it.title}"
                    )
                }
            }

            override fun onFailure(throwable: Throwable?) {
                if (throwable != null) {
                    Log.i(
                        "NewsApi Response Failed",
                        throwable.localizedMessage

                    )
                }
            }
        })
    }


    fun searchEveryThingWithQuery(query: String) {
        /*Create a NewsApiClient instance*/
        val newsApiClient = NewsApiClient(Constant.apiKey)

        val request = EverythingRequest.Builder().language("en").q(query).build()
        /*Here object is use for call back function*/
        newsApiClient.getEverything(request, object : NewsApiClient.ArticlesResponseCallback {
            override fun onSuccess(response: ArticleResponse?) {


                response?.articles?.let {
                    _articles.postValue(it)
                }

                response?.articles?.forEach {
                    Log.i(
                        "NewsApi Response 1",
                        "Result: ${it.title}"
                    )
                }
            }

            override fun onFailure(throwable: Throwable?) {
                if (throwable != null) {
                    Log.i(
                        "NewsApi Response Failed",
                        throwable.localizedMessage

                    )
                }
            }
        })
    }
}