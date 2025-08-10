package co.opntest.emarket.ui.models

import android.os.Parcelable
import co.opntest.emarket.domain.models.ProductModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUiModel(
    val name: String,
    val price: Double,
    val imageUrl: String,
    var selectedCount: Int = 0,
) : Parcelable

fun ProductModel.toUiModel() = ProductUiModel(
    name = name.orEmpty(),
    price = price ?: 0.0,
    imageUrl = imageUrl.orEmpty(),
)

fun List<ProductModel>.toUiModelList() = map { it.toUiModel() }

fun ProductUiModel.toDomainModel() = ProductModel(
    name = name,
    price = price,
    imageUrl = imageUrl,
)
