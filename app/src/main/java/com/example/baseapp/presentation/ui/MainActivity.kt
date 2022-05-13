package com.example.baseapp.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.baseapp.databinding.ActivityMainBinding
import com.example.baseapp.domain.model.DResponse
import com.example.baseapp.presentation.vm.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MovieViewModel by viewModel()

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
