package com.exnor.vray.net.api

import com.exnor.vray.bean.ConfigBean
import com.exnor.vray.bean.RawServerConfig
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
created by edison 2020/5/31
 */
interface UpdateApi {

    @GET("base_config.json")
    fun getUpdateConfig(): Observable<ConfigBean>

    //加密的vpn配置
    @GET("servers_config")
    fun getServerConfig(@Query("version") version: Int): Observable<RawServerConfig>
}