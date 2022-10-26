package ru.okei.med.domain.use_case.profile

import android.content.Context
import io.ktor.utils.io.errors.*
import ru.okei.med.R
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.model.ProfileBody
import ru.okei.med.domain.repos.ProfileRepository
import ru.okei.med.domain.repos.TokenRepository
import java.io.IOError

class GetProfileUseCase(
    private val tokenRepository: TokenRepository,
    private val profileRepository: ProfileRepository,
    private val appContext: Context
) {
    suspend fun execute() = runCatching<ProfileBody> {
        try {
            val accessToken = tokenRepository.access
            profileRepository.getProfile(accessToken)
        }catch (e: IOException) {
            throw Errors.Request.NetworkError(
                message = appContext.getString(R.string.network_error)
            )
        }
    }
}