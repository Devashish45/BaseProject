package com.example.baseproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.data.models.MovieResponse
import com.example.baseproject.util.DispatcherProvider
import com.example.baseproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: MainRepository,
    val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class MovieEvent {
        class Success(val movieResponse: MovieResponse) : MovieEvent()
        class Failure(val errorMsg: String) : MovieEvent()
        object Loading : MovieEvent()
        object Empty : MovieEvent()
    }

    private val _movieResponse = MutableStateFlow<MovieEvent>(MovieEvent.Empty)
    val movieResponse: StateFlow<MovieEvent> = _movieResponse

    fun getTopRatedMovies(
        apiKey: String,
        lang: String,
        pgNo: String
    ) = viewModelScope.launch(dispatchers.io) {
        _movieResponse.value = MovieEvent.Loading
        delay(2000L)
        when (val movieResponse = repository.getMovies(apiKey, lang, pgNo)) {
            is Resource.Error -> _movieResponse.value = MovieEvent.Failure(movieResponse.message!!)
            is Resource.Success -> _movieResponse.value = MovieEvent.Success(movieResponse.data!!)
        }
    }

}