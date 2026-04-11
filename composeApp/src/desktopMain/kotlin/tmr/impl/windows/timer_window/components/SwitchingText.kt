package tmr.impl.windows.timer_window.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.core.ui.components.TmrText
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun SwitchingText(
    lastUpdate: String,
    city: String,
    modifier: Modifier,
) {
    val texts = remember(lastUpdate, city) {
        listOf("Last update $lastUpdate", city)
    }
    var index by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(20.seconds)
            index = (index + 1) % texts.size
        }
    }

    AnimatedContent(
        modifier = modifier.padding(top = 50.dp),
        targetState = texts[index],
        transitionSpec = {
            (fadeIn(animationSpec = tween(400, delayMillis = 50)) + scaleIn(
                initialScale = 0.92f,
                animationSpec = tween(400, delayMillis = 50)
            )).togetherWith(fadeOut(animationSpec = tween(200)))
        },
        label = "switchText"
    ) { text ->
        TmrText(
            text = text,
            textAlign = TextAlign.Center,
            alpha = .25f,
        )
    }
}