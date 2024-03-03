package com.muratozcan.stockmarketapp.repositories

import com.muratozcan.stockmarketapp.models.BaseModel
import com.muratozcan.stockmarketapp.models.CRUDResponse
import com.muratozcan.stockmarketapp.models.Stock
import com.muratozcan.stockmarketapp.models.UserDTO

interface StockMarketRepo {
    suspend fun login(query: String): BaseModel<CRUDResponse>
    suspend fun register(user: UserDTO): BaseModel<CRUDResponse>
    suspend fun getStock(stockId: Int): BaseModel<Stock>
    suspend fun getAllStocks(): BaseModel<List<Stock>>
}