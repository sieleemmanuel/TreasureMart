package com.sielehub.treasuremart.presentation.util

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

fun Modifier.shimmerEffect(
    shape: Shape = RoundedCornerShape(0.dp)
): Modifier = composed {
    val animDuration = 1250
    val angle = 20.0
    val shimmerLineWidth = 400f
    val speedMultiplier = 4
    val dx: Float = cos(Math.toRadians(angle)).toFloat() * shimmerLineWidth
    val dy: Float = sin(Math.toRadians(angle)).toFloat() * shimmerLineWidth
    val colors = listOf(
        Color.LightGray.copy(alpha = 0.7f),
        Color.White.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.7f)
    )
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "shimmer transition")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animDuration,
                easing = LinearEasing
            )
        ), label = ""
    )
    val x0 = (speedMultiplier * size.width + tan(Math.toRadians(angle)).toFloat() * size.height) *
            translateAnim + shimmerLineWidth * (translateAnim - 1)
    val y0 = 0f
    val x1 = x0 + dx
    val y1 = y0 + dy
    background(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset(x0, y0),
            end = Offset(x1, y1)
        ),
        shape = shape
    )
        .onGloballyPositioned {
            size = it.size
        }
}

fun Modifier.applyChoice(
    condition: Boolean? = null,
    modifierTrue: Modifier.() -> Modifier,
    modifierFalse: Modifier.() -> Modifier
): Modifier {
    return when {
        condition == true -> {
            then(modifierTrue(Modifier))
        }

        condition?.not() == true -> {
            then(modifierFalse(Modifier))
        }

        else -> this
    }
}

inline fun <reified T> String.jsonToModel(): T {
    return Json.decodeFromString<T>(this)
}

inline fun <reified T> T.modelToJsonArray(): String {
    return Json.encodeToString(this)
}

fun Double.formatedCurrency(): String {
    return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)
}