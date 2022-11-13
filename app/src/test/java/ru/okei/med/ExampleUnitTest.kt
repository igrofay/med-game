package ru.okei.med

import org.junit.Test

import org.junit.Assert.*
import ru.okei.med.domain.model.AchievementBody
import ru.okei.med.domain.model.TokensBody
import ru.okei.med.domain.repos.AuthRepository
import ru.okei.med.domain.repos.TokenRepository
import ru.okei.med.utils.CheckSpelling

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun achievementBodyCheckVariables(){
        println(
            AchievementBody(countPoint = 9999).textProgress
        )
    }
    @Test
    fun checkSpellingName(){
        assertEquals(false,CheckSpelling.check("", CheckSpelling.Type.NameAndSecondName))
        assertEquals(true,CheckSpelling.check("Гриша Петров", CheckSpelling.Type.NameAndSecondName))
        assertEquals(false,CheckSpelling.check("Гриша", CheckSpelling.Type.NameAndSecondName))
        assertEquals(false,CheckSpelling.check("Гриша  ", CheckSpelling.Type.NameAndSecondName))
    }
    private val tokenRepository = object : TokenRepository{
        override var refresh: String
            get() = ""
            set(value) {}
        override var access: String
            get() = ""
            set(value) {}
    }
    private val authRepository = object :AuthRepository{
        override suspend fun signIn(email: String, password: String): TokensBody {
            TODO("Not yet implemented")
        }
        override suspend fun signUp(
            email: String,
            nickname: String,
            password: String
        ): TokensBody {
            return TokensBody("","")
        }
        override suspend fun restoreSession(refreshToken: String): TokensBody {
            TODO("Not yet implemented")
        }

    }
    @Test
    suspend fun checkSpellingEmail(){
//        val context =InstrumentationRegistry.getInstrumentation().context
//        val use = SignUpUseCase(tokenRepository, authRepository, context)
//        val answer = use.execute("Oleg@ad.dw","Oleg Nikolash","1234567").getOrThrow()
//        println(answer.toString())
//        assertEquals(Unit, answer)
    }
}