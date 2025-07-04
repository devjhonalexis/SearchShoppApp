package jhon.solis.dev.domain.repository

import jhon.solis.dev.domain.dto.ProductResponseDto
import kotlinx.coroutines.flow.Flow
import jhon.solis.dev.domain.util.Result

interface SearchProductRepository {
    suspend fun searchProductByKeyword(query: String, page: Int, order: Int?): Flow<Result<ProductResponseDto>>
}