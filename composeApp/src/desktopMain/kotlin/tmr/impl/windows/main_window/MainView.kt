package tmr.impl.windows.main_window

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.core.ui.components.ExitButton
import app.core.ui.components.TmrLoader
import app.core.ui.components.TmrModeSwitchButton
import app.core.ui.components.TmrSpacer
import app.core.ui.resourses.TmrColors
import app.core.utils.remote_image.TmrImage
import kotlinx.coroutines.delay
import tmr.impl.windows.main_window.shutdown_timer.ShutdownTimer
import tmr.impl.windows.main_window.work_timer.WorkTimer
import kotlin.time.Duration.Companion.seconds

@Composable
fun MainView(
    closeApp: () -> Unit = {},
    store: MainStore,
) {
    val state by store.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        HeaderControlButton(
            state = state,
            onSwitch = store::switchTimer,
            closeApp = closeApp,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        WeatherBlock(
            state = state,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        SwitchingText(
            lastUpdate = state.weather.lastUpdate,
            city = state.userLocation.city,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        TimerSection(
            store = store,
            state = state,
        )
    }
}

@Composable
private fun HeaderControlButton(
    state: TmrState,
    onSwitch: (TypeTimer) -> Unit,
    closeApp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            TmrModeSwitchButton(
                onClick = { onSwitch(it) },
                typeTimer = TypeTimer.WorkTimer,
                activeButton = state.typeTimer,
            )
            TmrSpacer(width = 4.dp)
            TmrModeSwitchButton(
                onClick = { onSwitch(it) },
                typeTimer = TypeTimer.ShutdownTimer,
                activeButton = state.typeTimer,
            )
        }
        ExitButton(closeApp)
    }
}

@Composable
private fun SwitchingText(
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
        modifier = modifier.padding(top = 60.dp),
        targetState = texts[index],
        transitionSpec = {
            (fadeIn(animationSpec = tween(400, delayMillis = 50)) + scaleIn(
                initialScale = 0.92f,
                animationSpec = tween(400, delayMillis = 50)
            )).togetherWith(fadeOut(animationSpec = tween(200)))
        },
        label = "switchText"
    ) { text ->

        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = TmrColors.mainText.copy(alpha = .25f),
        )
    }
}

@Composable
private fun TimerSection(
    store: MainStore,
    state: TmrState,
) {
    when (state.typeTimer) {
        TypeTimer.WorkTimer -> WorkTimer(
            modifier = Modifier.size(238.dp),
            store = store,
            state = state,
        )

        TypeTimer.ShutdownTimer -> ShutdownTimer(
            modifier = Modifier.size(238.dp),
        )
    }
}

@Composable
private fun WeatherBlock(
    modifier: Modifier = Modifier,
    state: TmrState,
) {
    if (state.weather.iconUrl.isNotEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 110.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier.padding(top = 22.dp),
                    text = "${state.weather.temperature} \u00B0C",
                    fontSize = 20.sp,
                    color = TmrColors.mainText,
                )
                TmrImage(
                    modifier = Modifier.size(60.dp),
                    model = state.weather.iconUrl,
                )
                Text(
                    modifier = Modifier.padding(top = 22.dp),
                    text = "${state.weather.windSpeed} m/s",
                    fontSize = 20.sp,
                    color = TmrColors.mainText,
                )
            }
        }
    } else {
        TmrLoader(
            modifier = Modifier
                .padding(top = 30.dp)
                .size(36.dp),
        )
    }
}
