package tmr.impl.windows.main_window.shutdown_timer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.core.ui.resourses.TmrColors

@Composable
fun TmrTextField(submit: (String) -> Unit = {}) {
    val (text, onValueChange) = remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        textStyle = TextStyle(
            color = TmrColors.mainText,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        ),
        modifier = Modifier
            .size(60.dp, 30.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
            .background(TmrColors.inactiveComponent)
            .padding(horizontal = 3.dp, vertical = 3.dp)
            .focusRequester(focusRequester)
            .onKeyEvent { event ->
                if (event.key == Key.Enter) {
                    val cleanedText = text.trim()
                    submit(cleanedText)
                    onValueChange("")
                    true
                } else false
            },
    )
}