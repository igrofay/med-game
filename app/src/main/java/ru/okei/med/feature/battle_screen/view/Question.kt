package ru.okei.med.feature.battle_screen.view

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.skydoves.landscapist.glide.GlideImage
import ru.okei.med.R
import ru.okei.med.domain.model.QuestionBody
import ru.okei.med.feature.battle_screen.model.BattleState
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.widget.AppTopBar
import ru.okei.med.feature.widget.CustomButton
import ru.okei.med.feature.widget.InputField
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
                questionForm.currentQuestion,
                questionForm.question,
                questionForm.userAnswer,
                onAnswer,
            )
        }
        else -> {
            HorizontalQuestion(
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
            AppTopBar()
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp, top = 30.dp)
                    .border(1.dp, White95, shape)
                    .padding(10.dp)
            ){
                Text(
                    text = "$currentQuestion вопрос",
                    color = White95,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400,
                    maxLines = 1
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(18.dp))
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
        Spacer(modifier = Modifier.height(18.dp))
        Spacer(modifier = Modifier.weight(1f))
        RowWithWrap(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 10.dp),
            verticalSpacer = 12.dp,
            horizontalSpacer = 12.dp
        ){
            AnswerBox(
                question = question,
                userAnswer = userAnswer,
                reply = onAnswer
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}


@Composable
fun HorizontalQuestion(
    currentQuestion:Int,
    question: QuestionBody,
    userAnswer: QuestionBody.AnswerOption?,
    onAnswer: (QuestionBody.AnswerOption)->Unit,
) {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ){
            AppTopBar()
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp, top = 30.dp)
                    .border(1.dp, White95, shape)
                    .padding(10.dp)
            ){
                Text(
                    text = "$currentQuestion вопрос",
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
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
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 10.dp),
                verticalSpacer = 12.dp,
                horizontalSpacer = 12.dp
            ){
                AnswerBox(
                    question = question,
                    userAnswer = userAnswer,
                    reply = onAnswer
                )
            }
        }
    }
}


@Composable
fun AnswerBox(
    question: QuestionBody,
    userAnswer: QuestionBody.AnswerOption?,
    reply: (QuestionBody.AnswerOption)->Unit
) {
    if(question.rightAnswer.type == QuestionBody.TypeAnswer.Input){
        var answer by remember {
            mutableStateOf("")
        }
        val color = if(userAnswer == null) White95
        else if (question.rightAnswer.text == answer)
            Color.Green else MaterialTheme.colors.error
        InputField(
            value = answer,
            onValueChange = {answer = it},
            label = stringResource(R.string.enter_answer),
            size = DpSize(300.dp, 52.dp),
            textStyle = TextStyle(
                fontSize = 13.sp,
                color = color,
                fontWeight = FontWeight.W400,),
            read = userAnswer != null
        )
        Box(modifier = Modifier
            .width(300.dp)
            .height(60.dp), contentAlignment = Alignment.Center){
            if(userAnswer==null) CustomButton(
                label = stringResource(R.string.rerly),
                textSize = 13.sp,
                size = DpSize(200.dp, 52.dp)
            ){
                reply(QuestionBody.AnswerOption(QuestionBody.TypeAnswer.Input, text = answer))
            }
        }
    }else{
        val size = if(question.rightAnswer.type == QuestionBody.TypeAnswer.Image)
            DpSize(300.dp, 200.dp)
        else DpSize(170.dp,90.dp)
        for (answerOption in question.answers){
            ItemAnswerOption(
                answerOption = answerOption,
                onClick = { reply(answerOption) },
                dpSize = size,
                rightAnswer = question.rightAnswer,
                userAnswer = userAnswer
            )
        }
    }
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
    if(answerOption.type == QuestionBody.TypeAnswer.Image) {
        ZoomImage(
            image = answerOption.image,
            modifier = Modifier
                .size(dpSize)
                .clip(shape)
                .clickable(onClick = onClick)
                .border(2.dp, color, shape)
                .padding(2.dp)
        )
    }else{
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
                fontSize = 13.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }

}

@Composable
private fun QuestionBox(
    question: QuestionBody
) {
    when(question.type){
        QuestionBody.TypeQuestion.Image -> ZoomImage(
            image = question.image!!,
            modifier = Modifier.size(DpSize(300.dp, 200.dp)),
        )
        QuestionBody.TypeQuestion.Text -> Text(
            text = question.text!!,
            color = White95,
            fontSize = 22.sp,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .heightIn(max = 400.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        )
    }
}

