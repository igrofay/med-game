package ru.okei.med.feature.battle_screen.view

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import ru.okei.med.domain.model.QuestionBody
import ru.okei.med.domain.model.QuestionBody.Type.*
import ru.okei.med.feature.battle_screen.model.BattleState
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.widget.AppTopBar
import ru.okei.med.feature.widget.RowWithWrap

private val shape = RoundedCornerShape(10.dp)

@Composable
fun Question(
    questionForm: BattleState.QuestionForm,
    onAnswer: (QuestionBody.AnswerOption) -> Unit
) {
    when(LocalConfiguration.current.orientation){
        Configuration.ORIENTATION_PORTRAIT -> {
            VerticalQuestion(
                questionForm.numberOfQuestions,
                questionForm.currentQuestion,
                questionForm.question,
                questionForm.userAnswer,
                onAnswer,
            )
        }
        else -> {
            HorizontalQuestion(
                questionForm.numberOfQuestions,
                questionForm.currentQuestion,
                questionForm.question,
                questionForm.userAnswer,
                onAnswer,
            )
        }
    }
}


@Composable
private fun VerticalQuestion(
    numberOfQuestions:Int,
    currentQuestion:Int,
    question: QuestionBody,
    userAnswer: QuestionBody.AnswerOption?,
    onAnswer: (QuestionBody.AnswerOption)->Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ){
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 15.dp, top = 30.dp)
                    .border(1.dp, White95, shape)
                    .padding(10.dp)
            ){
                Text(
                    text = "$numberOfQuestions вопросов",
                    color = White95,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400,
                    maxLines = 1
                )
            }
            AppTopBar()
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp, top = 30.dp)
                    .border(1.dp, White95, shape)
                    .padding(10.dp)
            ){
                Text(
                    text = "${numberOfQuestions - currentQuestion} осталось",
                    color = White95,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400,
                    maxLines = 1
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        QuestionBox(question)
        Spacer(modifier = Modifier.height(10.dp))
        question.description?.let {description->
            Text(
                text = description,
                color = White95,
                fontSize = 15.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.fillMaxWidth(.9f),
                textAlign = TextAlign.Center
            )
        }
        val time by question.timeFlow.rememberState()
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = time,
            color = White95,
            fontSize = 13.sp,
            fontWeight = FontWeight.W300,
        )
        Spacer(modifier = Modifier.weight(1f))
        val size = DpSize(150.dp,75.dp)
        RowWithWrap(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            verticalSpacer = 12.dp,
            horizontalSpacer = 12.dp
        ){
            for (answerOption in question.answers){
                ItemAnswerOption(
                    answerOption = answerOption,
                    onClick = { onAnswer(answerOption) },
                    dpSize = size,
                    rightAnswer = question.rightAnswer,
                    userAnswer = userAnswer
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}


@Composable
fun HorizontalQuestion(
    numberOfQuestions:Int,
    currentQuestion:Int,
    question: QuestionBody,
    userAnswer: QuestionBody.AnswerOption?,
    onAnswer: (QuestionBody.AnswerOption)->Unit,
) {
    val size = DpSize(150.dp,75.dp)
    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ){
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 15.dp, top = 30.dp)
                    .border(1.dp, White95, shape)
                    .padding(10.dp)
            ){
                Text(
                    text = "$numberOfQuestions вопросов",
                    color = White95,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400,
                    maxLines = 1
                )
            }
            AppTopBar()
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp, top = 30.dp)
                    .border(1.dp, White95, shape)
                    .padding(10.dp)
            ){
                Text(
                    text = "${numberOfQuestions - currentQuestion} осталось",
                    color = White95,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400,
                    maxLines = 1
                )
            }
        }
        Row{
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                QuestionBox(question)
                Spacer(modifier = Modifier.height(10.dp))
                question.description?.let {description->
                    Text(
                        text = description,
                        color = White95,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.fillMaxWidth(.9f),
                        textAlign = TextAlign.Center
                    )
                }
                val time by question.timeFlow.rememberState()
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = time,
                    color = White95,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W300,
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            RowWithWrap(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .zIndex(-1f)
                    .padding(horizontal = 10.dp),
                verticalSpacer = 12.dp,
                horizontalSpacer = 12.dp
            ){
                for (answerOption in question.answers){
                    ItemAnswerOption(
                        answerOption = answerOption,
                        onClick = { onAnswer(answerOption) },
                        dpSize = size,
                        rightAnswer = question.rightAnswer,
                        userAnswer = userAnswer
                    )
                }
            }
        }
    }
}


@Composable
fun ReplyAnswerBox() {

}


@Composable
private fun ItemAnswerOption(
    answerOption: QuestionBody.AnswerOption,
    userAnswer: QuestionBody.AnswerOption?,
    onClick:()->Unit,
    dpSize: DpSize,
    rightAnswer: QuestionBody.AnswerOption,
    shape: RoundedCornerShape = RoundedCornerShape(15.dp)
) {
    val color = if (rightAnswer == answerOption && userAnswer == answerOption)
        Color.Green.copy(0.5f)
    else if (rightAnswer != answerOption && userAnswer == answerOption)
        Color.Red.copy(0.5f)
    else Color.Gray.copy(0.5f)
    Box(
        modifier = Modifier
            .size(dpSize)
            .clip(shape)
            .clickable(onClick = onClick)
            .border(1.dp, White95, shape)
            .background(color),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = answerOption.text!!,
            color = White95,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400
        )
    }
}

@Composable
private fun QuestionBox(
    question: QuestionBody
) {
    when(question.type){
        Image -> ZoomImage(
            image = question.image!!,
            dpSize = DpSize(300.dp, 200.dp)
        )
        Text -> Text(
            text = question.text!!,
            color = White95,
            fontSize = 24.sp,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        Input -> {}
    }
}

