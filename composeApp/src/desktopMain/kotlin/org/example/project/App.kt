package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.core.utils.RemoteImage
import org.example.project.core.store.TmrState
import org.example.project.core.store.TmrStore
import org.example.project.core.store.TypeTimer
import org.example.project.core.ui.components.ExitButton
import org.example.project.core.ui.components.TmrModeSwitchButton
import org.example.project.core.ui.resourses.TmrColors
import org.example.project.core.utils.constant.Constants.BASE_URL_IMAGE
import org.example.project.main_window.shutdown_timer.ShutdownTimer
import org.example.project.main_window.work_timer.WorkTimer
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(closeApp: () -> Unit = {}) {
    val store: TmrStore = koinViewModel()
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
        LastUpdateText(
            lastUpdate = state.weather.lastUpdate,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        TimerSection(typeTimer = state.typeTimer)
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
                state = state,
            )
            Spacer(modifier = Modifier.width(4.dp))
            TmrModeSwitchButton(
                onClick = { onSwitch(it) },
                typeTimer = TypeTimer.ShutdownTimer,
                state = state,
            )
        }
        ExitButton(closeApp)
    }
}

@Composable
private fun LastUpdateText(lastUpdate: String, modifier: Modifier) {
    Text(
        modifier = modifier.padding(top = 60.dp),
        text = "Last update $lastUpdate",
        fontSize = 16.sp,
        color = TmrColors.mainText.copy(alpha = .25f),
    )
}

@Composable
private fun TimerSection(typeTimer: TypeTimer) {
    when (typeTimer) {
        TypeTimer.WorkTimer -> WorkTimer(modifier = Modifier.size(250.dp))
        TypeTimer.ShutdownTimer -> ShutdownTimer(modifier = Modifier.size(250.dp), totalTime = 3600L * 1000L)
    }
}

@Composable
private fun WeatherBlock(state: TmrState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 110.dp),
    ) {
        val imageUrl = "$BASE_URL_IMAGE${state.weather.iconCode}@2x.png"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.padding(top = 22.dp),
                text = "${state.weather.temperature}\u00B0C",
                fontSize = 20.sp,
                color = TmrColors.mainText,
            )
            RemoteImage(url = imageUrl, modifier = Modifier.size(60.dp))
            Text(
                modifier = Modifier.padding(top = 22.dp),
                text = "${state.weather.windSpeed} m/s",
                fontSize = 20.sp,
                color = TmrColors.mainText,
            )
        }
    }
}