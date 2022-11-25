package ru.okei.med.feature.rating.model

sealed class RatingState {
    object Loading : RatingState()
    object BadInternetConnection: RatingState()
}