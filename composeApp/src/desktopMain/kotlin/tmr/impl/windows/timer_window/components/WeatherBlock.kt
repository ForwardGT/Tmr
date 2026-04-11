package tmr.impl.windows.timer_window.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.core.ui.components.TmrLoader
import app.core.ui.components.TmrText
import app.core.utils.remote_image.TmrImage
import tmr.impl.windows.timer_window.TmrState


@Composable
internal fun WeatherBlock(
    modifier: Modifier = Modifier,
    state: TmrState,
) {
    if (state.weather.iconUrl.isNotEmpty()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 118.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TmrText(
                modifier = Modifier.padding(start = 14.dp),
                text = "${state.weather.temperature} \u00B0C , ${state.weather.windSpeed} m/s",
                fontSize = 18.sp,
            )

            TmrImage(
                modifier = Modifier.size(46.dp),
                model = state.weather.iconUrl,
            )
        }
    } else {
        TmrLoader(
            modifier = Modifier
                .padding(top = 34.dp)
                .size(32.dp),
        )
    }
}