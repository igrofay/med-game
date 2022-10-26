package ru.okei.med.feature.main_screen.model

import ru.okei.med.domain.model.ProfileBody

data class MainState(
    val profileBody: ProfileBody = ProfileBody()
)