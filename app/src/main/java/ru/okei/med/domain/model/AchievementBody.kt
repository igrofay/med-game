package ru.okei.med.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AchievementBody(
    val name:String = "",
    val description:String= "",
    val countPoint:Int = 0,
    val maxCountPoint:Int = 0,
    val urlIcon:String=""
){
    val textProgress
        get() = transformationPointsToSimpleView(countPoint)+"/"+transformationPointsToSimpleView(maxCountPoint)
    val progress get() = countPoint/maxCountPoint.toFloat()
    private fun transformationPointsToSimpleView(points:Int):String{
        return when(points){
            in 0..999-> points.toString()
            else->{
                val answer = if (points/100%10 == 0) points/1000 else points/100/10f
                "$answer" +"K"
            }
        }
    }
}
