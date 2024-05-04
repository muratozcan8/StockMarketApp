package com.muratozcan.stockmarketapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozcan.stockmarketapp.models.BaseModel
import com.muratozcan.stockmarketapp.models.StockPrice
import com.muratozcan.stockmarketapp.repositories.StockMarketRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StockDetailViewModel: ViewModel(), KoinComponent {
    private val repo: StockMarketRepo by inject()

    private val _price1week:MutableStateFlow<BaseModel<StockPrice>?> = MutableStateFlow(null)
    private val _price1month:MutableStateFlow<BaseModel<StockPrice>?> = MutableStateFlow(null)
    private val _price3month:MutableStateFlow<BaseModel<StockPrice>?> = MutableStateFlow(null)
    private val _price6month:MutableStateFlow<BaseModel<StockPrice>?> = MutableStateFlow(null)


    val price1week = _price1week.asStateFlow()
    val price1month = _price1month.asStateFlow()
    val price3month = _price3month.asStateFlow()
    val price6month = _price6month.asStateFlow()

    fun getPrice1Week(stockId: Int) {
        viewModelScope.launch {
            repo.getStockPrice1Week(stockId).also { res ->
                _price1week.update { res }
            }
        }
    }

    fun getPrice1Month(stockId: Int) {
        viewModelScope.launch {
            repo.getStockPrice1Month(stockId).also { res ->
                _price1month.update { res }
            }
        }
    }

    fun getPrice3Month(stockId: Int) {
        viewModelScope.launch {
            repo.getStockPrice3Month(stockId).also { res ->
                _price3month.update { res }
            }
        }
    }

    fun getPrice6Month(stockId: Int) {
        viewModelScope.launch {
            repo.getStockPrice6Month(stockId).also { res ->
                _price6month.update { res }
            }
        }
    }

}