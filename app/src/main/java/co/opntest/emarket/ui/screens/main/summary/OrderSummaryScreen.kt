package co.opntest.emarket.ui.screens.main.summary

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.opntest.emarket.R
import co.opntest.emarket.extensions.collectAsEffect
import co.opntest.emarket.lib.IsLoading
import co.opntest.emarket.ui.models.ProductUiModel
import co.opntest.emarket.ui.screens.common.AppLoadingContent
import co.opntest.emarket.ui.showToast
import co.opntest.emarket.ui.theme.AppTheme
import coil3.compose.AsyncImage

@Composable
fun OrderSummaryScreen(
    productList: List<ProductUiModel>,
    viewModel: OrderSummaryViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    viewModel.error.collectAsEffect { it.showToast(context) }

    val products by viewModel.products.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setProducts(productList)
    }

    OrderSummaryScreenContent(
        isLoading = isLoading,
        products = products,
        totalPrice = viewModel.getTotalPrice(),
        onClickPlaceOrder = viewModel::placeOrder
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderSummaryScreenContent(
    isLoading: IsLoading,
    products: List<ProductUiModel>,
    totalPrice: Double,
    onClickPlaceOrder: (deliveryAddress: String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var deliveryAddress by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.order_summary))
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .padding(AppTheme.dimensions.spacingMedium)
                    .navigationBarsPadding()
                    .imePadding()
            ) {
                TextField(
                    value = deliveryAddress,
                    onValueChange = {
                        deliveryAddress = it
                    },
                    label = { Text(stringResource(R.string.delivery_address)) },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus(force = true) }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingMedium))

                Button(
                    enabled = !isLoading,
                    onClick = {
                        onClickPlaceOrder(deliveryAddress)
                    },
                    content = {
                        Row {
                            Text(
                                stringResource(R.string.place_order)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                stringResource(R.string.product_total_price, totalPrice.toString())
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(stringResource(R.string.place_order_button))
                )
            }
        },
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(AppTheme.dimensions.spacingMedium),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium),
            modifier = Modifier
                .padding(paddingValues)
                .focusable()
        ) {
            items(products) { product ->
                ProductItem(product)
            }
        }

        if (isLoading) {
            AppLoadingContent()
        }
    }
}

@Composable
private fun ProductItem(
    product: ProductUiModel,
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
                    text = stringResource(R.string.product_count_and_name, product.selectedCount, product.name),
                    style = AppTheme.typography.themeTypography.bodyLarge,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(R.string.price, product.price),
                    style = AppTheme.typography.themeTypography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
private fun OrderSummaryScreenPreview(
    @PreviewParameter(OrderSummaryScreenPreviewParameters::class)
    param: OrderSummaryScreenPreviewParameters.Params,
) {
    OrderSummaryScreenContent(
        products = param.products,
        totalPrice = param.totalPrice,
        isLoading = param.isLoading,
        onClickPlaceOrder = {}
    )
}

private class OrderSummaryScreenPreviewParameters :
    PreviewParameterProvider<OrderSummaryScreenPreviewParameters.Params> {

    override val values: Sequence<Params>
        get() = sequenceOf(
            Params(),
            Params(isLoading = true),
        )

    private val productList = listOf(
        ProductUiModel(
            name = "Product 1",
            price = 10.0,
            imageUrl = "https://www.nespresso.com/ncp/res/uploads/recipes/nespresso-recipes-Latte-Art-Tulip.jpg",
            selectedCount = 2
        ),
        ProductUiModel(
            name = "Product 2",
            price = 15.0,
            imageUrl = "https://www.nespresso.com/ncp/res/uploads/recipes/nespresso-recipes-Latte-Art-Tulip.jpg",
            selectedCount = 1
        )
    )

    inner class Params(
        val isLoading: IsLoading = false,
        val products: List<ProductUiModel> = productList,
        val totalPrice: Double = productList.sumOf { it.price * it.selectedCount }
    )
}
