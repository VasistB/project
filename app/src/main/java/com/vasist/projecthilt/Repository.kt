package com.vasist.projecthilt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vasist.projecthilt.model.ApiData
import com.vasist.projecthilt.network.NetworkService
import retrofit2.Retrofit

class Repository(private val quoteService: NetworkService.ResultDataService) {

    private val quotesLiveData = MutableLiveData<ApiData>()
    val quotes: LiveData<ApiData>
        get() = quotesLiveData

    suspend fun getQuotes(){
        val userResults = quoteService.getUsersList()
        if ( userResults.body()!=null){
            quotesLiveData.postValue(userResults.body())

        }
    }
}