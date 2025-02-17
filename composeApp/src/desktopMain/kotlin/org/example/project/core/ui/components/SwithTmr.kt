package org.example.project.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.core.ui.resourses.TmrColors

@Composable
fun SwitchTmr(
    modifier: Modifier = Modifier,
    state: Boolean,
    onClick: (Boolean) -> Unit,
) {
    Switch(
        modifier = modifier.padding(start = 6.dp),
        checked = state,
        onCheckedChange = { onClick(it) },
        colors = SwitchDefaults.colors(
            checkedThumbColor = TmrColors.inactiveComponent,
            checkedTrackColor = TmrColors.inactiveComponent,
            uncheckedThumbColor = TmrColors.inactiveComponent,
            uncheckedTrackColor = TmrColors.inactiveComponent,
        )
    )
}