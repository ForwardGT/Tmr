package app.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.core.ui.resources.TmrColors
import app.core.ui.resources.TmrShapes

private object SwitchDefaults {
    const val DURATION_MILLIS = 250
    val ContainerWidth = 33.dp
    val ContainerHeight = 13.dp
    val ThumbSize = 19.dp
    val InnerCircleSize = 15.dp
    val ComponentHeight = 19.dp
}

@Composable
fun TmrSwitch(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onChangeChecked: (Boolean) -> Unit,
) {
    var isCheckedSwitch by remember(isChecked) { mutableStateOf(isChecked) }
    var containerWidth by remember { mutableStateOf(0f) }
    var roundWidth by remember { mutableStateOf(0f) }
    var shouldAnimate by remember { mutableStateOf(false) }

    val colorAnimationSpec = remember(shouldAnimate) {
        if (shouldAnimate) tween<Color>(SwitchDefaults.DURATION_MILLIS) else snap()
    }
    val offsetAnimationSpec = remember(shouldAnimate) {
        if (shouldAnimate) tween<Offset>(SwitchDefaults.DURATION_MILLIS) else snap()
    }

    var targetOffset by remember { mutableStateOf(Offset.Zero) }

    LaunchedEffect(containerWidth, roundWidth, isCheckedSwitch) {
        if (containerWidth > 0 && roundWidth > 0) {
            targetOffset = if (isCheckedSwitch) {
                Offset(containerWidth - roundWidth, 0f)
            } else {
                Offset.Zero
            }
        }
    }

    val backGroundColor by animateColorAsState(
        targetValue = if (isCheckedSwitch) TmrColors.activeSwitch else TmrColors.inactiveComponent,
        animationSpec = colorAnimationSpec,
    )

    val animatablePosition by animateOffsetAsState(
        targetValue = targetOffset,
        animationSpec = offsetAnimationSpec,
    )

    Box(modifier = modifier.height(SwitchDefaults.ComponentHeight)) {
        Box(
            modifier = Modifier
                .width(SwitchDefaults.ContainerWidth)
                .height(SwitchDefaults.ContainerHeight)
                .onGloballyPositioned { containerWidth = it.size.width.toFloat() }
                .clip(TmrShapes.shape12)
                .background(backGroundColor)
                .align(Alignment.Center),
        )

        Box(
            modifier = Modifier
                .size(SwitchDefaults.ThumbSize)
                .onGloballyPositioned { roundWidth = it.size.width.toFloat() }
                .graphicsLayer { translationX = animatablePosition.x }
                .clip(CircleShape)
                .background(backGroundColor)
                .align(Alignment.CenterStart)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = {
                        shouldAnimate = true
                        isCheckedSwitch = !isCheckedSwitch
                        onChangeChecked(isCheckedSwitch)
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(SwitchDefaults.InnerCircleSize)
                    .clip(TmrShapes.shape12)
                    .background(TmrColors.inactiveComponent),
            )
        }
    }
}

@Preview
@Composable
private fun TmrSwitchPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        var checked1 by remember { mutableStateOf(false) }
        TmrSwitch(isChecked = checked1, onChangeChecked = { checked1 = it })

        var checked2 by remember { mutableStateOf(true) }
        TmrSwitch(isChecked = checked2, onChangeChecked = { checked2 = it })
    }
}
