package ru.okei.med.domain.model

@kotlinx.serialization.Serializable
data class RequestForFight(
    val module: String,
    val friendInfo: FriendInfo,
    val tokenRoom: String,
)