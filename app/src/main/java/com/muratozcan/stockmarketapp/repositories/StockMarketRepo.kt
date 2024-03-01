package com.muratozcan.stockmarketapp.repositories

import com.muratozcan.stockmarketapp.models.BaseModel
import com.muratozcan.stockmarketapp.models.Login
import com.muratozcan.stockmarketapp.models.SignUp
import com.muratozcan.stockmarketapp.models.Stock

interface StockMarketRepo {
    suspend fun login(query: String): BaseModel<Login>
    suspend fun register(query: String): BaseModel<SignUp>
    suspend fun getStock(stockId: Int): BaseModel<Stock>
    suspend fun getAllStocks(): BaseModel<List<Stock>>
}