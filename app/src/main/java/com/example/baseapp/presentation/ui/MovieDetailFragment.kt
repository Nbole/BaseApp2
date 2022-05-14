package com.example.baseapp.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baseapp.R
import com.example.baseapp.presentation.MovieDetailStates
import com.example.baseapp.presentation.MovieStates
import com.example.baseapp.presentation.vm.MovieDetailViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {
    private val viewModel: MovieDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val screenState: MovieDetailStates.State =
                    viewModel.uiState.collectAsState().value
                MaterialTheme {
                    Surface {
                        Column {
                            if (screenState is MovieDetailStates.State.ShowGenres) {
                                Log.d("screenState", screenState.movie.toString())
                            }
                            Box(
                                Modifier
                                    .clickable { activity?.onBackPressed() }
                                    .padding(12.dp)) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_arrow_back_24),
                                    contentDescription = null
                                )
                            }
                            Text(
                                "Volver",
                                modifier = Modifier.padding(start = 12.dp)
                            )
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
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.setMovieId(checkNotNull(arguments?.getInt("movie_id")))
            }
        }
    }
}