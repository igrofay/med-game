package ru.okei.med.data.model

import kotlinx.serialization.Serializable
import ru.okei.med.domain.model.TypeBattle

@Serializable
data class SettingRoomBody(
    val nameModule:String?,
    val nameDepartment:String,
    val type: TypeBattle
)