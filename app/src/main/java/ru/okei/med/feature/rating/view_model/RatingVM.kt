package ru.okei.med.feature.rating.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.okei.med.feature.base.EventBase
import ru.okei.med.feature.rating.model.RatingEvent
import ru.okei.med.feature.rating.model.RatingState
import javax.inject.Inject

@HiltViewModel
class RatingVM @Inject constructor(

): ViewModel(), EventBase<RatingEvent>{
    private val _state = mutableStateOf<RatingState>(RatingState.Loading)
    val state: State<RatingState> get() = _state

    init {

    }

    override fun onEvent(event: RatingEvent) {
        when(event){
            RatingEvent.RetryRequest ->{

            }
        }
    }

}