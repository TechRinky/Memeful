import com.example.infrrdassignment.network.APIErrorType
import okhttp3.ResponseBody

open class APIResponse<T> {

    var response: T? = null
    var error: APIErrorType? = null
    var errorBody: ResponseBody? = null

    fun hasError(): Boolean {
        return response == null
    }
}