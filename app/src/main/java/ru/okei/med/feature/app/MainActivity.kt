package ru.okei.med.feature.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import dagger.hilt.android.AndroidEntryPoint
import ru.okei.med.R
import ru.okei.med.domain.model.ProfileBody
import ru.okei.med.domain.model.QuestionBody
import ru.okei.med.feature.battle_screen.model.BattleState
import ru.okei.med.feature.battle_screen.view.Question
import ru.okei.med.feature.nav_app.view.InitialNavigationApp
import ru.okei.med.feature.profile_screen.view.ContentProfile
import ru.okei.med.feature.theme.MedTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            MedTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ){
                    Image(
                        painter = painterResource(R.drawable.background),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        colorFilter = ColorFilter.tint(
                            Color(0x99161616),
                            BlendMode.Overlay
                        ),
                        contentScale = ContentScale.Crop
                    )
                    InitialNavigationApp()
                }
            }
        }
    }
}
