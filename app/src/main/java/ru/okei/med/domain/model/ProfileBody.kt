package ru.okei.med.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileBody(
    var nickname:String = "",
    var mail:String = "",
    var urlIcon: String = "",
)