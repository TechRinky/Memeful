package com.example.infrrdassignment.viewmodels

import android.text.TextUtils
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.infrrdassignment.MemesModel
import com.example.infrrdassignment.MostViralFeedModel
import com.example.infrrdassignment.TypeOfMeme
import com.example.infrrdassignment.repositories.MostViralRepo

/**
 * This class is responsible to communicate with the repository class to call api .
 * In this class bussiness logic is implemented.
 * contains live data which will be observed by View(Activity/Fragments)
 */
class MostViralMemeViewModel : ViewModel() {

    private  var mostViralMemeRepo = MostViralRepo()
    var mostViralResponse = mostViralMemeRepo.memeResponse
    var loadingState = mostViralMemeRepo.loadingState
    var internetState = mostViralMemeRepo.internetState
    var memeFilteredListLiveData = MediatorLiveData<ArrayList<MemesModel>>()

    /**
     * This method is responsible to call memes api from repository
     */
    fun getMemes(currentPage: Int) {
        mostViralMemeRepo.getMemes(currentPage)
    }

    /**
     * This method is responsible to filter images
     * It will fetch top image from image array if it will match to jpeg or png and then loop will break
     * as we are showing one image from each object of image list
     * @param mostViralMemeList This paramtere contains the list of all images which is to be shown in recylerview
     */
    fun calculateImagesToShow(mostViralMemeList: ArrayList<MostViralFeedModel.Data>){
        val memeFilteredList = ArrayList<MemesModel>()
        for(item in mostViralMemeList){
            if(item.images!=null && item.images?.size!! > 0){
                var isValueSet = false
                for (imagesOfMeme in item.images!!){
                    if (!TextUtils.isEmpty(imagesOfMeme.type) && (imagesOfMeme.type.equals(TypeOfMeme.IMAGE_JPEG.memeType) || imagesOfMeme.type.equals(
                            TypeOfMeme.IMAGE_PNG.memeType)) && !TextUtils.isEmpty(imagesOfMeme.link)){
                        val memesModel = MemesModel()
                        if(!TextUtils.isEmpty(imagesOfMeme.description))
                        memesModel.description = imagesOfMeme.description
                        memesModel.points = imagesOfMeme.points
                        memesModel.link = imagesOfMeme.link
                        memesModel.height = imagesOfMeme.height
                        memesModel.width = imagesOfMeme.width
                        memeFilteredList.add(memesModel)
                        isValueSet = true
                    }
                    if(isValueSet){
                        break
                    }
                }

            }
        }
        memeFilteredListLiveData.value = memeFilteredList
    }

}