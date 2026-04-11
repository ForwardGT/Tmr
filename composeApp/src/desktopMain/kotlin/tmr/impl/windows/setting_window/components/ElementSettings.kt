package tmr.impl.windows.setting_window.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import app.core.ui.components.TmrSwitch
import app.core.ui.components.TmrText

@Composable
internal fun ElementSettings(
    label: String,
    isChecked: Boolean,
    onChangeChecked: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TmrText(
            text = label,
            fontSize = 13.sp,
        )
        TmrSwitch(
            isChecked = isChecked,
            onChangeChecked = onChangeChecked,
        )
    }
}
