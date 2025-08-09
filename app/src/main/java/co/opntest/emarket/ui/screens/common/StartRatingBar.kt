package co.opntest.emarket.ui.screens.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import co.opntest.emarket.ui.theme.AppTheme

// Reference : https://medium.com/@imitiyaz125/star-rating-bar-in-jetpack-compose-5ae54a2b5b23
@Composable
fun StarRatingBar(
    rating: Double,
    modifier: Modifier = Modifier,
    maxStars: Int = 5,
    onRatingChanged: (Float) -> Unit? = {},
) {
    val density = LocalDensity.current.density
    val starSize = (10f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (index in 1..maxStars) {
            val isSelected = index <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color(0x21595656)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(index.toFloat())
                        }
                    )
                    .width(starSize)
                    .height(starSize)
            )

            if (index < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }

        Text(
            text = "($rating)",
            style = AppTheme.typography.themeTypography.labelLarge,
            modifier = Modifier.padding(start = AppTheme.dimensions.spacingXSmall)
        )
    }
}
