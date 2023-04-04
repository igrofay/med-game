package ru.okei.med.feature.rating.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.model.ProfileBody
import ru.okei.med.domain.use_case.profile.GetProfileUseCase
import ru.okei.med.domain.use_case.rating.GetRatingInfoUseCase
import ru.okei.med.feature.base.EventBase
import ru.okei.med.feature.rating.model.RatingEvent
import ru.okei.med.feature.rating.model.RatingState
import javax.inject.Inject

@HiltViewModel
class RatingVM @Inject constructor(
    private val getRatingInfoUseCase: GetRatingInfoUseCase,
    private val getProfileUseCase: GetProfileUseCase,
): ViewModel(), EventBase<RatingEvent> {
    private val _state = mutableStateOf<RatingState>(RatingState.Loading)
    val state: State<RatingState> get() = _state
    private var profile : ProfileBody? = null

    init {
        load()
    }

    private fun load(){
        viewModelScope.launch {
            try {
                if(profile == null){
                    profile = getProfileUseCase.execute().getOrThrow()
                }
                getRatingInfoUseCase.execute().onSuccess {ratingInfo->
                    _state.value = RatingState.RatingList(ratingInfo,profile!!.email)
                }.onFailure(::errorProcessing)
            }catch (e:Throwable){
                errorProcessing(e)
            }
        }
    }

    override fun onEvent(event: RatingEvent) {
        when (event) {
            RatingEvent.RetryRequest -> {
                _state.value = RatingState.Loading
                load()
            }
        }
    }

    private fun errorProcessing(e: Throwable) {
        when(e){
            is Errors.Request.NetworkError->{
                _state.value = RatingState.BadInternetConnection
            }
            else -> Log.e("AuthVM",e.message.toString())
        }
    }
}