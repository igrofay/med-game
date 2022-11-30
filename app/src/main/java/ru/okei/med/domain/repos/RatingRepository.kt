package ru.okei.med.domain.repos

import ru.okei.med.domain.model.RatingInfo

interface RatingRepository {

    suspend fun getRatingInfo(token:String):RatingInfo

}