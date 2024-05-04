package com.muratozcan.stockmarketapp.screens

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.muratozcan.stockmarketapp.models.BaseModel
import com.muratozcan.stockmarketapp.models.Stock
import kotlin.math.roundToInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.navArgument
import kotlinx.coroutines.delay

@Composable
fun StockPage(navController: NavController, viewModel: StockViewModel = viewModel()){

    LaunchedEffect(Unit) {
        viewModel.getAllStocks()
    }
    val stocks by viewModel.stocks.collectAsState()

    Column(modifier = Modifier
        .padding(horizontal = 12.dp)
    ) {
        when(val result = stocks) {
            is BaseModel.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            is BaseModel.Success -> {
                Text(
                    modifier = Modifier.padding(top = 18.dp),
                    text = "BIST 30",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier=Modifier.height(8.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)){
                    items(result.data){
                        val stock = it
                        Crypto(stock = stock, navController = navController)
                    }
                }
            }

            is BaseModel.Error -> {
                Text(result.error)
                Log.e("Error", result.error)
            }

            null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.Black)
                }
                Log.e("Null", "null")
            }
        }
    }
}

@Composable
fun Crypto(stock: Stock, horizontal: Boolean = false, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(8.dp)
            .clip(
                RoundedCornerShape(8.dp)
            )
            .clickable {
                navController.navigate("stock_detail_page/${stock.stockId}/${stock.stockName}"){
                }
            }
            .background(MaterialTheme.colorScheme.outline)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(55.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(MaterialTheme.colorScheme.outline),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("http://10.0.2.2:8080/api/Images/GetImage/${stock.stockShortName}.png")
                        .crossfade(true)
                        .build(),
                    contentDescription = null
                )
            }

            Column {
                Text(stock.stockName, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.White)
                Text(stock.stockShortName, fontSize = 14.sp, color = Color.Gray)
            }
        }
        if (horizontal) {
            Spacer(modifier = Modifier.width(10.dp))
        }
        Column(horizontalAlignment = Alignment.End) {
            Text("${((stock.stockPrice * 100).roundToInt()) / 100.0} " +
                    "₺", color = Color.White, fontSize = 20.sp)
        }

    }
}