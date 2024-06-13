package com.sielehub.treasuremart.presentation.common

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BadgedIcon(icon: ImageVector, count: Int) {
    BadgedBox(badge = {
        Badge {
            Text(text = "$count")
        }
    }) {
        Icon(imageVector = icon, contentDescription = null)
    }
}
