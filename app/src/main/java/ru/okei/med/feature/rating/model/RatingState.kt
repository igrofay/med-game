package ru.okei.med.feature.rating.model

import ru.okei.med.domain.model.RatingInfo

sealed class RatingState {
    object Loading : RatingState()
    object BadInternetConnection: RatingState()
    class RatingList(val ratingInfo: RatingInfo, val myEmail:String,):RatingState()
}