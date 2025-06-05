package com.sielehub.treasuremart.presentation.util

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.NumberFormat
import java.util.Locale

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "shimmer transition")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(animation = tween(1000)), label = ""
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.LightGray.copy(alpha = .9f),
                Color.LightGray.copy(alpha = .3f),
                Color.LightGray.copy(alpha = .9f)
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat() * 3, size.height.toFloat())
        )
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