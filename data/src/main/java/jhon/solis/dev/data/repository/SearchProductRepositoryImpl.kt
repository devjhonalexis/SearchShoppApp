package jhon.solis.dev.data.repository

import jhon.solis.dev.data.remote.ApiService
import jhon.solis.dev.data.remote.utils.BaseNetworkResponse
import jhon.solis.dev.domain.dto.ProductResponseDto
import jhon.solis.dev.domain.repository.SearchProductRepository
import jhon.solis.dev.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val baseNetworkResponse: BaseNetworkResponse
) : SearchProductRepository {
    override suspend fun searchProductByKeyword(
        query: String,
        page: Int,
        order: Int?
    ): Flow<Result<ProductResponseDto>> {
        return baseNetworkResponse.callService {
            apiService.searchShoppAppProduct(
                query = query,
                page = page,
                order = order
            )
        }
    }
}