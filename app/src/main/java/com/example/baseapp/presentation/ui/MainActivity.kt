package com.example.baseapp.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Database
import androidx.room.DatabaseView
import com.example.baseapp.databinding.ActivityMainBinding
import com.example.baseapp.domain.model.DResponse
import com.example.baseapp.presentation.vm.MovieViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.sql.DatabaseMetaData

//@AndroidEntryPoint
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
