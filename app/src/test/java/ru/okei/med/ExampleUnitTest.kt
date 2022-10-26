package ru.okei.med

import org.junit.Test

import org.junit.Assert.*
import ru.okei.med.domain.model.AchievementBody
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
            AchievementBody(
                countPoint = 9999
            ).textProgress
        )
    }
    @Test
    fun checkSpellingName(){
        assertEquals(false,CheckSpelling.check("", CheckSpelling.Type.NameAndSecondeName))
        assertEquals(true,CheckSpelling.check("Гриша Петров", CheckSpelling.Type.NameAndSecondeName))
        assertEquals(false,CheckSpelling.check("Гриша", CheckSpelling.Type.NameAndSecondeName))
    }
    @Test
    fun checkSpellingEmail(){
        println(CheckSpelling.check("1@s.", CheckSpelling.Type.Email))
//        assertEquals(false,CheckSpelling.check("", CheckSpelling.Type.Email))
//        assertEquals(false,CheckSpelling.check("@ad.wd", CheckSpelling.Type.Email))
//        assertEquals(false,CheckSpelling.check("@w.", CheckSpelling.Type.Email))
    }
}