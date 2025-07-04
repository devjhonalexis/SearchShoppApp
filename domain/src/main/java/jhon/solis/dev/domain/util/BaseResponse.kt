package jhon.solis.dev.domain.util

import com.google.gson.annotations.SerializedName

open class BaseResponse<L> {
    @SerializedName("status") var responseStatus: StatusResponse = StatusResponse()
    @SerializedName("plpResults") var result: L? = null
}

data class StatusResponse(
    val status: String? = null,
    val statusCode: Int? = null,
)