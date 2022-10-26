package ru.okei.med.domain.repos

interface TokenRepository {
    var refresh: String
    var access: String
}