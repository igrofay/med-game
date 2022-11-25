package ru.okei.med.feature.rating.model

sealed class RatingEvent {
    object RetryRequest : RatingEvent()

}