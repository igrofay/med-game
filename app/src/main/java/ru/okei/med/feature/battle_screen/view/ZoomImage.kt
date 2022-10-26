package ru.okei.med.feature.battle_screen.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.graphics.drawable.toBitmap
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import ru.okei.med.feature.theme.White95

@Composable
fun<T> ZoomImage(
    image: T,
    dpSize: DpSize,

) {
    var zIndex by remember {
        mutableStateOf(0f)
    }
    var scale by remember { mutableStateOf(1f) }
    var rotationState by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        zIndex = Float.MAX_VALUE
        scale *= zoomChange
        rotationState += rotationChange
        offset += offsetChange
    }
    GlideImage(
        imageModel = image,
        modifier = Modifier
            .zIndex(zIndex)
            .height(dpSize.height)
            .fillMaxWidth(.9f)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitUpTap()
                        scale = 1f
                        rotationState = 0f
                        offset = Offset.Zero
                        zIndex = 0f
                    }
                }
            }
            .transformable(state = state)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotationState,
                translationX = offset.x,
                translationY = offset.y
            ),
        loading = {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = White95
            )
        },
        imageOptions = ImageOptions(
            contentScale = ContentScale.FillHeight
        )
    )
}

private suspend fun AwaitPointerEventScope.awaitUpTap(){
    do {
        val event: PointerEvent = awaitPointerEvent()
    } while (event.changes.any { it.pressed })
}