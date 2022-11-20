package ru.okei.med.domain.model

import kotlinx.serialization.Transient

enum class Department (
    @Transient val departmentName: String
){
    Anatomy("Анатомия")
}