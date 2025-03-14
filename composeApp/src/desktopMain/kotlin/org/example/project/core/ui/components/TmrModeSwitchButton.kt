package org.example.project.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.example.project.TypeTimer
import org.example.project.core.ui.resourses.TmrColors

@Composable
fun TmrModeSwitchButton(
    typeTimer: TypeTimer,
    onClick: (typeTimer: TypeTimer) -> Unit,
    activeButton: TypeTimer,
) {
    Spacer(
        modifier = Modifier
            .size(17.dp)
            .clip(CircleShape)
            .background(color = if (activeButton == typeTimer) TmrColors.colorIconExit else TmrColors.inactiveComponent)
            .clickable { onClick(typeTimer) }
    )
}