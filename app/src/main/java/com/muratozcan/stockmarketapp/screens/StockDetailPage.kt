package com.muratozcan.stockmarketapp.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import com.muratozcan.stockmarketapp.models.BaseModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockDetailPage(
    navController: NavController,
    viewModel: StockDetailViewModel,
    stockId: Int,
    stockName: String,
    stockPrice: Float
) {

    LaunchedEffect(Unit) {
        viewModel.getPrice1Week(stockId)
        viewModel.getPrice1Month(stockId)
        viewModel.getPrice3Month(stockId)
        viewModel.getPrice6Month(stockId)
    }
    val price1week by viewModel.price1week.collectAsState()
    val price1month by viewModel.price1month.collectAsState()
    val price3month by viewModel.price3month.collectAsState()
    val price6month by viewModel.price6month.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.onBackground,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
            .verticalScroll(enabled = true, state = rememberScrollState())
            .padding(25.dp)
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.onBackground.copy(0.2f),
                titleContentColor = Color.White
            ),
            title = {
                Text(text = stockName)
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("stock_page")
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        AnimatedVisibility(visible = price1week is BaseModel.Success
                    && price1month is BaseModel.Success
                    && price3month is BaseModel.Success
                    && price6month is BaseModel.Success,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            val data1week = price1week as BaseModel.Success
            val data1month = price1month as BaseModel.Success
            val data3month = price3month as BaseModel.Success
            val data6month = price6month as BaseModel.Success

            val prices1week = data1week.data.selectedPrice
            val prices1month = data1month.data.selectedPrice
            val prices3month = data3month.data.selectedPrice
            val prices6month = data6month.data.selectedPrice

            val prices = mutableListOf(prices1week, prices1month, prices3month, prices6month)
            val maxPrice = prices.maxOf {
                it
            }

            LazyRow(
                modifier = Modifier.height(270.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                items(prices) { item ->
                    SingleBar(price = item, maxPrice)
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }
        }
        AnimatedVisibility(visible = price1week is BaseModel.Loading
                && price1month is BaseModel.Loading
                && price3month is BaseModel.Loading
                && price6month is BaseModel.Loading) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        AnimatedVisibility(visible = price1week is BaseModel.Success) {
            val data1week = price1week as BaseModel.Success
            val prices1week = data1week.data.selectedPrice
            Log.e("Stock", data1week.data.stockNameAndPrice + " " + stockPrice)

            if (stockPrice != null) {
                ProfileInfoCard(
                    heading = "Kasım",
                    subHeading = "Total Sales Today",
                    valueText = prices1week.toString(),
                    percentage = (((prices1week - stockPrice) / stockPrice) * 100).toInt(),
                    color = when (data1week.data.stockResultSentiment) {
                        "Low" -> Color.Red
                        "Medium" -> Color.Yellow
                        else -> Color.Green },
                    isIncreasing = stockPrice < prices1week
                )
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        AnimatedVisibility(visible = price1month is BaseModel.Success) {
            val data1month = price1month as BaseModel.Success
            val prices1month = data1month.data.selectedPrice

            if (stockPrice != null) {
                ProfileInfoCard(
                    heading = "Aralık",
                    subHeading = "Total Sales Today",
                    valueText = prices1month.toString(),
                    percentage = (((prices1month - stockPrice) / stockPrice) * 100).toInt(),
                    color = when (data1month.data.stockResultSentiment) {
                        "Low" -> Color.Red
                        "Medium" -> Color.Yellow
                        else -> Color.Green },
                    isIncreasing = stockPrice < prices1month
                )
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        AnimatedVisibility(visible = price3month is BaseModel.Success) {
            val data3month = price3month as BaseModel.Success
            val prices3month = data3month.data.selectedPrice

            if (stockPrice != null) {
                ProfileInfoCard(
                    heading = "Şubat",
                    subHeading = "Total Sales Today",
                    valueText = prices3month.toString(),
                    percentage = (((prices3month - stockPrice) / stockPrice) * 100).toInt(),
                    color = when (data3month.data.stockResultSentiment) {
                        "Low" -> Color.Red
                        "Medium" -> Color.Yellow
                        else -> Color.Green },
                    isIncreasing = stockPrice < prices3month
                )
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        AnimatedVisibility(visible = price6month is BaseModel.Success) {
            val data6month = price6month as BaseModel.Success
            val prices6month = data6month.data.selectedPrice

            if (stockPrice != null) {
                ProfileInfoCard(
                    heading = "Mayıs",
                    subHeading = "Total Sales Today",
                    valueText = prices6month.toString(),
                    percentage = (((prices6month - stockPrice) / stockPrice) * 100).toInt(),
                    color = when (data6month.data.stockResultSentiment) {
                        "Low" -> Color.Red
                        "Medium" -> Color.Yellow
                        else -> Color.Green },
                    isIncreasing = stockPrice < prices6month
                )
            }
        }
    }
    }

@Composable
fun ProfileInfoCard(
    heading: String,
    subHeading: String,
    valueText: String,
    percentage: Int,
    color: Color,
    isIncreasing: Boolean
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .fillMaxWidth()
            .height(140.dp)
            .background(Color.White.copy(0.3f))
            .padding(
                start = 30.dp,
                top = 10.dp,
                end = 10.dp,
                bottom = 10.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(7f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = heading,
                    fontSize = 24.sp,
                    color = Color.White
                    //fontFamily = FontFamily(Font())
                )
                Text(
                    text = subHeading,
                    fontSize = 12.sp,
                    color = Color.LightGray
                    //fontFamily = FontFamily(Font())
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "${valueText}₺",
                    fontSize = 30.sp,
                    color = color
                    //fontFamily = FontFamily(Font())
                )
            }
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Arrow",
                    tint = color,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .rotate(if (isIncreasing) -45f else 45f)
                )
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "${percentage}%",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                    //fontFamily = FontFamily(Font())
                )
                Canvas(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(70.dp)
                ) {
                    drawArc(
                        color = color,
                        startAngle = -120f,
                        sweepAngle = 360 * percentage / 100f,
                        useCenter = false,
                        style = Stroke(
                            width = 10f,
                            cap = StrokeCap.Round
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun SingleBar(price: Double, maxPrice: Double) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${price}₺",
            color = Color.White,
            fontSize = 14.sp
            //fontFamily = FontFamily(Font())
        )
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp
                    )
                )
                .background(Color.LightGray)
                .width(72.dp)
                .height(
                    (200 * price / maxPrice).dp
                )
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Kas",
            color = Color.White,
            fontSize = 14.sp
            //fontFamily = FontFamily(Font())
        )
    }
}


