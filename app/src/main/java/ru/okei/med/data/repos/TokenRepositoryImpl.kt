package ru.okei.med.data.repos

import android.content.SharedPreferences
import ru.okei.med.domain.repos.TokenRepository
import ru.okei.med.utils.string

class TokenRepositoryImpl(
    sharedPreferences: SharedPreferences
): TokenRepository {
    override var access: String by sharedPreferences.string()
    override var refresh: String by sharedPreferences.string()
}