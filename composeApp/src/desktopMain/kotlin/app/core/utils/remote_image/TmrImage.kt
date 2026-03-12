package app.core.utils.remote_image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun TmrImage(
    modifier: Modifier = Modifier,
    model: Any?,
    clipToBounds: Boolean = true,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
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