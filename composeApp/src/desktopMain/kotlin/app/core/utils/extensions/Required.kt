package app.core.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.required(condition: Boolean, modifier: @Composable Modifier.() -> Modifier): Modifier {
    return if (condition) this.modifier() else this
}
