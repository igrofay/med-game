package ru.okei.med.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ru.okei.med.utils.Timer


@Serializable
data class QuestionBody(
    val type: TypeQuestion,
    val description:String? = null,
    val text:String? = null,
    val image: String? = null,
    val timeSeconds: Int,
    val rightAnswer: AnswerOption,
    val answers: List<AnswerOption>
){
    @Transient
    val timeFlow = Timer(timeSeconds)


    @Serializable
    data class AnswerOption(
        val type: TypeAnswer,
        val text:String? = null,
        val image: String? = null,
    )
    @Serializable
    enum class TypeQuestion{
        Image,
        Text
    }
    @Serializable
    enum class TypeAnswer{
        Image,
        Text,
        Input
    }
}
