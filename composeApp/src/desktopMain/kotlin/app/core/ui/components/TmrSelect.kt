package app.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
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

data class TmrSelectOption<T>(
    val value: T,
    val label: String,
)

@Composable
fun <T> TmrSelect(
    modifier: Modifier = Modifier,
    selectedLabel: String,
    options: List<TmrSelectOption<T>>,
    onSelect: (T) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(TmrShapes.shape8)
                .clickable { isExpanded = true }
                .background(TmrColors.selectBackground)
                .border(1.dp, TmrColors.inactiveBar, TmrShapes.shape8)
                .padding(horizontal = 8.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TmrText(
                text = selectedLabel,
                fontSize = 13.sp,
            )
            TmrSpacer(width = 6.dp)
            TmrText(
                text = "▼",
                fontSize = 10.sp,
                alpha = 0.8f,
            )
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .background(TmrColors.inactiveComponent)
                .border(1.dp, TmrColors.inactiveBar, TmrShapes.shape8),
        ) {
            options.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelect(option.value)
                            isExpanded = false
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                ) {
                    TmrText(
                        text = option.label,
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TmrSelectPreview() {
    val options = remember {
        listOf(
            TmrSelectOption(value = "minimal", label = "Мини"),
            TmrSelectOption(value = "focus", label = "Фокус"),
            TmrSelectOption(value = "radar", label = "Радар"),
        )
    }
    var selected by remember { mutableStateOf(options.first()) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TmrText(text = "Вид таймера", fontSize = 13.sp)
        TmrSelect(
            modifier = Modifier.fillMaxWidth(0.48f),
            selectedLabel = selected.label,
            options = options,
            onSelect = { value -> selected = options.first { it.value == value } },
        )
    }
}
