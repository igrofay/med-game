package ru.okei.med.feature.auth_screen.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.okei.med.R
import ru.okei.med.feature.auth_screen.model.AuthEvent
import ru.okei.med.feature.auth_screen.model.AuthState
import ru.okei.med.feature.base.EventBase
import ru.okei.med.feature.theme.White95
import ru.okei.med.feature.theme.montserratFont
import ru.okei.med.feature.widget.CustomButton
import ru.okei.med.feature.widget.InputField
import ru.okei.med.feature.widget.noRippleClickable

@Composable
fun InputData(
    isVertical: Boolean,
    form: AuthState.FormInput,
    authEventBase: EventBase<AuthEvent>
) {
    var isSignIn by remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current
    val sizeElements = DpSize(250.dp,52.dp)
    val styleText = TextStyle(fontSize = 14.sp, color = White95, fontWeight = FontWeight.W400,)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .offset(y=if(isVertical)(-10).dp else 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if(isVertical) Arrangement.Top else Arrangement.Center
    ) {
        Row(
            modifier = Modifier.width(sizeElements.width),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(White95))
            Text(
                text = if (isSignIn)
                    stringResource(R.string.authorization)
                else stringResource(R.string.registration),
                style = TextStyle(
                    color = White95, fontSize = 20.sp, fontWeight = FontWeight.W600,
                    fontFamily = montserratFont
                ),
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(White95))
        }
        Spacer(modifier = Modifier.height(16.dp))
        InputField(
            value = form.email, onValueChange = {form.email = it},
            label = "E-mail" ,
            size = sizeElements,
            textStyle = styleText,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            isError = form.emailIsError
        )
        Spacer(modifier = Modifier.height(6.dp))
        AnimatedVisibility(
            visible = !isSignIn,
        ) {
            InputField(
                value = form.nickname, onValueChange ={form.nickname = it},
                label = stringResource(R.string.your_nickname),
                size = sizeElements,
                textStyle = styleText,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                modifier = Modifier.padding(bottom = 6.dp),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                isError = form.nicknameIsError
            )
        }
        InputField(
            value = form.password, onValueChange = {form.password = it},
            label = stringResource(R.string.password),
            size = sizeElements,
            textStyle = styleText,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            isError = form.passwordIsError
        )
        Spacer(modifier = Modifier.height(10.dp))
        CustomButton(
            label = if (isSignIn) stringResource(R.string.sign_in)
            else stringResource(R.string.sign_up),
            textSize = 14.sp,
            size = sizeElements
        ){
            if (isSignIn){
                authEventBase.onEvent(AuthEvent.SignIn)
            }else {
                authEventBase.onEvent(AuthEvent.SignUp)
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = if(isSignIn)
                stringResource(R.string.i_dont_have_an_account)
            else stringResource(R.string.i_already_have_an_account),
            color = Color.White.copy(0.7f),
            fontWeight = FontWeight.W600,
            fontSize = 11.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.noRippleClickable {
                isSignIn =!isSignIn
            }
        )
    }

}