package com.karleinstein.sample.model

import com.google.gson.annotations.SerializedName

data class MoviePopularResponse(
    @SerializedName("page")
    var page: Int = 1,
    @SerializedName("total_results")
    var totalResult: Int = 1,
    @SerializedName("total_pages")
    var totalPages: Int = 1,
    @SerializedName("results")
    var results: MutableList<MovieResult> = mutableListOf()
)

data class MovieResult(
    @SerializedName("vote_count")
    var voteCount: Int = 1,
    @SerializedName("id")
    var idMovie: Int = 1,
    @SerializedName("video")
    var video: Boolean = false,
    @SerializedName("vote_average")
    var voteAverage: Double = 1.0,
    @SerializedName("title")
    var titleMovie: String = "title",
    @SerializedName("popularity")
    var popularity: Double = 1.0,
    @SerializedName("poster_path")
    var posterPath: String = "default",
    @SerializedName("original_title")
    var originalTitle: String = "title",
    @SerializedName("backdrop_path")
    var backDropPath: String = "default",
    @SerializedName("overview")
    var overView: String = "default",
    @SerializedName("release_date")
    var releaseDate: String? = null,
    @SerializedName("original_name")
    var nameMovie: String? = null,
    val imageSize: String = "w185",
    val imageSizeBlur: String = "original",
    var isFavorite: Boolean? = false
)