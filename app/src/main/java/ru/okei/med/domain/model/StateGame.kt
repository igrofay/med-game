package ru.okei.med.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class StateGame(
    val isEndGame:Boolean,
    val rating: List<GamePointsRating>,
    val nameWinner: String?,
){
    @Serializable
    data class GamePointsRating(
        val nickname: String,
        val image:String?,
        val countOfPoints: Int,
        val maxPointsGame:Int,
        val pointGain: Int,
    ){
        val passLastPercentage
            get() = (countOfPoints-pointGain)/maxPointsGame.toFloat()*100
        val passPercentage
            get() = countOfPoints/maxPointsGame.toFloat()*100
        val lastCountOfPoints
            get() = countOfPoints - pointGain;
    }
}