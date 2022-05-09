package com.example.baseapp

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.example.baseapp.databinding.ActivityMainBinding
import com.example.baseapp.domain.DResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            viewModel.latestMovies.observe(this) {
                when (it) {
                    is DResponse.Error<*> -> Log.d("BaseApp", "Error ${it.message}")
                    is DResponse.Loading<*> -> Log.d("BaseApp", "Loading ${it.data}")
                    else -> Log.d("BaseApp", "Succes ${it.data}")
                }
            }
        }
}
