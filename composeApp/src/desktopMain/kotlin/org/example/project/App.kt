package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.core.ui.components.ExitButton
import org.example.project.core.ui.components.SwitchTmr
import org.example.project.main_window.shutdown_timer.ShutdownTimer
import org.example.project.main_window.work_timer.WorkTimer

@Composable
fun App(closeApp: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        var state by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SwitchTmr(state = state) {
                state = it
            }
            ExitButton(closeApp)
        }

        if (state) {
            WorkTimer(modifier = Modifier.size(250.dp))
        } else {
            ShutdownTimer(
                modifier = Modifier.size(250.dp),
                totalTime = 3600L * 1000L,
            )
        }
    }
}
