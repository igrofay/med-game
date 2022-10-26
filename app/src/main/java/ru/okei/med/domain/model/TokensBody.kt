package ru.okei.med.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokensBody(
    @SerialName("refresh_token")
    val refresh:String,
    @SerialName("access_token")
    val access:String
)
