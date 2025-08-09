package co.opntest.emarket.ui.screens.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.opntest.emarket.R
import co.opntest.emarket.extensions.collectAsEffect
import co.opntest.emarket.ui.base.BaseScreen
import co.opntest.emarket.ui.models.UiModel
import co.opntest.emarket.ui.showToast
import co.opntest.emarket.ui.theme.AppTheme.dimensions
import co.opntest.emarket.ui.theme.ComposeTheme
import timber.log.Timber

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) = BaseScreen {
    val context = LocalContext.current
    viewModel.error.collectAsEffect { e -> e.showToast(context) }

    val uiModels: List<UiModel> by viewModel.uiModels.collectAsStateWithLifecycle()

    HomeScreenContent(
        title = stringResource(id = R.string.app_name),
        uiModels = uiModels
    )
}

@Composable
private fun HomeScreenContent(
    title: String,
    uiModels: List<UiModel>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensions.spacingMedium)
        )
    }
    Timber.d("Result : $uiModels")
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    ComposeTheme {
        HomeScreenContent(
            title = stringResource(id = R.string.app_name),
            uiModels = listOf(UiModel(1), UiModel(2), UiModel(3))
        )
    }
}
