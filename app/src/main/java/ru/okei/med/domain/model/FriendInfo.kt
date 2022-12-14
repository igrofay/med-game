package ru.okei.med.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendInfo(
    val email:String = "",
    val name: String = "",
    val icon: String= "",
    val department: Department = Department.Anatomy,
    val placeInRatingDepartment: Int = 0,
    val numberPointsInRatingDepartment: Int = 0,
    val status: FriendStatus
){
    enum class FriendStatus(
        @Transient val statusName: String,
    ){
        Friend("Друзья"),
        ApplicationSent("Отправленные заявки"),
        Subscriber("Полученные заявки"),
        NotFriends("Не друзья"),
    }
}