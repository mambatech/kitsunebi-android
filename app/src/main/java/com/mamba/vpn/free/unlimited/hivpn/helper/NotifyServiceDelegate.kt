package com.mamba.vpn.free.unlimited.hivpn.helper

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.mamba.vpn.free.unlimited.hivpn.service.ForegroundNotiService

/**
created by edison 2020/4/5
 */
class NotifyServiceDelegate {

    private var notifyService: ForegroundNotiService? = null
    private var hasBinded = false

    private val serviceConnection = object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {
            hasBinded = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
           hasBinded = true
            val binder = service as ForegroundNotiService.MyBinder
            notifyService = binder.getService()
        }
    }

    fun bindService(context: Context){
        Intent(context, ForegroundNotiService::class.java).also { intent ->
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    fun updateNotify(isConnected: Boolean){
        notifyService?.updateNotify(isConnected)
    }

}