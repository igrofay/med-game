package ru.okei.med.domain.repos

import ru.okei.med.domain.model.ProfileBody

interface ProfileRepository {

    suspend fun getProfile(token:String) : ProfileBody
}