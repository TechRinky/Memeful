package com.example.infrrdassignment.network

import com.example.infrrdassignment.MostViralFeedModel
import com.example.infrrdassignment.constants.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * This interface contains all the api with request parameters
 */
interface ApInterface {
    @GET(ApiEndPoint.MEME_GALLERY)
    @Headers("Authorization: Client-ID 1ec739192f5385c")
    fun getMemeGallery(@Path("section")section : String,
                       @Path("sort")sort : String,
                       @Path("window")window : String,
                       @Path("page")page : Int,
                       @Query("showViral") showViral : Boolean,
                       @Query("mature") mature : Boolean,
                       @Query("album_previews") album_previews : Boolean): Call<MostViralFeedModel>
}