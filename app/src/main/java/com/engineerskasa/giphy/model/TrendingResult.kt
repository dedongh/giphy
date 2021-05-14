package com.engineerskasa.giphy.model


import com.google.gson.annotations.SerializedName

data class TrendingResult(
    @SerializedName("data")
    val data : List<Data>
)