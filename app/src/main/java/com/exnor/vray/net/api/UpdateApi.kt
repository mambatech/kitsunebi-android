package com.exnor.vray.net.api

import com.exnor.vray.bean.ConfigBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

/**
created by edison 2020/5/31
 */
interface UpdateApi {

    @GET("base_config.json")
    fun getUpdateConfig(): Observable<ConfigBean>
}