package ru.okei.med.feature.rating.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import ru.okei.med.feature.rating.model.RatingEvent
import ru.okei.med.feature.rating.model.RatingState
import ru.okei.med.feature.rating.view_model.RatingVM
import ru.okei.med.feature.widget.LoadingIndicator
import ru.okei.med.feature.widget.RetryRequest

@Composable
fun RatingScreen(
    ratingVM: RatingVM = hiltViewModel()
) {
    val state by remember { ratingVM.state }
    when(state){
        RatingState.BadInternetConnection -> RetryRequest {
            ratingVM.onEvent(RatingEvent.RetryRequest)
        }
        RatingState.Loading -> LoadingIndicator()
    }
}