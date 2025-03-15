package app.core.utils.remote_image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import java.net.URI

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RemoteImage(url: String, modifier: Modifier = Modifier) {
    var imageState by remember { mutableStateOf(ImageState.Loading) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val handler = remember {
        CoroutineExceptionHandler { _, e ->
            println(e.message)
            imageState = ImageState.Error
            imageBitmap = null
        }
    }

    LaunchedEffect(url) {
        imageState = ImageState.Loading
        val bitmap = runCatching {
            withContext(Dispatchers.IO + handler) {
                URI(url).toURL()
                    .openStream()
                    .use { it.readAllBytes().decodeToImageBitmap() }
            }
        }.getOrNull()

        imageBitmap = bitmap
        delay(50) // todo Тактический делэй ! Чтоб не пролетала ошибка на UI. Пофиксить позже.
        imageState = if (bitmap != null) ImageState.Successful else ImageState.Error
    }

    when (imageState) {
        ImageState.Loading -> CircularProgressIndicator(modifier = Modifier.size(30.dp))
        ImageState.Successful -> {
            imageBitmap?.also {
                Image(bitmap = it, contentDescription = null, modifier = modifier)
            }
        }
        ImageState.Error -> Text("Ошибка загрузки", modifier = modifier)
    }
}

private enum class ImageState {
    Loading, Successful, Error
}
