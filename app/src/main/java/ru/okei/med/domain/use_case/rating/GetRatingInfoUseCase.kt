package ru.okei.med.domain.use_case.rating

import android.content.Context
import io.ktor.utils.io.errors.*
import ru.okei.med.R
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.model.RatingInfo
import ru.okei.med.domain.repos.RatingRepository
import ru.okei.med.domain.repos.TokenRepository

class GetRatingInfoUseCase(
    private val tokenRepository: TokenRepository,
    private val ratingRepository: RatingRepository,
    private val appContext:Context
) {
    suspend fun execute() = runCatching<RatingInfo>{
        try {
            val token = tokenRepository.access
            ratingRepository.getRatingInfo(token)
        }catch (e: IOException) {
            throw Errors.Request.NetworkError(
                message = appContext.getString(R.string.network_error)
            )
        }
    }
}