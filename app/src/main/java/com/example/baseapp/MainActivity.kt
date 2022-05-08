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

            /*query<PreviewHomeSupplierResponse>(
                baseUrl = "https://bff-qa.wabi2b.com/graphql",
                queryName = "previewHomeSuppliers",
                queryBody = " {...on PreviewHomeSupplierResponse {suppliers {id name avatar}}}",
                variables = CoordinatesInput(
                    -34.6287294,
                    -58.41489699999999
                )
            )*/

            /*    get<MealResponse>(
                "https://www.themealdb.com/api/json/v1/1/categories.php",
                null,
                null
            )
            get<MovieResponse>(
                "https://api.themoviedb.org/3/movie/now_playing",
                "api_key",
                "5e30e8afd06d2b8b9aae8eb164c85a29"
            )
        }*/
            viewModel.latestPagedMovies.observe(this) {
                when (it) {
                    is Resource.Error<*> -> Log.d("Main", "Error ${it.message}")
                    is Resource.Loading<*> -> Log.d("Loading", "Loading ${it.data}")
                    else -> Log.d("Success", "Succes ${it.data}")
                }
            }
        }
}
