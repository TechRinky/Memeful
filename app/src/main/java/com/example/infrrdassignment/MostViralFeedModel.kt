package com.example.infrrdassignment

class MostViralFeedModel(){
    var data  : List<Data> =  ArrayList<Data>()
    var status: Int = 0 
    var success: Boolean = false

    class Data{
        var images: List<Image> ? =null
        var images_count: Int = 0
        var in_most_viral: Boolean = false
        var points: Int = 0
        var title: String  = ""
        var type: String = ""
    }


    class Image{
        var description: String = ""
        var height: Int = 0
        var link: String = ""
        var points: Long = 0
        var type: String = ""
        var width: Int = 0
    }
}




