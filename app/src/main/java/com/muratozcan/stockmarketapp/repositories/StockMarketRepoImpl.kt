package com.muratozcan.stockmarketapp.repositories

import com.muratozcan.stockmarketapp.models.BaseModel
import com.muratozcan.stockmarketapp.models.CRUDResponse
import com.muratozcan.stockmarketapp.models.Stock
import com.muratozcan.stockmarketapp.models.UserDTO
import com.muratozcan.stockmarketapp.network.Api
import retrofit2.Response

class StockMarketRepoImpl(private val api: Api): StockMarketRepo {
    override suspend fun login(query: String): BaseModel<CRUDResponse> {
        return request {
            api.login(query = query)
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