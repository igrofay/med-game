package ru.okei.med.data.model

@kotlinx.serialization.Serializable
data class SignInBody(
    val mail:String,
    val password:String
)