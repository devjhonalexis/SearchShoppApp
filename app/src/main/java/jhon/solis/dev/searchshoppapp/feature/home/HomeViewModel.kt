package jhon.solis.dev.searchshoppapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jhon.solis.dev.domain.model.Product
import jhon.solis.dev.domain.usecases.SearchProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import jhon.solis.dev.domain.util.Result

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchProductUseCase: SearchProductUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<Result<List<Product>>?>(null)
    val products: StateFlow<Result<List<Product>>?> = _products

    private val _queryProduct = MutableStateFlow(QueryProduct())
    val queryProduct: StateFlow<QueryProduct> = _queryProduct

    fun updateQuery(queryProduct: QueryProduct){
        _queryProduct.value = queryProduct
    }

    fun searchProducts(){
        viewModelScope.launch {
            _products.value = Result.Loading

            val flow = searchProductUseCase.execute(
                query = queryProduct.value.query,
                page = queryProduct.value.page,
                order = queryProduct.value.order
            )

            flow.collect{ result ->
                _products.value = result
            }

        }

    }

    data class QueryProduct(
        val query: String = "",
        val page: Int = 1,
        val order: Int? = null
    )

}