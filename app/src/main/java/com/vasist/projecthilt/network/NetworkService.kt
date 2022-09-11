package com.vasist.projecthilt.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vasist.projecthilt.model.ApiData
import com.vasist.projecthilt.model.Result
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.time.Duration
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkService {
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://randomuser.me/api/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    interface ResultDataService {
        @GET("?results=50")
        suspend fun getUsersList(): Response<ApiData>
    }
    @Provides
    @Singleton
    fun providesOkHttpClint(): OkHttpClient {
        val duration = Duration.ofSeconds(1)
        return OkHttpClient.Builder()
            .connectTimeout(duration)
            .readTimeout(duration)
            .writeTimeout(duration)
            .build()
    }
    @Provides
    @Singleton
    fun providesResultJsonService(retrofit: Retrofit):ResultDataService{
        return retrofit.create(ResultDataService::class.java)
    }
}