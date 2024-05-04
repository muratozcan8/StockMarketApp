package com.muratozcan.stockmarketapp.screens

import androidx.lifecycle.ViewModel
import com.muratozcan.stockmarketapp.repositories.StockMarketRepo
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StockDetailViewModel: ViewModel(), KoinComponent {
    private val repo: StockMarketRepo by inject()


}