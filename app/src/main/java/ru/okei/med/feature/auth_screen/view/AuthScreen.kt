package ru.okei.med.feature.auth_screen.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import ru.okei.med.feature.auth_screen.model.AuthEvent
import ru.okei.med.feature.auth_screen.model.AuthState
import ru.okei.med.feature.auth_screen.view_model.AuthVM
import ru.okei.med.feature.widget.AppTopBar

@Composable
fun AuthScreen(
    authVM: AuthVM = hiltViewModel(),
    loggedIn: ()->Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val state by remember { authVM.state }
    val errorMessage by authVM.error
    LaunchedEffect(errorMessage){
        errorMessage?.message?.let {
            scaffoldState.snackbarHostState.showSnackbar(it)
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color.Transparent
    ) {
        when(state){
            is AuthState.FormInput ->{
                val form = state as AuthState.FormInput
                when(LocalConfiguration.current.orientation){
                    Configuration.ORIENTATION_PORTRAIT -> {
                        Column{
                            AppTopBar()
                            AuthLogo(isVertical = true)
                            InputData(isVertical = true, form = form, authEventBase = authVM)
                        }
                    }
                    else -> {
                        Row{
                            AuthLogo(isVertical = false)
                            InputData(isVertical = false, form = form, authEventBase = authVM)
                        }
                    }
                }
            }
            AuthState.Success ->{
                LaunchedEffect(true){
                    loggedIn()
                }
            }
        }
    }
}