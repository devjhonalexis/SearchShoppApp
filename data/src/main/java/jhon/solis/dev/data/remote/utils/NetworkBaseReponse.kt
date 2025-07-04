package jhon.solis.dev.data.remote.utils

import android.util.Log
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import jhon.solis.dev.domain.util.Result

class BaseNetworkResponse @Inject constructor(){
    private  val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun<ResultType> callService(api : suspend () -> Response<ResultType>): Flow<Result<ResultType>> {
        return withContext(ioDispatcher) {
            flow {
                emit(Result.Loading)
                val response: Response<ResultType> = api()

                if (response.isSuccessful){
                    response.body()?.let {
                        emit(Result.Success(data = it))
                    }?: emit(Result.Error(message = "Unknown error occurred", code = 0))
                }else{
                    emit(Result.Error(message = parserErrorBody(response.errorBody()), code = response.code()))
                }

            }.catch { error->
                Log.e("ERROR", "ERROR -> ${error.localizedMessage}")
                emit(Result.Error(message = message(error), code = code(error)))
            }
        }
    }

    private fun parserErrorBody(response: ResponseBody?):String{
        return response?.let {
            val errorMessage = JsonParser.parseString(it.string()).asJsonObject["message"].asString
            errorMessage.ifEmpty { "Something went wrong. Please try again." }
            errorMessage
        }?:"Unknown error occurred. Please try again"
    }
    private fun message(throwable: Throwable?):String{
        when (throwable) {
            is SocketTimeoutException -> return "Connection time out. Please try again"
            is IOException -> return "No Internet Connection. Please try again"
            is HttpException -> return try {
                val errorJsonString = throwable.response()?.errorBody()?.string()
                val errorMessage = JsonParser.parseString(errorJsonString).asJsonObject["message"].asString
                errorMessage.ifEmpty { "Something went wrong. Please try again." }
            }catch (e:Exception){
                "Unknown error occurred. Please try again"
            }
        }
        return "Unknown error occurred. Please try again"
    }
    private fun code(throwable: Throwable?):Int{
        return if (throwable is HttpException) (throwable).code()
        else  0
    }
}