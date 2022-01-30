package com.example.moviesdemo.network

import com.example.moviesdemo.models.DetailPageModel
import com.example.moviesdemo.models.SearchedResultModel
import com.example.tentwentyassignment.models.UpcomingMoviesModel
import retrofit2.Response
import retrofit2.http.*

interface BackEndApi {

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") key : String): Response<UpcomingMoviesModel>

    @GET("3/movie/{keyword}")
    suspend fun getDetailPage(@Path("keyword") keyword: Int, @Query("api_key") key : String): Response<DetailPageModel>

//    https://api.themoviedb.org/3/search/movie?api_key=83d01f18538cb7a275147492f84c3698&query=time

    @GET("3/search/movie")
    suspend fun getSearchedMovies(@Query("api_key") key : String,
                                  @Query("query") query : String, ): Response<SearchedResultModel>


}

