package com.example.infrrdassignment.network

import APIResponse
import com.google.gson.JsonSyntaxException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLException

/**
 * This abstract class is responsible to handle the callbacks of api
 */
abstract class CallbackHandler<T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val apiResponse = APIResponse<T>()
        if (response.isSuccessful) {
            apiResponse.response = response.body()
            onSuccess(apiResponse)
        }
        else if(response.code() == ErrorTypeHandler.ResponseCode.UNAUTHORIZED_422) {
            apiResponse.errorBody = response.errorBody()
            val apiError = APIErrorType()
            apiError.code = ErrorTypeHandler.ResponseCode.UNAUTHORIZED_422
            apiResponse.error = apiError
            onSuccess(apiResponse)
        }
        else {
            val error = getError(response)
            apiResponse.error = error
            onError(apiResponse)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        val error = APIErrorType()
        if (t is UnknownHostException || t is SocketException || t is ConnectException || t is SSLException) {
            error.code = ErrorTypeHandler.ErrorCode.NETWORK_ERROR
        }
        else if (t is SocketTimeoutException || t is TimeoutException) {
            error.code = ErrorTypeHandler.ErrorCode.TIMEOUT_ERROR
        }
        else if (t is JsonSyntaxException) {
            error.code = ErrorTypeHandler.ErrorCode.JSON_ERROR
        }
        val msg = ErrorTypeHandler.ErrorCodeMessageMapping.codeMessageMap[error.code]
        error.message = if (msg == null) "" else msg
        val apiResponse = APIResponse<T>()
        apiResponse.error = error
        onError(apiResponse)
    }

    abstract fun onSuccess(t: APIResponse<T>?)
    abstract fun onError(t: APIResponse<T>?)

    private fun getError(response: Response<T>): APIErrorType {
        val apiError = APIErrorType()
        apiError.code = response.code()
        try {
            val errorBody = response.errorBody()!!.string()
            val jsonObject = JSONObject(errorBody)
            val jsonObject1 = jsonObject.optJSONObject("data")
            apiError.message = jsonObject1.optString("error")
        }
        catch (e: JSONException) {
            e.printStackTrace()
            apiError.message = response.message()
        }
        catch (e: IOException) {
            e.printStackTrace()
            apiError.message = response.message()
        }
        catch (e: Exception) {
            e.printStackTrace()
            apiError.message = response.message()
        }
        return apiError
    }
}