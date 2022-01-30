package com.example.moviesdemo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesdemo.models.SearchedResultModel
import com.example.moviesdemo.repository.MainRepository
import com.example.tentwentyassignment.models.UpcomingMoviesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class SearchedFragmentViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val mSearchedMoviesResponse = MutableLiveData<SearchedResultModel>()
    var loadingError = MutableLiveData<String?>()
    var loading = MutableLiveData<Boolean>()
    var job: Job? = null
    var exceptionalHandling = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exceptional Error: ${throwable.localizedMessage}")
    }

    private fun onError(message: String) {
         loadingError.value = message
        loading.value = false
    }

    fun callSearchedMovies(queryKeyword : String) {
        job = CoroutineScope(Dispatchers.IO + exceptionalHandling).launch {
            val response = mainRepository.getSearchedMovies(queryKeyword)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        mSearchedMoviesResponse.value = response.body()
                        loadingError.value = null
                        loading.value = false
                    } else {
                        onError("UserLoadError : ${response.message()} ")
                        loading.value = false
                    }
                } catch (e: SocketTimeoutException) {
                    onError("UserLoadError : timeout ")
                    loading.value = false
                }
            }
        }
    }

}