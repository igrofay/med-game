package ru.okei.med.feature.rating.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.use_case.rating.GetRatingInfoUseCase
import ru.okei.med.feature.base.EventBase
import ru.okei.med.feature.rating.model.RatingEvent
import ru.okei.med.feature.rating.model.RatingState
import javax.inject.Inject

@HiltViewModel
class RatingVM @Inject constructor(
    private val getRatingInfoUseCase: GetRatingInfoUseCase
): ViewModel(), EventBase<RatingEvent> {
    private val _state = mutableStateOf<RatingState>(RatingState.Loading)
    val state: State<RatingState> get() = _state

    init {
        viewModelScope.launch {
            getRatingInfoUseCase.execute().onSuccess {ratingInfo->
                _state.value = RatingState.RatingList(ratingInfo)
            }.onFailure(::errorProcessing)
        }
    }

    override fun onEvent(event: RatingEvent) {
        when (event) {
            RatingEvent.RetryRequest -> {
                _state.value = RatingState.Loading
                viewModelScope.launch {
                    getRatingInfoUseCase.execute().onSuccess {ratingInfo->
                        _state.value = RatingState.RatingList(ratingInfo)
                    }.onFailure(::errorProcessing)
                }
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