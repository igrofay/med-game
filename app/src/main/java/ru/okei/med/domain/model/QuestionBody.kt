package ru.okei.med.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ru.okei.med.utils.Timer


@Serializable
data class QuestionBody(
    val type: Type,
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
        val type: Type,
        val text:String? = null,
        val image: String? = null,
    )
    @Serializable
    enum class Type{
        Image,
        Text,
        Input
    }
}
