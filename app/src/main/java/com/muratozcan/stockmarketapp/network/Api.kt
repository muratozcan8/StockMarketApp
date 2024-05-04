package com.muratozcan.stockmarketapp.network

import com.muratozcan.stockmarketapp.models.CRUDResponse
import com.muratozcan.stockmarketapp.models.Stock
import com.muratozcan.stockmarketapp.models.StockPrice
import com.muratozcan.stockmarketapp.models.UserDTO
import com.muratozcan.stockmarketapp.models.UserLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @POST("api/users/login")
    suspend fun login(
        @Body user: UserLogin
    ): Response<CRUDResponse>

    @POST("api/users/register")
    suspend fun register(
        @Body user: UserDTO
    ): Response<CRUDResponse>

    @GET("api/stocks/{id}")
    suspend fun getStock(
        @Path("id") stockId: Int
    ): Response<Stock>

    @GET("api/stocks")
    suspend fun getAllStocks(): Response<List<Stock>>

    @GET("api/prediction1week/{id}")
    suspend fun getPrediction1Week(
        @Path("id") stockId: Int
    ): Response<StockPrice>

    @GET("api/prediction1month/{id}")
    suspend fun getPrediction1Month(
        @Path("id") stockId: Int
    ): Response<StockPrice>

    @GET("api/prediction3month/{id}")
    suspend fun getPrediction3Month(
        @Path("id") stockId: Int
    ): Response<StockPrice>

    @GET("api/prediction6month/{id}")
    suspend fun getPrediction6Month(
        @Path("id") stockId: Int
    ): Response<StockPrice>
}