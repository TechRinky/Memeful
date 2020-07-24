package com.example.infrrdassignment.network

import com.example.infrrdassignment.constants.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * This class is responsible to initialize the Retrofit
 */
class APIHandler {

    companion object {

        private var mApiInterface: ApInterface? = null

        fun getApiInterface(): ApInterface? {
            if (mApiInterface == null) {
                val okHttpClient = OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build()

                val retrofitBuilder = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                mApiInterface = retrofitBuilder.create(ApInterface::class.java)
            }
            return mApiInterface
        }
    }
}