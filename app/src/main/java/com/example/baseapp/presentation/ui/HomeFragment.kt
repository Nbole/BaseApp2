package com.example.baseapp.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.baseapp.BuildConfig
import com.example.baseapp.R
import com.example.baseapp.presentation.BaseState
import com.example.baseapp.presentation.MovieStates
import com.example.baseapp.presentation.model.PreviewMovieDisplay
import com.example.baseapp.presentation.vm.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val screenState: MovieStates.State =
                    viewModel.uiState.collectAsState().value

                MaterialTheme {
                    Surface {
                        when (screenState) {
                            is MovieStates.State.ShowLatestMovies -> {
                                if (screenState.lastMovies is BaseState.Success) {
                                    MovieList(
                                        input = screenState.lastMovies.data,
                                    ) {
                                        viewModel.setEvent(
                                            MovieStates.Event.OnMovieSelected(it)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    if (it is MovieStates.Effect.GoToDetailMovie) {
                        parentFragmentManager.commit {
                            replace(
                                R.id.id, MovieDetailFragment().apply {
                                    arguments = bundleOf("movie_id" to it.id)
                                }
                            ).addToBackStack(null)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MovieList(
        input: List<PreviewMovieDisplay>,
        action: (Int) -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn {
                items(input) { movie ->
                    MovieCard(
                        movieDisplay = movie
                    ) {
                        action(it)
                    }
                }
            }
        }
    }

    @Composable
    fun MovieCard(
        movieDisplay: PreviewMovieDisplay,
        action: (Int) -> Unit,
    ) {
        androidx.compose.material.Card(elevation = 3.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .padding(12.dp)
                    .background(Color.Yellow)
                    .clickable { action(id) }
            ) {
                LoadImageFromUrl(
                    movieDisplay.posterPath,
                    Modifier.size(150.dp),
                    R.drawable.ic_launcher_background
                )
                Column {
                    Text(text = movieDisplay.title.orEmpty())
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewCard() {
        MaterialTheme {
            MovieCard(
                PreviewMovieDisplay(1, "Una Cagada", "Otra bosta"),
                action = {}
            )
        }
    }
}

@Composable
fun LoadImageFromUrl(
    url: String?,
    modifier: Modifier = Modifier,
    @DrawableRes placeholderResId: Int? = null,
) {
    val painter: ImagePainter = rememberImagePainter(
        data = BuildConfig.BASE_URL_IMAGE + url,
        builder = { placeholderResId?.let { placeholder(it) } }
    )
    Image(
        modifier = modifier,
        painter = painter,
        contentDescription = "",
    )
}