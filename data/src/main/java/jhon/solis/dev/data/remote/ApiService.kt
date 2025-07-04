package jhon.solis.dev.data.remote

import jhon.solis.dev.domain.dto.ProductResponseDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface ApiService {
    @GET("appclienteservices/services/v3/plp")
    suspend fun searchShoppAppProduct(
        @Query("search-string") query: String,
        @Query("page-number") page: Int,
        @Query("minSortPrice") order: Int? = null
    ): Response<ProductResponseDto>
}