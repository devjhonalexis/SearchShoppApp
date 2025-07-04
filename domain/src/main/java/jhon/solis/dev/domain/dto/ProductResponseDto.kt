package jhon.solis.dev.domain.dto

import jhon.solis.dev.domain.util.BaseResponse

class ProductResponseDto: BaseResponse<RecordsDto>()

data class RecordsDto(
    val records: List<ProductDto>
)