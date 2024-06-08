package com.sielehub.treasuremart.presentation.cart.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sielehub.treasuremart.R

@Composable
fun CartCard(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(12.dp)

    ) {
        AsyncImage(
            model = R.drawable.ic_launcher_background,
            placeholder = painterResource(id = R.drawable.ic_shopping),
            contentDescription = null,
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .size(80.dp)
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        ) {
            Row(modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,) {
                Text(
                    text = "Product name here",
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = { /*TODO*/ },
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        contentColor = Color.Gray
                    ),
                    modifier = modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = null,
                        )
                }
            }
            Text(
                text = "Brief description here",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),
                    text = "KSh 18, 000",
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = modifier
                        .padding(start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedIconButton(
                        modifier = modifier.size(28.dp),
                        colors = IconButtonDefaults.outlinedIconButtonColors(
                            contentColor = Color.Gray
                        ),
                        border = BorderStroke(color = Color.Gray, width = 1.dp),
                        shape = RoundedCornerShape(8.dp),
                        onClick = {

                        }) {
                        Icon(imageVector = Icons.Default.Remove, contentDescription = null)
                    }
                    Text(text = "2", style = TextStyle(fontWeight = FontWeight.Bold))
                    OutlinedIconButton(
                        modifier = modifier.size(28.dp),
                        colors = IconButtonDefaults.outlinedIconButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        border = BorderStroke(color = MaterialTheme.colorScheme.primary, width = 1.dp),
                        shape = RoundedCornerShape(8.dp),
                        onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun CartCardPreview() {
    CartCard()
}