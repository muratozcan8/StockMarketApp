package com.muratozcan.stockmarketapp

import android.app.Application
import com.muratozcan.stockmarketapp.network.Api
import com.muratozcan.stockmarketapp.network.HeaderInterceptor
import com.muratozcan.stockmarketapp.repositories.StockMarketRepo
import com.muratozcan.stockmarketapp.repositories.StockMarketRepoImpl
import okhttp3.OkHttpClient
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(module {
                single {
                    val client = OkHttpClient.Builder()
                        .addInterceptor(HeaderInterceptor())
                        .build()
                    Retrofit
                        .Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .baseUrl("http://10.0.2.2:8080/")
                        .build()
                }
                single {
                    val retrofit:Retrofit = get()
                    retrofit.create(Api::class.java)
                }
                single {
                    val api: Api = get()
                    StockMarketRepoImpl(api)
                } bind StockMarketRepo::class
            })
        }
    }
}