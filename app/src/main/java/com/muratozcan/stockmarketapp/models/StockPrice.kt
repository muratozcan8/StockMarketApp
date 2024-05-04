package com.muratozcan.stockmarketapp.models

import com.google.gson.annotations.SerializedName

data class StockPrice(
    @SerializedName("stockId")
    val stockId: Int,
    @SerializedName("stockNameAndPrice")
    val stockNameAndPrice: String,
    @SerializedName("rf")
    val rf: Double,
    @SerializedName("lstm")
    val lstm: Double,
    @SerializedName("prophet")
    val prophet: Double,
    @SerializedName("stockResultSentiment")
    val stockResultSentiment: String,
    @SerializedName("selectPrice")
    val selectedPrice: Double
)
