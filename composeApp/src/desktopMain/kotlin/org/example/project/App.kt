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
import org.example.project.api.RemoteImage
import org.example.project.core.store.TmrStore
import org.example.project.core.ui.components.ExitButton
import org.example.project.core.ui.components.SwitchTmr
import org.example.project.core.ui.resourses.TmrColors
import org.example.project.core.utils.constant.Constants.BASE_URL_IMAGE
import org.example.project.main_window.shutdown_timer.ShutdownTimer
import org.example.project.main_window.work_timer.WorkTimer
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun App(closeApp: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val store: TmrStore = koinViewModel()
        val state by store.state.collectAsState()

        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SwitchTmr(state = state.isTimer) { store.switchTimer(it) }
            ExitButton(closeApp)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Spacer(modifier = Modifier.padding(top = 110.dp))
            val url = "$BASE_URL_IMAGE${state.weather.iconCode}@2x.png"
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

                RemoteImage(url = url, modifier = Modifier.size(60.dp))

                Text(
                    modifier = Modifier.padding(top = 22.dp),
                    text = "${state.weather.windSpeed} m/s",
                    fontSize = 20.sp,
                    color = TmrColors.mainText,
                )
            }
        }

        Text(
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 60.dp),
            text = "Last update ${state.weather.lastUpdate}",
            fontSize = 16.sp,
            color = TmrColors.mainText.copy(alpha = .3f),
        )

        Box {
            if (state.isTimer) WorkTimer(modifier = Modifier.size(250.dp))
            else ShutdownTimer(modifier = Modifier.size(250.dp), totalTime = 3600L * 1000L)
        }
    }
}
