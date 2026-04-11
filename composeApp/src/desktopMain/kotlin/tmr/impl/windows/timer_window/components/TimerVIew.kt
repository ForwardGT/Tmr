package tmr.impl.windows.timer_window.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.core.ui.components.TmrButton
import app.core.ui.components.TmrLoader
import app.core.ui.components.TmrModeSwitchButton
import app.core.ui.components.TmrSpacer
import app.core.ui.components.TmrText
import app.core.ui.resources.TmrColors
import app.core.utils.remote_image.TmrImage
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import tmr.composeapp.generated.resources.Res
import tmr.composeapp.generated.resources.exit
import tmr.composeapp.generated.resources.gear
import tmr.impl.windows.timer_window.TimerStore
import tmr.impl.windows.timer_window.TmrState
import tmr.impl.windows.timer_window.TypeTimer
import kotlin.time.Duration.Companion.seconds

@Composable
fun TimerView(
    onClickCloseApp: () -> Unit,
    onClickOptions: () -> Unit,
) {
    val store = koinViewModel<TimerStore>()
    val state by store.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        HeaderControlButton(
            state = state,
            onClickSwitch = store::switchTimer,
            onClickCloseApp = onClickCloseApp,
            modifier = Modifier.align(Alignment.TopCenter),
            onClickOptions = onClickOptions,
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
    modifier: Modifier = Modifier,
    state: TmrState,
    onClickSwitch: (TypeTimer) -> Unit,
    onClickCloseApp: () -> Unit,
    onClickOptions: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            TmrModeSwitchButton(
                onClick = { onClickSwitch(it) },
                typeTimer = TypeTimer.WorkTimer,
                activeButton = state.typeTimer,
            )
            TmrSpacer(width = 4.dp)
            TmrModeSwitchButton(
                onClick = { onClickSwitch(it) },
                typeTimer = TypeTimer.ShutdownTimer,
                activeButton = state.typeTimer,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TmrButton(
                modifier = Modifier.size(17.dp),
                text = "",
                icon = Res.drawable.gear,
                onClick = { onClickOptions() },
                colorGradientBackground = TmrColors.offButtonGradient,
                colorIcon = TmrColors.colorIconExit,
                isExitButton = true
            )
            TmrSpacer(width = 4.dp)
            TmrButton(
                text = "",
                onClick = onClickCloseApp,
                isExitButton = true,
                icon = Res.drawable.exit,
                colorGradientBackground = TmrColors.offButtonGradient,
                colorIcon = TmrColors.colorIconExit,
            )
        }
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

@Composable
private fun TimerSection(
    store: TimerStore,
    state: TmrState,
) {
    when (state.typeTimer) {
        TypeTimer.WorkTimer -> WorkTimerView(
            modifier = Modifier.size(210.dp),
            store = store,
            state = state,
            timerDesign = state.timerDesign,
        )

        TypeTimer.ShutdownTimer -> ShutdownTimerView(
            modifier = Modifier.size(210.dp),
            store = store,
            state = state,
            timerDesign = state.timerDesign,
        )
    }
}

@Composable
private fun WeatherBlock(
    modifier: Modifier = Modifier,
    state: TmrState,
) {
    if (state.weather.iconUrl.isNotEmpty()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 118.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TmrText(
                modifier = Modifier.padding(start = 14.dp),
                text = "${state.weather.temperature} \u00B0C , ${state.weather.windSpeed} m/s",
                fontSize = 18.sp,
            )

            TmrImage(
                modifier = Modifier.size(46.dp),
                model = state.weather.iconUrl,
            )
        }
    } else {
        TmrLoader(
            modifier = Modifier
                .padding(top = 34.dp)
                .size(32.dp),
        )
    }
}
