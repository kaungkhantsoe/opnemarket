package co.opntest.emarket.ui.screens.main.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import co.opntest.emarket.R
import co.opntest.emarket.ui.models.ProductUiModel
import co.opntest.emarket.ui.theme.AppTheme
import coil3.compose.AsyncImage

@Composable
fun OrderSummaryScreen(
    viewModel: OrderSummaryViewModel = hiltViewModel(),
) {
    OrderSummaryScreenContent(
        products = viewModel.products,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderSummaryScreenContent(
    products: List<ProductUiModel>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.order_summary))
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {},
                content = {
                    Row {
                        Text(
                            stringResource(R.string.place_order)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            stringResource(R.string.product_total_price, "100.0")
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimensions.spacingMedium)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(AppTheme.dimensions.spacingMedium),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(products) { product ->
                ProductItem(product)
            }
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
private fun OrderSummaryScreenPreview() {
    OrderSummaryScreenContent(
        products = listOf(
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
        ),
    )
}
