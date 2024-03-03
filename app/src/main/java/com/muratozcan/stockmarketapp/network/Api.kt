package com.muratozcan.stockmarketapp.network

import com.muratozcan.stockmarketapp.models.CRUDResponse
import com.muratozcan.stockmarketapp.models.Stock
import com.muratozcan.stockmarketapp.models.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @POST("api/users/login")
    suspend fun login(
        @Body query: String
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

    @GET("api/Images/GetImage/{FilePath}.png")
    suspend fun getStockImage(
        @Path("FilePath") filePath: String
    )
}