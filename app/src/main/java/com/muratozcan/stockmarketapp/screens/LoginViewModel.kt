package com.muratozcan.stockmarketapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozcan.stockmarketapp.models.BaseModel
import com.muratozcan.stockmarketapp.models.CRUDResponse
import com.muratozcan.stockmarketapp.models.UserDTO
import com.muratozcan.stockmarketapp.models.UserLogin
import com.muratozcan.stockmarketapp.repositories.StockMarketRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel: ViewModel(), KoinComponent {
    private val repo: StockMarketRepo by inject()

    private val _user: MutableStateFlow<BaseModel<CRUDResponse>?> = MutableStateFlow(null)
    val user = _user.asStateFlow()

    fun login(user: UserLogin) {
        viewModelScope.launch {
            _user.update { BaseModel.Loading }
            repo.login(user).also { res ->
                _user.update { res }
            }
        }
    }
}