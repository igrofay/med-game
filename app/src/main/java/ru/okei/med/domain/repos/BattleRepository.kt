package ru.okei.med.domain.repos

interface BattleRepository {
    suspend fun getModules(): List<String>
}