package com.example.infrrdassignment.repositories

import APIResponse
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.infrrdassignment.MemeApplication
import com.example.infrrdassignment.MostViralFeedModel
import com.example.infrrdassignment.Utility
import com.example.infrrdassignment.network.BaseRepository
import com.example.infrrdassignment.network.CallbackHandler

/**
 * This class is responsible to connect with network for calling api
 */
class MostViralRepo : BaseRepository() {
    var memeResponse = MediatorLiveData<APIResponse<MostViralFeedModel>>()
    var internetState = MutableLiveData<Boolean>()
    var loadingState = MutableLiveData<Boolean>()

    /**
     * This method is used to call Memes Api
     * @param currentPage This is the parameter which contains number of pages for pagination
     */
    fun getMemes(currentPage: Int) {
        if (Utility.isNetworkConnected(MemeApplication.appContext)) {
            internetState.value = false
            return
        }
        loadingState.value = true
        val section = "hot"
        val sort = "viral"
        val window = "day"
        val page = 1
        val showViral = true
        val mature = true
        val album_previews = true
        apiInterface?.getMemeGallery(section, sort, window, currentPage, showViral, mature, album_previews)
            ?.enqueue(object : CallbackHandler<MostViralFeedModel>() {
                override fun onSuccess(t: APIResponse<MostViralFeedModel>?) {
                    sendMemeCallback(t)
                }

                override fun onError(t: APIResponse<MostViralFeedModel>?) {
                    sendMemeCallback(t)
                }

            })
    }

    /**
     * This method is responsible to set live values
     * @param t This is the parameter which contains response of api
     */
    private fun sendMemeCallback(t: APIResponse<MostViralFeedModel>?) {
        loadingState.value = false
        memeResponse.value = t
    }


}