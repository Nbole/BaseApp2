package com.example.baseapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.example.baseapp.databinding.ActivityMainBinding
import com.example.baseapp.db.MovieResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch(Dispatchers.IO) {


            /*query<PreviewHomeSupplierResponse>(
                baseUrl = "https://bff-qa.wabi2b.com/graphql",
                queryName = "previewHomeSuppliers",
                queryBody = " {...on PreviewHomeSupplierResponse {suppliers {id name avatar}}}",
                variables = CoordinatesInput(
                    -34.6287294,
                    -58.41489699999999
                )
            )*/


            get<MealResponse>(
                "https://www.themealdb.com/api/json/v1/1/categories.php",
                null,
                null
            )
            get<MovieResponse>(
                "https://api.themoviedb.org/3/movie/now_playing",
                "api_key",
                "5e30e8afd06d2b8b9aae8eb164c85a29"
            )
        }


        /*viewModel.latestMovies.observe(
            this@MainActivity,
            {
                when (it) {
                    is Resource.Error<*> -> Log.d("Error", it.message.toString())
                    is Resource.Loading<*> -> Log.d("Loading", it.data.toString())
                    else -> Log.d("Success", it.data.toString())
                }
            }
        )*/
    }
}
