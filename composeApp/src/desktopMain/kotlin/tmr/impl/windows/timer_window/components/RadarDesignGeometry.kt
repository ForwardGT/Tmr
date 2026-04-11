package tmr.impl.windows.timer_window.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import app.core.ui.resources.TmrColors
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

internal data class RadarBlip(
    val angleDeg: Float,
    val radiusFactor: Float,
)

internal fun radarAngleRadians(
    index: Int,
    dialTicks: Int,
): Double = ((index * (360f / dialTicks)) - 90f) * (PI / 180f)

internal fun radarPoint(
    center: Offset,
    radius: Float,
    angleRadians: Double,
): Offset = Offset(
    x = center.x + radius * cos(angleRadians).toFloat(),
    y = center.y + radius * sin(angleRadians).toFloat(),
)

internal fun radarAngleDegreesRadians(
    angleDegrees: Float,
): Double = (angleDegrees - 90f) * (PI / 180f)

internal fun radarBlipAlpha(
    sweepAngleDegrees: Float,
    blipAngleDegrees: Float,
    pulse: Float,
): Float {
    val distance = radarAngleDistanceDegrees(sweepAngleDegrees, blipAngleDegrees)
    val lock = 1f - (distance / RADAR_BLIP_LOCK_SECTOR_DEGREES).coerceIn(0f, 1f)
    return 0.10f + lock * (0.52f + 0.2f * pulse)
}

internal fun DrawScope.drawRadarRingsAndCrosshair(
    center: Offset,
    radius: Float,
    ringBaseAlpha: Float = 0.12f,
) {
    repeat(RADAR_RING_LAYERS) { layer ->
        drawCircle(
            color = TmrColors.inactiveBar.copy(alpha = ringBaseAlpha + layer * 0.04f),
            radius = radius * (1f - layer * 0.22f),
            center = center,
            style = Stroke(width = 1.dp.toPx()),
        )
    }

    drawLine(
        color = TmrColors.radarCrosshair,
        start = Offset(x = center.x - radius * 0.86f, y = center.y),
        end = Offset(x = center.x + radius * 0.86f, y = center.y),
        strokeWidth = 1.dp.toPx(),
    )
    drawLine(
        color = TmrColors.radarCrosshair,
        start = Offset(x = center.x, y = center.y - radius * 0.86f),
        end = Offset(x = center.x, y = center.y + radius * 0.86f),
        strokeWidth = 1.dp.toPx(),
    )
}

internal fun DrawScope.drawRadarBlips(
    center: Offset,
    radius: Float,
    blips: List<RadarBlip>,
    sweepAngleDegrees: Float,
    pulse: Float,
) {
    blips.forEach { blip ->
        val angleDeg = blip.angleDeg
        val angleRadians = radarAngleDegreesRadians(angleDeg)
        val blipCenter = radarPoint(
            center = center,
            radius = radius * blip.radiusFactor,
            angleRadians = angleRadians,
        )
        val blipAlpha = radarBlipAlpha(
            sweepAngleDegrees = sweepAngleDegrees,
            blipAngleDegrees = angleDeg,
            pulse = pulse,
        )
        drawCircle(
            color = TmrColors.activeBar.copy(alpha = blipAlpha),
            center = blipCenter,
            radius = 2.4.dp.toPx(),
        )
    }
}

internal fun generateRadarBlips(
    seed: Int,
    count: Int = 5,
    minRadiusFactor: Float = 0.34f,
    maxRadiusFactor: Float = 0.78f,
): List<RadarBlip> {
    if (count <= 0) return emptyList()

    val random = Random(seed)
    val sectorSize = 360f / count

    return List(count) { index ->
        val sectorStart = index * sectorSize
        val angleOffset = random.nextFloat() * sectorSize * 0.7f
        val angle = sectorStart + angleOffset
        val radiusFactor = minRadiusFactor + random.nextFloat() * (maxRadiusFactor - minRadiusFactor)
        RadarBlip(angleDeg = angle, radiusFactor = radiusFactor)
    }
}

private fun radarAngleDistanceDegrees(
    a: Float,
    b: Float,
): Float {
    val diff = kotlin.math.abs(normalizeAngleDegrees(a) - normalizeAngleDegrees(b))
    return minOf(diff, 360f - diff)
}

private fun normalizeAngleDegrees(angle: Float): Float {
    var normalized = angle % 360f
    if (normalized < 0f) normalized += 360f
    return normalized
}

private const val RADAR_RING_LAYERS = 4
private const val RADAR_BLIP_LOCK_SECTOR_DEGREES = 80f
