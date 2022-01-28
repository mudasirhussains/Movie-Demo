package com.example.moviesdemo.network

import com.example.moviesdemo.models.DetailPageModel
import com.example.tentwentyassignment.models.UpcomingMoviesModel
import retrofit2.Response
import retrofit2.http.*

interface BackEndApi {

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") key : String): Response<UpcomingMoviesModel>

    @GET("3/movie/{keyword}")
    suspend fun getDetailPage(@Path("keyword") keyword: Int, @Query("api_key") key : String): Response<DetailPageModel>

}

