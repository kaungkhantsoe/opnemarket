package co.opntest.emarket.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import co.opntest.emarket.ui.theme.AppTheme
import co.opntest.emarket.R

@Composable
fun ErrorScreenContent(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.something_went_wrong),
            modifier = Modifier.padding(AppTheme.dimensions.spacingMedium)
        )
        Button(
            onClick = onRetry,
            modifier = Modifier.padding(AppTheme.dimensions.spacingMedium)
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}
