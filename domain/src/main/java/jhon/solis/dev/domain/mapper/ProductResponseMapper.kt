package jhon.solis.dev.domain.mapper

import jhon.solis.dev.domain.dto.ProductResponseDto
import jhon.solis.dev.domain.model.Product

fun ProductResponseDto.toDomainList(): List<Product> {
    return result
        ?.records
        ?.map {
            it.toDomain()
        }
        ?: emptyList()
}