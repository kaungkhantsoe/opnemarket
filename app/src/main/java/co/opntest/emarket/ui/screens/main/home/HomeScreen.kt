package co.opntest.emarket.ui.screens.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.opntest.emarket.R
import co.opntest.emarket.extensions.collectAsEffect
import co.opntest.emarket.lib.IsLoading
import co.opntest.emarket.ui.base.BaseScreen
import co.opntest.emarket.ui.models.StoreDetailUiModel
import co.opntest.emarket.ui.screens.common.AppLoadingContent
import co.opntest.emarket.ui.screens.common.ErrorScreenContent
import co.opntest.emarket.ui.screens.common.StarRatingBar
import co.opntest.emarket.ui.showToast
import co.opntest.emarket.ui.theme.AppTheme
import co.opntest.emarket.ui.theme.ComposeTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) = BaseScreen {
    val context = LocalContext.current
    viewModel.error.collectAsEffect { e -> e.showToast(context) }

    val storeDetail by viewModel.storeDetail.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    var isError by remember { mutableStateOf(false) }

    viewModel.error.collectAsEffect {
        isError = true
    }

    HomeScreenContent(
        isLoading = isLoading,
        isError = isError,
        storeDetail = storeDetail,
        onRetry = {
            isError = false
            viewModel.getStoreDetail()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    isLoading: IsLoading,
    isError: Boolean,
    storeDetail: StoreDetailUiModel?,
    onRetry: () -> Unit,
) {
    Scaffold(
        content = { paddingValues ->
            storeDetail?.let {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                        .padding(AppTheme.dimensions.spacingMedium)
                ) {
                    Text(
                        text = storeDetail.name,
                        style = AppTheme.typography.themeTypography.titleLarge,
                        fontWeight = FontWeight.W700
                    )

                    StarRatingBar(
                        rating = storeDetail.rating
                    )

                    val shouldShowTime = storeDetail.openingTime.isNotEmpty() && storeDetail.closingTime.isNotEmpty()
                    if (shouldShowTime) {
                        Text(
                            text = stringResource(
                                R.string.open_and_close_time,
                                storeDetail.openingTime,
                                storeDetail.closingTime,
                            ),
                            style = AppTheme.typography.themeTypography.labelLarge,
                            modifier = Modifier.padding(top = AppTheme.dimensions.spacingXSmall)
                        )
                    }
                }
            }

            if (isError) {
                ErrorScreenContent(
                    onRetry = onRetry,
                    modifier = Modifier.fillMaxSize()
                )
            }

            if (isLoading) {
                AppLoadingContent()
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(HomeScreenPreviewParameters::class)
    param: HomeScreenPreviewParameters.Params,

    ) {
    ComposeTheme {
        HomeScreenContent(
            isError = param.isError,
            isLoading = param.isLoading,
            storeDetail = param.storeDetail,
            onRetry = {}
        )
    }
}

private class HomeScreenPreviewParameters : PreviewParameterProvider<HomeScreenPreviewParameters.Params> {
    private val storeDetail = StoreDetailUiModel(
        name = "The Coffee Shop",
        rating = 4.6,
        openingTime = "9 AM",
        closingTime = "9 PM"
    )

    override val values: Sequence<Params>
        get() = sequenceOf(
            Params(isLoading = true),
            Params(isError = true),
            Params(
                isLoading = true,
                isError = true
            ),
            Params(storeDetail = storeDetail)
        )

    data class Params(
        val isLoading: IsLoading = false,
        val storeDetail: StoreDetailUiModel? = null,
        val isError: Boolean = false,
    )
}
