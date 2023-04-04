package ru.okei.med.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ProfileBody(
    var nickname:String = "",
    var email:String = "",
    var urlIcon: String = "",
    var department: Department = Department.Anatomy,
)