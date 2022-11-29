package ru.okei.med.domain.repos

import ru.okei.med.domain.model.FriendInfo
import ru.okei.med.domain.model.ProfileBody
import java.io.InputStream

interface ProfileRepository {

    suspend fun getProfile(token:String) : ProfileBody

    suspend fun sendImageProfile(imageFile:InputStream,token:String)

}