package ru.okei.med.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RatingInfo(
    val department: Department = Department.Anatomy,
    val listPlayers: List<Player>,
){
    @Serializable
    data class Player(
        val email:String = "",
        val name: String = "",
        val icon: String= "",
        val placeInRatingDepartment: Int = 0,
        val numberPointsInRatingDepartment: Int = 0,
    )
}
