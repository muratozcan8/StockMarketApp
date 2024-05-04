package com.muratozcan.stockmarketapp

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muratozcan.stockmarketapp.screens.LoginPage
import com.muratozcan.stockmarketapp.screens.LoginViewModel
import com.muratozcan.stockmarketapp.screens.RegisterPage
import com.muratozcan.stockmarketapp.screens.RegisterViewModel
import com.muratozcan.stockmarketapp.screens.StockDetailPage
import com.muratozcan.stockmarketapp.screens.StockDetailViewModel
import com.muratozcan.stockmarketapp.screens.StockPage
import com.muratozcan.stockmarketapp.screens.StockViewModel
import com.muratozcan.stockmarketapp.ui.theme.StockMarketAppTheme

class MainActivity : ComponentActivity() {

    private val stockViewModel: StockViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val stockDetailViewModel: StockDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            StockMarketAppTheme {
                LoginApplication()
            }
        }
    }

    @Composable
    fun LoginApplication(){
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "login_page", builder = {
            composable("login_page", content = { LoginPage(navController = navController, viewModel = loginViewModel) })
            composable("register_page", content = { RegisterPage(navController = navController, viewModel = registerViewModel) })
            composable("stock_page", content = { StockPage(navController = navController, viewModel = stockViewModel) })
            composable(
                "stock_detail_page/{stockId}/{stockName}",
                arguments = listOf(
                    navArgument("stockId") {
                        type = NavType.IntType
                    },
                    navArgument("stockName") {
                        type = NavType.StringType
                    }
                )
            ) {
                StockDetailPage(
                    navController = navController,
                    viewModel = stockDetailViewModel,
                    stockId = it.arguments?.getInt("stockId") ?: 1,
                    stockName = it.arguments?.getString("stockName") ?: ""
                )
            }
        })
    }
}

