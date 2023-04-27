package ru.okei.med.feature.main_screen.model

import ru.okei.med.domain.model.ProfileBody
import ru.okei.med.domain.model.RequestForFight

data class MainState(
    val profileBody: ProfileBody = ProfileBody(),
    val requestForFight: RequestForFight? = null,
)