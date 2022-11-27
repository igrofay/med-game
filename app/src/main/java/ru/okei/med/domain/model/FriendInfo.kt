package ru.okei.med.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FriendInfo(
    val email:String = "",
    val name: String = "",
    val icon: String= "",
    val department: Department = Department.Anatomy,
    val placeInRatingDepartment: Int = 0,
    val numberPointsInRatingDepartment: Int = 0,
    val state: FriendState
){
    enum class FriendState{
        Friend, // Друг
        ApplicationSent, // человек которому отправили заявку в друзья
        Subscriber, // человек который от правил заявку в друзья
        NotFriends, // Обычный чел
    }
}