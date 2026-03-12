package app.core.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.core.ui.resourses.TmrColors


@Composable
fun TmrLoader(
    modifier: Modifier = Modifier.size(30.dp),
    color: Color = TmrColors.mainText,
    cycleDuration: Int = 1500,
    borderWidth: Dp = 3.dp,
) {
    val transition = rememberInfiniteTransition()

    val rotation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(cycleDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(modifier) {

        RotatingCircle(
            modifier = Modifier.matchParentSize(),
            rotationX = 35f,
            rotationY = -45f,
            rotationZ = -90f + rotation.value,
            borderColor = color,
            borderWidth = borderWidth
        )

        RotatingCircle(
            modifier = Modifier.matchParentSize(),
            rotationX = 50f,
            rotationY = 10f,
            rotationZ = rotation.value,
            borderColor = color,
            borderWidth = borderWidth
        )

        RotatingCircle(
            modifier = Modifier.matchParentSize(),
            rotationX = 35f,
            rotationY = 55f,
            rotationZ = 90f + rotation.value,
            borderColor = color,
            borderWidth = borderWidth
        )
    }
}

@Composable
private fun RotatingCircle(
    modifier: Modifier,
    rotationX: Float,
    rotationY: Float,
    rotationZ: Float,
    borderColor: Color,
    borderWidth: Dp,
) {
    Canvas(
        modifier.graphicsLayer {
            this.rotationX = rotationX
            this.rotationY = rotationY
            this.rotationZ = rotationZ
            cameraDistance = 12f * density
        }
    ) {

        val mainCircle = Path().apply {
            addOval(
                Rect(
                    center = size.center,
                    radius = size.minDimension / 2
                )
            )
        }

        val clipCenter = Offset(
            size.width / 2f - borderWidth.toPx(),
            size.height / 2f
        )

        val clipCircle = Path().apply {
            addOval(
                Rect(
                    center = clipCenter,
                    radius = size.minDimension / 2
                )
            )
        }

        val resultPath = Path().apply {
            op(mainCircle, clipCircle, PathOperation.Difference)
        }

        drawPath(
            path = resultPath,
            color = borderColor
        )
    }
}