package ru.okei.med.data.model

@kotlinx.serialization.Serializable
data class SignUpBody(
    val mail:String,
    val nickname: String,
    val password:String
)