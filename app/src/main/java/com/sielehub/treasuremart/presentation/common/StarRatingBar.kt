package com.sielehub.treasuremart.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun StarRatingBar(
    modifier: Modifier = Modifier,
    maxRating: Int = 5,
    rating: Float = 4f,
    onRatingChanged: (Float) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (6f * density).dp
    val starSpacing = (0.25f * density).dp

    Row(
        modifier = modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (star in 1..maxRating) {
            val isSelected = star <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Outlined.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color(0x20969595)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(star.toFloat())
                        }
                    )
                    .size(starSize)
            )
            if (star < maxRating)
                Spacer(modifier = modifier.width(starSpacing))
        }
    }
}