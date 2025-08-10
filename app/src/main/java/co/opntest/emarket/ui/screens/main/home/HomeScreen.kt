package co.opntest.emarket.ui.screens.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
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
import co.opntest.emarket.ui.models.ProductUiModel
import co.opntest.emarket.ui.models.StoreDetailUiModel
import co.opntest.emarket.ui.screens.common.AppCounterButton
import co.opntest.emarket.ui.screens.common.AppLoadingContent
import co.opntest.emarket.ui.screens.common.ErrorScreenContent
import co.opntest.emarket.ui.screens.common.StarRatingBar
import co.opntest.emarket.ui.showToast
import co.opntest.emarket.ui.theme.AppTheme
import co.opntest.emarket.ui.theme.ComposeTheme
import coil3.compose.AsyncImage

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onClickViewOrder: () -> Unit,
) = BaseScreen {
    val context = LocalContext.current

    val storeDetail by viewModel.storeDetail.collectAsStateWithLifecycle()
    val products by viewModel.products.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    var isError by remember { mutableStateOf(false) }

    viewModel.error.collectAsEffect { e ->
        isError = true
        e.showToast(context)
    }

    HomeScreenContent(
        isError = isError,
        isLoading = isLoading,
        products = products,
        storeDetail = storeDetail,
        onRetry = {
            isError = false
            viewModel.getStoreDetail()
            viewModel.getProductList()
        },
        onItemCountChange = viewModel::updateProductCount,
        onClickViewOrder = onClickViewOrder
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    isError: Boolean,
    isLoading: IsLoading,
    products: List<ProductUiModel>,
    storeDetail: StoreDetailUiModel?,
    onRetry: () -> Unit,
    onItemCountChange: (itemCount: Int, productUiModel: ProductUiModel) -> Unit,
    onClickViewOrder: () -> Unit,
) {
    Scaffold(
        bottomBar = {
            Button(
                onClick = onClickViewOrder,
                content = {
                    Text(text = stringResource(R.string.view_order))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimensions.spacingMedium)
            )
        },
        content = { paddingValues ->
            LazyColumn(
                contentPadding = PaddingValues(AppTheme.dimensions.spacingMedium),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {
                storeDetail?.let {
                    item {
                        StoreDetailContent(storeDetail = storeDetail)
                    }
                }

                items(products.size) { index ->
                    val product = products[index]

                    ProductItem(
                        product = product,
                        onItemCountChange = onItemCountChange
                    )
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

@Composable
private fun StoreDetailContent(
    storeDetail: StoreDetailUiModel,
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

@Composable
private fun ProductItem(
    product: ProductUiModel,
    onItemCountChange: (itemCount: Int, productUiModel: ProductUiModel) -> Unit,
) {
    Card {
        Row {
            AsyncImage(
                contentScale = ContentScale.FillBounds,
                model = product.imageUrl,
                contentDescription = stringResource(R.string.product_image),
                modifier = Modifier
                    .size(AppTheme.dimensions.imageSize)
                    .testTag(stringResource(R.string.product_image)),
            )
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimensions.spacingMedium)
            ) {
                Text(
                    text = product.name,
                    style = AppTheme.typography.themeTypography.bodyLarge,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(R.string.price, product.price),
                    style = AppTheme.typography.themeTypography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.weight(1f))

                AppCounterButton(
                    isExpandable = true,
                    initialItemCount = product.selectedCount,
                    onItemCountChange = { itemCount ->
                        onItemCountChange(itemCount, product)
                    },
                )
            }
        }
    }
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
            products = param.products,
            onRetry = {},
            onItemCountChange = { _, _ -> },
            onClickViewOrder = {}
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

    private val products = listOf(
        ProductUiModel(
            name = "Latte",
            price = 50.0,
            imageUrl = "https://www.nespresso.com/ncp/res/uploads/recipes/nespresso-recipes-Latte-Art-Tulip.jpg",
        ),
        ProductUiModel(
            name = "Dark Tiramisu Mocha",
            price = 75.0,
            imageUrl = "https://www.nespresso.com/shared_res/mos/free_html/sg/b2b/b2ccoffeerecipes/listing-image/image/dark-tiramisu-mocha.jpg",
            selectedCount = 2
        )
    )

    override val values: Sequence<Params>
        get() = sequenceOf(
            Params(isLoading = true),
            Params(isError = true),
            Params(
                isLoading = true,
                isError = true
            ),
            Params(
                storeDetail = storeDetail,
                products = products
            )
        )

    data class Params(
        val isLoading: IsLoading = false,
        val storeDetail: StoreDetailUiModel? = null,
        val isError: Boolean = false,
        val products: List<ProductUiModel> = emptyList(),
    )
}
