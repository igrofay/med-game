package ru.okei.med.feature.profile_screen.model

import ru.okei.med.domain.model.ProfileBody

sealed class ProfileState {
    object Loading : ProfileState()
    class Success (
        val profileBody : ProfileBody
    ): ProfileState()
    object BadInternetConnection: ProfileState()
}