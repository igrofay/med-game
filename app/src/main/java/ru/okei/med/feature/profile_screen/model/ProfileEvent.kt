package ru.okei.med.feature.profile_screen.model

import android.net.Uri

sealed class ProfileEvent {
    class ChangeImage(val uri:Uri): ProfileEvent()
}