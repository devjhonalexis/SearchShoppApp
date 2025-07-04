package jhon.solis.dev.domain.usecases

import jhon.solis.dev.domain.mapper.toDomainList
import jhon.solis.dev.domain.model.Product
import jhon.solis.dev.domain.repository.SearchProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import jhon.solis.dev.domain.util.Result
import kotlinx.coroutines.flow.map

class SearchProductUseCase @Inject constructor(
    private val repository: SearchProductRepository
) {
    suspend fun execute(
        query: String,
        page: Int,
        order: Int? = null
    ): Flow<Result<List<Product>>>{
        return repository.searchProductByKeyword(query, page, order)
            .map { result ->
                when(result){
                    is Result.Success -> Result.Success(result.data.toDomainList())
                    is Result.Error -> Result.Error(result.message, result.code)
                    is Result.Loading -> Result.Loading
                }
            }
    }
}