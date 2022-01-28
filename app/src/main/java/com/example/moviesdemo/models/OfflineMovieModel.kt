package com.example.tentwentyassignment.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class OfflineMovieModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "adult")
    var adult: Boolean,
    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String,
    @ColumnInfo(name = "original_language")
    var originalLanguage: String,
    @ColumnInfo(name = "original_title")
    var originalTitle: String,
    @ColumnInfo(name = "overview")
    var overview: String,
    @ColumnInfo(name = "popularity")
    var popularity: Double,
    @ColumnInfo(name = "poster_path")
    var posterPath: String,
    @ColumnInfo(name = "release_date")
    var releaseDate: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "video")
    var video: Boolean,
    @ColumnInfo(name = "vote_average")
    var voteAverage: Double,
    @ColumnInfo(name = "vote_count")
    var voteCount: Int

){
    @Ignore
    var genreIds: List<Int> = listOf()
}
