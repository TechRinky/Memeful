package com.example.infrrdassignment.network

open class BaseRepository {

    protected var apiInterface: ApInterface? = null

    init {
        apiInterface =  APIHandler.getApiInterface()
    }
}