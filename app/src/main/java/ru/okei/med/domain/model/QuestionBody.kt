package ru.okei.med.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ru.okei.med.utils.Timer


@Serializable
data class QuestionBody(
    val type: Type,
    val description:String?,
    val text:String?,
    val image: String?,
    val timeSeconds: Int,
    val rightAnswer: AnswerOption,
    val answers: List<AnswerOption>
){
    @Transient
    val timeFlow = Timer(timeSeconds)


    @Serializable
    data class AnswerOption(
        val type: Type,
        val text:String?,
        val image: String?,
    )
    @Serializable
    enum class Type{
        Image,
        Text,
        Input
    }
}
