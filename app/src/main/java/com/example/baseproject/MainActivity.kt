package com.example.baseproject

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.baseproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getTopRatedMovies("f8f3ab3d34d55edc30478ef0a17243e4", "en-US", "1")

        lifecycleScope.launchWhenStarted {
            viewModel.movieResponse.collect { event ->
                when(event) {
                    is MainViewModel.MovieEvent.Success -> {
                        binding.progressBar.isVisible = false
                        binding.tvMain.setTextColor(Color.BLACK)
                        binding.tvMain.text = event.movieResponse.results[0].title
                    }
                    is MainViewModel.MovieEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.tvMain.setTextColor(Color.RED)
                        binding.tvMain.text = event.errorMsg
                    }
                    is MainViewModel.MovieEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }
}