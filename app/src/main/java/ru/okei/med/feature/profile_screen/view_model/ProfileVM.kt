package ru.okei.med.feature.profile_screen.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.okei.med.domain.model.AchievementBody
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.model.ProfileBody
import ru.okei.med.domain.repos.ProfileRepository
import ru.okei.med.domain.repos.TokenRepository
import ru.okei.med.domain.use_case.profile.GetProfileUseCase
import ru.okei.med.feature.profile_screen.model.ProfileState
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
): ViewModel() {
    private var _state = mutableStateOf<ProfileState>(ProfileState.Loading)
    val state:State<ProfileState> get() = _state

    init {
        viewModelScope.launch(Dispatchers.IO){
            getProfileUseCase.execute().onSuccess {
                _state.value = ProfileState.Success(it)
            }.onFailure(::errorProcessing)
        }
    }


    private fun errorProcessing(e:Throwable){
        when(e){
            is Errors.Request.NetworkError->{
                _state.value = ProfileState.BadInternetConnection
            }
            else-> Log.e("ProfileVM",e.message.toString())
        }
    }
}
