package com.muratozcan.stockmarketapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozcan.stockmarketapp.models.BaseModel
import com.muratozcan.stockmarketapp.models.Stock
import com.muratozcan.stockmarketapp.repositories.StockMarketRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StockViewModel: ViewModel(), KoinComponent {
    private val repo: StockMarketRepo by inject()

    private val _stocks:MutableStateFlow<BaseModel<List<Stock>>?> = MutableStateFlow(null)
    val stocks = _stocks.asStateFlow()

    fun getAllStocks() {
        viewModelScope.launch {
            _stocks.update { BaseModel.Loading }
            repo.getAllStocks().also { res ->
                _stocks.update { res }
            }
        }
    }
}