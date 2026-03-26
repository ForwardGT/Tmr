package app.core.utils.remote_image

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun TmrImage(
    modifier: Modifier = Modifier,
    model: Any,
    clipToBounds: Boolean = true,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    tintColor: Color = Color.White,
) {
    when (model) {
        is String -> {
            AsyncImage(
                modifier = modifier,
                model = model,
                contentDescription = null,
                clipToBounds = clipToBounds,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter
            )
        }

        is ImageVector -> {
            Icon(
                modifier = modifier,
                imageVector = model,
                tint = tintColor,
                contentDescription = null,
            )
        }
    }
}