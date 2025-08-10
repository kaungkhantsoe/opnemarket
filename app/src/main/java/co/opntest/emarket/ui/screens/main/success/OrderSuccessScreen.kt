package co.opntest.emarket.ui.screens.main.success

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import co.opntest.emarket.R
import co.opntest.emarket.ui.theme.AppTheme

@Composable
fun OrderSuccessScreen(
    onClickContinueShopping: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.order_success),
            style = AppTheme.typography.themeTypography.titleLarge,
            fontWeight = FontWeight.W700,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClickContinueShopping,
            content = {
                Text(text = stringResource(R.string.continue_shopping))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.dimensions.spacingMedium)
        )
    }
}

@Preview
@Composable
private fun OrderSuccessScreenPreview() {
    OrderSuccessScreen(
        onClickContinueShopping = {}
    )
}
