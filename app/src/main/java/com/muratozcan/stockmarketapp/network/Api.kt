package com.muratozcan.stockmarketapp.network

import com.muratozcan.stockmarketapp.models.Login
import com.muratozcan.stockmarketapp.models.SignUp
import com.muratozcan.stockmarketapp.models.Stock
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @POST("api/users/login")
    suspend fun login(
        @Query("q") query: String
    ): Response<Login>

    @POST("api/users/register")
    suspend fun register(
        @Query("q") query: String
    ): Response<SignUp>

    @GET("api/stocks/{id}")
    suspend fun getStock(
        @Path("id") stockId: Int
    ): Response<Stock>

    @GET("api/stocks")
    suspend fun getAllStocks(): Response<List<Stock>>

    @GET("api/Images/GetImage/{FilePath}.png")
    suspend fun getStockImage(
        @Path("FilePath") filePath: String
    )
}