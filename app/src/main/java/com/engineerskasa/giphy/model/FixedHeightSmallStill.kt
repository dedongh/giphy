package com.engineerskasa.giphy.model


import com.google.gson.annotations.SerializedName

data class FixedHeightSmallStill(
    @SerializedName("url")
    val url: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("width")
    val width: String
)