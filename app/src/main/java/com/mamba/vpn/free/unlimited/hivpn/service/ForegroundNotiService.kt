package com.mamba.vpn.free.unlimited.hivpn.service

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.mamba.vpn.free.unlimited.hivpn.R
import com.mamba.vpn.free.unlimited.hivpn.ui.MainActivity

/**
created by edison 2020/4/4
 */
class ForegroundNotiService : Service() {

    val mNotificationId = 100
    private val NOTIFICATION_CHANNEL_ID = "scene_channel"
    private val NOTIFICATION_CHANNEL_NAME = "scene_notification"
    var mNotificationManager: NotificationManager? = null

    private val myBinder = MyBinder()

    override fun onBind(intent: Intent?): IBinder? {
        mNotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return myBinder
    }

    fun updateNotify(isConnected: Boolean) {
        val titleTxt = if (isConnected) {
            "Connect succeed, Enjoy it"
        } else {
            "See you next time"
        }

        val mBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("HiVpn")
                .setContentText(titleTxt)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager?.createNotificationChannel(createChannel(this))
        }

        val contentIntent = PendingIntent.getActivity(this, 0,
                Intent(this, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

        mBuilder.setContentIntent(contentIntent)

        mNotificationManager?.notify(mNotificationId, mBuilder.build())
    }

    @TargetApi(26)
    private fun createChannel(context: Context): NotificationChannel { //创建 通知通道
        val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH)

        channel.enableLights(true) //是否在桌面icon右上角展示小红点
        channel.lightColor = ContextCompat.getColor(context, R.color.colorAccent) //小红点颜色
        channel.enableVibration(false)
        channel.setShowBadge(true) //是否在久按桌面图标时显示此渠道的通知
        return channel
    }

    inner class MyBinder : Binder() {
        fun getService(): ForegroundNotiService = this@ForegroundNotiService
    }

}