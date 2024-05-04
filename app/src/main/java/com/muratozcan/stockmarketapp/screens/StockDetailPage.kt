package com.muratozcan.stockmarketapp.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.IconButton
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StockDetailPage() {
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
            .padding(25.dp)
    ) {
        //Top Bar
        LazyRow(
            modifier = Modifier.height(270.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            items(year2022Sales) {item ->
                SingleBar(
                    monthlySalesData = item
                )
                Spacer(modifier = Modifier.width(15.dp))
            }
        }
        Spacer(modifier = Modifier.height(60.dp))
        ProfileInfoCard(
            heading = "Sales",
            subHeading = "Total Sales Today",
            valueText = "500",
            percentage = 55,
            color = Color.Yellow,
            isIncreasing = true
        )
        Spacer(modifier = Modifier.height(25.dp))
        ProfileInfoCard(
            heading = "Profit",
            subHeading = "Per day ratio",
            valueText = "150",
            percentage = 30,
            color = Color.Green,
            isIncreasing = false
        )
        Spacer(modifier = Modifier.height(25.dp))
        ProfileInfoCard(
            heading = "Orders",
            subHeading = "Total Order Today",
            valueText = "1250",
            percentage = 80,
            color = Color.Red,
            isIncreasing = true
        )

    }
}

data class MonthlySalesData(
    val month: String,
    val salesInThousands: Int
)

val year2022Sales = mutableListOf(
    MonthlySalesData(month = "Jan", salesInThousands = 13),
    MonthlySalesData(month = "Feb", salesInThousands = 24),
    MonthlySalesData(month = "Mar", salesInThousands = 16),
    MonthlySalesData(month = "Apr", salesInThousands = 32)
)

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
                        .rotate(if(isIncreasing) -45f else 45f)
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
fun SingleBar(monthlySalesData: MonthlySalesData) {
    val largest = year2022Sales.maxOf {
        it.salesInThousands
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${monthlySalesData.salesInThousands}₺",
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
                    (200 * monthlySalesData.salesInThousands / largest).dp
                )
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = monthlySalesData.month,
            color = Color.White,
            fontSize = 14.sp
            //fontFamily = FontFamily(Font())
        )
    }
}

@Preview
@Composable
fun StockDetailPagePrev() {
    StockDetailPage()
}


