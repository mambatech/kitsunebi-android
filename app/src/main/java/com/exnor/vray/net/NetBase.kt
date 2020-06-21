package com.exnor.vray.net

import com.exnor.vray.net.api.UpdateApi
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


/**
created by edison 2020/5/31
 */
object NetBase {

    val base_url = "http://45.76.197.50:8686/"


    private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(7, TimeUnit.SECONDS)
            .readTimeout(7, TimeUnit.SECONDS)
            .writeTimeout(7, TimeUnit.SECONDS)
            .build()

    private val retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    fun getRetrofit(): Retrofit {
        return retrofit
    }
}

object ApiMgr {
    fun updateConfigApi(): UpdateApi {
        return NetBase.getRetrofit().create(UpdateApi::class.java)
    }
}