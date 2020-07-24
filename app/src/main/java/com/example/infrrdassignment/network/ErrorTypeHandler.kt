package com.example.infrrdassignment.network

import com.example.infrrdassignment.network.ErrorTypeHandler.ErrorCode.Companion.JSON_ERROR
import com.example.infrrdassignment.network.ErrorTypeHandler.ErrorCode.Companion.NETWORK_ERROR
import com.example.infrrdassignment.network.ErrorTypeHandler.ErrorCode.Companion.TIMEOUT_ERROR
import com.example.infrrdassignment.network.ErrorTypeHandler.ResponseCode.Companion.SERVER_ERROR
import com.example.infrrdassignment.network.ErrorTypeHandler.ResponseCode.Companion.UNAUTHORIZED


class ErrorTypeHandler {

    interface ResponseCode {
        companion object {
            const val UNAUTHORIZED = 401
            const val UNAUTHORIZED_400 = 400
            const val UNAUTHORIZED_403 = 403
            const val UNAUTHORIZED_404 = 404
            const val ERROR_CONFLICT = 409
            const val UNAUTHORIZED_422 = 422
            const val SERVER_ERROR = 500
        }
    }

    interface ErrorCode {
        companion object {
            val TIMEOUT_ERROR = 1
            val NETWORK_ERROR = 2
            val JSON_ERROR = 3
        }
    }

    interface ErrorCodeMessageMapping {
        companion object {
            val codeMessageMap: HashMap<Int, String> = hashMapOf(
                UNAUTHORIZED to "Failed to get login",
                TIMEOUT_ERROR to "Operation has timed out.",
                NETWORK_ERROR to "Seems like you are not connected to the internet.",
                JSON_ERROR to "Error in data parsing.",
                SERVER_ERROR to "Server is not responding."
            )
        }
    }
}