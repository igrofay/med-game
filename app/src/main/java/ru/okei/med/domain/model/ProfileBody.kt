package ru.okei.med.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ProfileBody(
    var nickname:String = "",
    var mail:String = "",
    var urlIcon: String = "",
    @Transient
    var department:Department = Department.Anatomy,
)