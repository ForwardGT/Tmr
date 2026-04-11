package app.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.core.ui.resources.TmrColors
import app.core.ui.resources.TmrShapes

@Composable
fun TmrChip(
    modifier: Modifier = Modifier,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(TmrShapes.shape8)
            .clickable(onClick = onClick)
            .background(
                if (isSelected) {
                    TmrColors.chipSelectedBackground
                } else {
                    TmrColors.chipUnselectedBackground
                },
            )
            .border(
                width = 1.dp,
                color = if (isSelected) TmrColors.activeSwitch else TmrColors.inactiveBar,
                shape = TmrShapes.shape8,
            )
            .padding(vertical = 4.dp, horizontal = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        TmrText(
            text = label,
            fontSize = 12.sp,
            alpha = if (isSelected) 1f else 0.75f,
        )
    }
}

@Preview
@Composable
private fun TmrChipPreview() {
    var selected by remember { mutableStateOf("minimal") }

Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        TmrChip(
            modifier = Modifier.weight(1f),
            label = "Мини",
            isSelected = selected == "minimal",
            onClick = { selected = "minimal" },
        )
        TmrSpacer(width = 6.dp)
        TmrChip(
            modifier = Modifier.weight(1f),
            label = "Фокус",
            isSelected = selected == "focus",
            onClick = { selected = "focus" },
        )
        TmrSpacer(width = 6.dp)
        TmrChip(
            modifier = Modifier.weight(1f),
            label = "Радар",
            isSelected = selected == "radar",
            onClick = { selected = "radar" },
        )
    }
}
