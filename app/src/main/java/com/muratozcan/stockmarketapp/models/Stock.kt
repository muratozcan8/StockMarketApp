package com.muratozcan.stockmarketapp.models

import com.google.gson.annotations.SerializedName

data class Stock(
    @SerializedName("stockId")
    val stockId: Int,
    @SerializedName("stockName")
    val stockName: String,
    @SerializedName("stockShortName")
    val stockShortName: String,
    @SerializedName("stockPrice")
    val stockPrice: Double
)
