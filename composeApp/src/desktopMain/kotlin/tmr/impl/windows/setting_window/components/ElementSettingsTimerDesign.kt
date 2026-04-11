package tmr.impl.windows.setting_window.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import app.application.configurations.TimerDesign
import app.core.ui.components.TmrSelect
import app.core.ui.components.TmrSelectOption
import app.core.ui.components.TmrText

@Composable
internal fun ElementSettingsTimerDesign(
    label: String,
    selectedDesign: TimerDesign,
    onSelectDesign: (TimerDesign) -> Unit,
) {
    val options = remember {
        listOf(
            TmrSelectOption(TimerDesign.Minimal, "Мини"),
            TmrSelectOption(TimerDesign.Focus, "Фокус"),
            TmrSelectOption(TimerDesign.Radar, "Радар"),
        )
    }
    val selectedLabel = options.firstOrNull { it.value == selectedDesign }?.label ?: "Мини"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TmrText(
            text = label,
            fontSize = 13.sp,
        )

        TmrSelect(
            modifier = Modifier.fillMaxWidth(0.54f),
            selectedLabel = selectedLabel,
            options = options,
            onSelect = onSelectDesign,
        )
    }
}
