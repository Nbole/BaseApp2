package com.example.baseapp

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.example.baseapp.databinding.ActivityMainBinding
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
        viewModel.latestMovies.observe(
            this@MainActivity,
            {
                when (it) {
                    is Resource.Error<*> -> Log.d("Error", it.message.toString())
                    is Resource.Loading<*> -> Log.d("Loading", it.data.toString())
                    else -> Log.d("Success", it.data.toString())
                }
            }
        )
    }
}
