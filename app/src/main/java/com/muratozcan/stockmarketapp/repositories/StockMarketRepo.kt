package com.muratozcan.stockmarketapp.repositories

import com.muratozcan.stockmarketapp.models.BaseModel
import com.muratozcan.stockmarketapp.models.CRUDResponse
import com.muratozcan.stockmarketapp.models.Stock
import com.muratozcan.stockmarketapp.models.StockPrice
import com.muratozcan.stockmarketapp.models.UserDTO
import com.muratozcan.stockmarketapp.models.UserLogin

interface StockMarketRepo {
    suspend fun login(user: UserLogin): BaseModel<CRUDResponse>
    suspend fun register(user: UserDTO): BaseModel<CRUDResponse>
    suspend fun getStock(stockId: Int): BaseModel<Stock>
    suspend fun getAllStocks(): BaseModel<List<Stock>>

    suspend fun getStockPrice1Week(stockId: Int): BaseModel<StockPrice>
    suspend fun getStockPrice1Month(stockId: Int): BaseModel<StockPrice>
    suspend fun getStockPrice3Month(stockId: Int): BaseModel<StockPrice>
    suspend fun getStockPrice6Month(stockId: Int): BaseModel<StockPrice>
}