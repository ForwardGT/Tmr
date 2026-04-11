package app.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.core.ui.resources.TmrColors
import app.core.ui.resources.TmrShapes

@Composable
fun TmrTextField(
    submit: (String?) -> Unit = {},
    autoFocus: Boolean = false,
) {
    val (text, onValueChange) = remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(autoFocus) {
        if (autoFocus) {
            focusRequester.requestFocus()
        }
    }

    BasicTextField(
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        textStyle = TextStyle(
            color = TmrColors.mainText,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        ),
        modifier = Modifier
            .size(width = 60.dp, height = 30.dp)
            .clip(TmrShapes.shape8)
            .border(2.dp, Color.Black, TmrShapes.shape8)
            .background(TmrColors.inactiveComponent)
            .padding(horizontal = 3.dp, vertical = 3.dp)
            .focusRequester(focusRequester)
            .onKeyEvent { event ->
                when (event.key) {
                    Key.Enter -> {
                        val text = text.trim()
                        submit(text)
                        onValueChange("")
                        true
                    }

                    Key.Escape -> {
                        onValueChange("")
                        submit(null)
                        true
                    }

                    else -> false
                }
            },
    )
}

@Preview
@Composable
private fun TmrTextFieldPreview() {
    TmrTextField(submit = {})
}
