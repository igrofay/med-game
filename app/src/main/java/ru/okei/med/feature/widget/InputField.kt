package ru.okei.med.feature.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import ru.okei.med.feature.theme.White95

@Composable
fun InputField(
    value :String,
    onValueChange:(String)->Unit,
    label:String,
    size: DpSize,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(10),
    isError: Boolean = false,
    read: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val style = if (isError)
        textStyle.copy(color = colors.error, textAlign = TextAlign.Center)
    else textStyle.copy(textAlign = TextAlign.Center)
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        readOnly = read,
        textStyle = style,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(White95),
    ){innerTextField->
        Box(
            modifier = modifier
                .size(size)
                .border(1.dp,White95.copy(0.25f), shape)
                .background(White95.copy(0.1f))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ){
            if (value.isEmpty())
                Text(
                    text = label,
                    style = if (isError)
                        textStyle.copy(color = colors.error)
                    else
                        textStyle.copy(color = White95.copy(0.7f))
                )
            innerTextField()
        }
    }
}