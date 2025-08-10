package co.opntest.emarket.ui.screens.common

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import co.opntest.emarket.R
import co.opntest.emarket.ui.theme.AppTheme
import co.opntest.emarket.ui.theme.ComposeTheme
import kotlin.run

@Composable
fun AppCounterButton(
    onItemCountChange: (itemCount: Int) -> Unit,
    modifier: Modifier = Modifier,
    minCount: Int = 1,
    initialItemCount: Int = 0,
    isExpandable: Boolean = false,
    hasCounterButtons: Boolean = true,
    enabled: Boolean = true,
    styles: AppCounterButtonDefaults.Styles = AppCounterButtonDefaults.styles(),
) {
    var lastItemCount by remember(initialItemCount) { mutableIntStateOf(initialItemCount) }
    var itemCount by remember(initialItemCount) { mutableIntStateOf(initialItemCount) }
    val isItemAddedState by remember(initialItemCount) {
        derivedStateOf {
            itemCount > minCount - 1
        }
    }

    LaunchedEffect(itemCount) {
        if (hasCounterButtons && itemCount != lastItemCount) {
            lastItemCount = itemCount
            onItemCountChange(itemCount)
        }
    }

    val transition = updateTransition(isItemAddedState, label = "add item button state")
    val backgroundColor by transition.animateColor(label = "add item button background") { isAdded ->
        if (isAdded || !isExpandable) Color.Unspecified else styles.containerColor(enabled)
    }
    val buttonWidth by transition.animateDp(label = "add item button width") { isAdded ->
        if (isAdded) Dp.Unspecified else styles.height
    }
    val borderColor by transition.animateColor(label = "add item button border") { isAdded ->
        if (isAdded) styles.containerColor(enabled) else Color.Unspecified
    }

    Surface(
        color = backgroundColor,
        shape = styles.shape,
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier
            .height(styles.height)
            .sizeIn(
                minWidth = styles.height,
                maxWidth = if (hasCounterButtons) buttonWidth else styles.height
            )
    ) {
        transition.AnimatedContent { isAdded ->
            if (isAdded || !isExpandable) {
                // TODO modify to counter button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = styles.horizontalArrangement,
                    modifier = Modifier
                        .padding(if (hasCounterButtons) styles.contentPadding else PaddingValues())
                ) {
                    if (hasCounterButtons) {
                        Icon(
                            painter = painterResource(id = styles.minusIcon),
                            contentDescription = null,
                            tint = if (itemCount > minCount) {
                                styles.counterColor(enabled)
                            } else {
                                styles.counterColor(false)
                            },
                            // Allow default clicks behavior with no throttle
                            modifier = Modifier.clickable(
                                enabled = (isExpandable || itemCount > minCount) && enabled,
                                onClick = { itemCount -= 1 }
                            ),
                        )
                    }
                    Text(
                        text = itemCount.toString(),
                        style = styles.counterTextStyle.run {
                            copy(
                                color = if (enabled) color else styles.contentColor(false)
                            )
                        },
                        textAlign = TextAlign.Center,
                    )
                    if (hasCounterButtons) {
                        Icon(
                            painter = painterResource(id = styles.plusIcon),
                            contentDescription = null,
                            tint = styles.counterColor(enabled),
                            // Allow default clicks behavior with no throttle
                            modifier = Modifier.clickable(
                                enabled = enabled,
                                onClick = { itemCount += 1 }
                            )
                        )
                    }
                }
            } else {
                Icon(
                    painter = painterResource(id = styles.plusIcon),
                    contentDescription = null,
                    tint = styles.contentColor(enabled),
                    modifier = Modifier
                        .padding(styles.buttonIconSpacing)
                        .run {
                            if (hasCounterButtons) {
                                clickable(
                                    enabled = enabled,
                                    onClick = { if (itemCount == 0) itemCount = 1 }
                                )
                            } else {
                                this
                            }
                        }
                )
            }
        }
    }
}

object AppCounterButtonDefaults {
    const val DisabledContainerOpacity = 0.12f
    const val DisabledLabelTextOpacity = 0.38f
    private val SmallButtonSize = 28.dp
    private val LargeButtonSize = 48.dp
    private val ButtonIconSpacing = 6.dp

    data class Styles(
        val buttonIconSpacing: Dp,
        val contentPadding: PaddingValues,
        val counterTextStyle: TextStyle,
        private val containerColor: Color,
        private val contentColor: Color,
        private val disabledContainerColor: Color,
        private val disabledContentColor: Color,
        val height: Dp,
        val horizontalArrangement: Arrangement.Horizontal,
        @DrawableRes
        val minusIcon: Int,
        @DrawableRes
        val plusIcon: Int,
        val shape: Shape,
    ) {
        fun containerColor(enabled: Boolean): Color = if (enabled) containerColor else disabledContainerColor

        fun contentColor(enabled: Boolean): Color = if (enabled) contentColor else disabledContentColor

        fun counterColor(enabled: Boolean): Color = if (enabled) containerColor else disabledContentColor

        @Composable
        fun large() = copy(
            counterTextStyle = AppTheme.typography.themeTypography.bodySmall,
            height = LargeButtonSize,
            horizontalArrangement = Arrangement.SpaceBetween,
            minusIcon = R.drawable.ic_minus_large,
            plusIcon = R.drawable.ic_plus_large,
            shape = AppTheme.shapes.themeShapes.medium,
        )
    }

    // styles() is the default with small size
    @Composable
    fun styles() = Styles(
        buttonIconSpacing = ButtonIconSpacing,
        contentPadding = PaddingValues(horizontal = AppTheme.dimensions.spacingSmall),
        counterTextStyle = AppTheme.typography.themeTypography.bodySmall,
        containerColor = AppTheme.colors.themeColors.primary,
        contentColor = AppTheme.colors.themeColors.onPrimary,
        disabledContainerColor = AppTheme.colors.themeColors.onSurface.copy(alpha = DisabledContainerOpacity),
        disabledContentColor = AppTheme.colors.themeColors.onSurface.copy(alpha = DisabledLabelTextOpacity),
        height = SmallButtonSize,
        horizontalArrangement = Arrangement.spacedBy(
            space = AppTheme.dimensions.spacingSmall,
            alignment = Alignment.CenterHorizontally
        ),
        minusIcon = R.drawable.ic_minus_medium,
        plusIcon = R.drawable.ic_plus_medium,
        shape = RoundedCornerShape(SmallButtonSize / 2),
    )
}

@Preview
@Composable
private fun AppCounterButtonPreview(
    @PreviewParameter(AppCounterButtonPreviewParameterProvider::class)
    params: AppCounterButtonPreviewParameterProvider.Params,
) {
    Surface {
        ComposeTheme {
            AppCounterButton(
                minCount = params.minCount,
                initialItemCount = params.itemCount,
                styles = params.styles(),
                onItemCountChange = {},
                isExpandable = params.isExpandable,
                hasCounterButtons = params.hasCounterButtons,
                enabled = params.enabled,
                modifier = Modifier.padding(AppTheme.dimensions.spacingMedium)
            )
        }
    }
}

private class AppCounterButtonPreviewParameterProvider :
    PreviewParameterProvider<AppCounterButtonPreviewParameterProvider.Params> {

    override val values = sequenceOf(
        Params(),
        Params(enabled = false),
        // Add new mode with default min amount 1
        Params(itemCount = 1),
        // Add new mode
        Params(minCount = 2, itemCount = 2),
        // Edit mode
        Params(minCount = 0, itemCount = 2),
        // Delete mode
        Params(minCount = 0, itemCount = 0),
        Params(itemCount = 10),
        Params(
            itemCount = 10,
            enabled = false,
        ),
        Params(
            itemCount = 10,
            styles = {
                AppCounterButtonDefaults.styles()
            },
            hasCounterButtons = false
        ),
        Params(
            styles = {
                AppCounterButtonDefaults.styles()
            },
        ),
        Params(
            styles = {
                AppCounterButtonDefaults.styles()
            },
            enabled = false,
        ),
        Params(
            styles = {
                AppCounterButtonDefaults.styles()
            },
            itemCount = 10,
        ),
        Params(
            styles = {
                AppCounterButtonDefaults.styles()
            },
            itemCount = 10,
            enabled = false,
        ),
    )

    inner class Params(
        val minCount: Int = 1,
        val itemCount: Int = 0,
        val isExpandable: Boolean = true,
        val styles: @Composable () -> AppCounterButtonDefaults.Styles = {
            AppCounterButtonDefaults.styles().large()
        },
        val hasCounterButtons: Boolean = true,
        val enabled: Boolean = true,
    )
}
