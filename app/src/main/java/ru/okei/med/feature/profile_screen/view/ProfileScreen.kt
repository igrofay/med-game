package ru.okei.med.feature.profile_screen.view


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import ru.okei.med.feature.profile_screen.model.ProfileEvent
import ru.okei.med.feature.profile_screen.model.ProfileState
import ru.okei.med.feature.profile_screen.view_model.ProfileVM
import ru.okei.med.feature.widget.LoadingIndicator
import ru.okei.med.feature.widget.RetryRequest

@Composable
fun ProfileScreen(
    profileVM : ProfileVM = hiltViewModel()
) {
    val state : ProfileState by remember { profileVM.state }
    when(state){
        ProfileState.Loading -> LoadingIndicator()
        is ProfileState.Success -> ContentProfile(
            profileBody = (state as ProfileState.Success).profileBody,
            onChange = profileVM::onEvent
        )
        ProfileState.BadInternetConnection -> RetryRequest {
            profileVM.onEvent(ProfileEvent.RetryRequest)
        }
    }
}