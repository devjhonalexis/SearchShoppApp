package jhon.solis.dev.domain.mapper

import jhon.solis.dev.domain.dto.ProductDto
import jhon.solis.dev.domain.model.Product

fun ProductDto.toDomain(): Product {
    return Product(
        id = productId.orEmpty(),
        productName = productDisplayName.orEmpty(),
        priceBeforeDiscount = listPrice ?: 0.0,
        priceAfterDiscount = promoPrice ?: 0.0,
        imageUrl = smImage.orEmpty(),
        colors = variantsColor?.mapNotNull { it.colorHex.takeIf { color -> !color.isNullOrBlank() } } ?: emptyList(),
    )
}