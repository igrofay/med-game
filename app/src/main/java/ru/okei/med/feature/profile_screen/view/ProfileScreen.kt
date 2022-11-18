package ru.okei.med.feature.profile_screen.view


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import ru.okei.med.feature.profile_screen.model.ProfileState
import ru.okei.med.feature.profile_screen.view_model.ProfileVM
import ru.okei.med.feature.widget.LoadingIndicator

@Composable
fun ProfileScreen(
    profileVM : ProfileVM = hiltViewModel()
) {
    val state by remember { profileVM.state }
    when(state){
        ProfileState.Loading -> LoadingIndicator()
        is ProfileState.Success -> ContentProfile(profileBody = (state as ProfileState.Success).profileBody){

        }
        ProfileState.BadInternetConnection -> {}
    }
}