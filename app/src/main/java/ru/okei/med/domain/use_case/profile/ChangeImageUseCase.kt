package ru.okei.med.domain.use_case.profile

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import ru.okei.med.domain.repos.ProfileRepository
import ru.okei.med.domain.repos.TokenRepository


class ChangeImageUseCase(
   private val tokenRepository: TokenRepository,
   private val profileRepository: ProfileRepository,
   private val appContext: Application
){
   suspend fun execute(uriImage: Uri) = runCatching {
      val stream = appContext.contentResolver.openInputStream(uriImage)!!
      val token = tokenRepository.access
      profileRepository.sendImageProfile(stream, token)
   }
}