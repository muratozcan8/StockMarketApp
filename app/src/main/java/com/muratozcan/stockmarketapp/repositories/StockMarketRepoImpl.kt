package com.muratozcan.stockmarketapp.repositories

import com.muratozcan.stockmarketapp.models.BaseModel
import com.muratozcan.stockmarketapp.models.CRUDResponse
import com.muratozcan.stockmarketapp.models.Stock
import com.muratozcan.stockmarketapp.models.StockPrice
import com.muratozcan.stockmarketapp.models.UserDTO
import com.muratozcan.stockmarketapp.models.UserLogin
import com.muratozcan.stockmarketapp.network.Api
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body

class StockMarketRepoImpl(private val api: Api): StockMarketRepo {
    override suspend fun login(user: UserLogin): BaseModel<CRUDResponse> {
        return request {
            api.login(user)
        }
    }

    override suspend fun register(user: UserDTO): BaseModel<CRUDResponse> {
        return request {
            api.register(user = user)
        }
    }

    override suspend fun getStock(stockId: Int): BaseModel<Stock> {
        return request {
            api.getStock(stockId = stockId)
        }
    }

    override suspend fun getAllStocks(): BaseModel<List<Stock>> {
        return request {
            api.getAllStocks()
        }
    }

    override suspend fun getStockPrice1Week(stockId: Int): BaseModel<StockPrice> {
        return request {
            api.getPrediction1Week(stockId)
        }
    }

    override suspend fun getStockPrice1Month(stockId: Int): BaseModel<StockPrice> {
        return request {
            api.getPrediction1Month(stockId)
        }
    }

    override suspend fun getStockPrice3Month(stockId: Int): BaseModel<StockPrice> {
        return request {
            api.getPrediction3Month(stockId)
        }
    }

    override suspend fun getStockPrice6Month(stockId: Int): BaseModel<StockPrice> {
        return request {
            api.getPrediction6Month(stockId)
        }
    }


}

suspend fun<T> request(request: suspend ()-> Response<T>):BaseModel<T>{
    try {
        request().also {
            return if (it.isSuccessful){
                BaseModel.Success(it.body()!!)
            } else {
                BaseModel.Error(it.errorBody()?.string().toString())
            }
        }
    } catch (e:Exception){
        return BaseModel.Error(e.message.toString())
    }
}