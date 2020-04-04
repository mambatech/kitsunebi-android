package com.mamba.vpn.free.unlimited.hivpn.ui

import android.annotation.TargetApi
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.net.VpnService
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamba.vpn.free.unlimited.hivpn.R
import com.mamba.vpn.free.unlimited.hivpn.bean.ConnectStatus
import com.mamba.vpn.free.unlimited.hivpn.bean.VpnItemBean
import com.mamba.vpn.free.unlimited.hivpn.common.Constants
//import com.mamba.vpn.free.unlimited.hivpn.common.GGHelper
import com.mamba.vpn.free.unlimited.hivpn.common.showAlert
import com.mamba.vpn.free.unlimited.hivpn.helper.VpnConnectMgr
import com.mamba.vpn.free.unlimited.hivpn.service.SimpleVpnService
import com.mamba.vpn.free.unlimited.hivpn.storage.Preferences
import com.mamba.vpn.free.unlimited.hivpn.ui.adapter.VpnListAdapter
import com.mamba.vpn.free.unlimited.hivpn.ui.dialog.RateDialog
//import com.google.android.gms.ads.formats.UnifiedNativeAdView
//import com.google.android.gms.ads.reward.RewardItem
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),
        VpnListAdapter.VpnItemListener/*,GGHelper.RewardGGListener*/ {

    var running = false
    private var starting = false
    private var stopping = false
    private lateinit var configString: String

    val mNotificationId = 100
    private val NOTIFICATION_CHANNEL_ID = "scene_channel"
    private val NOTIFICATION_CHANNEL_NAME = "scene_notification"
    var mNotificationManager: NotificationManager? = null
    private var vpnAdapter: VpnListAdapter? = null
    private var curSelectedPosition = 0
    private var ratingDialog: RateDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        GGHelper.loadExitGG(this)
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.theme_blue)
                .init()

        setSupportActionBar(toolbar)
        loadGGAndShow()

        mNotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        registerReceiver()
        sendBroadcast(Intent("ping"))
        updateUI()

        fab.setOnClickListener { view ->
            if (!running && !starting) {
                starting = true
                fab.setImageResource(android.R.drawable.ic_media_ff)
                val intent = VpnService.prepare(this)
                updateVpnItemStatus(ConnectStatus.CONNECTING)
                VpnConnectMgr.curStatus = ConnectStatus.CONNECTING
                if (intent != null) {
                    startActivityForResult(intent, 1)
                } else {
                    onActivityResult(1, Activity.RESULT_OK, null);
                }
            } else if (running && !stopping) {
                stopping = true
                sendBroadcast(Intent("stop_vpn"))
            }
        }
    }

    private fun loadGGAndShow(){
//        val adView = layoutInflater
//                .inflate(R.layout.template_main_page_ad, null) as UnifiedNativeAdView
//        GGHelper.loadAndShowMainPageAd(this,adView,fl_ad)

//        GGHelper.rewardGGListener = this
//        GGHelper.loadRewardVideoGG(this)
    }

    override fun onItemClicked(position: Int) {
        val dataList = vpnAdapter?.dataList
        if (dataList?.isNotEmpty() == true){
            VpnConnectMgr.curVpnConfig = dataList[position].configJson
            for (i in 0 until dataList.size){
                val bean = dataList[i]
                if (i == position){
                    curSelectedPosition = i
                    bean.isSelected = true
                }else{
                    bean.isSelected = false
                }
            }

            vpnAdapter?.notifyDataSetChanged()
        }
    }

    private fun updateVpnItemStatus(@ConnectStatus status: Int) {
        val dataList = vpnAdapter?.dataList
        if (dataList?.isNotEmpty() == true) {
            val bean = dataList[curSelectedPosition]
            bean.status = status
        }

        vpnAdapter?.notifyItemChanged(curSelectedPosition)
    }

    private fun updateUI() {
        configString = VpnConnectMgr.curVpnConfig
        vpnAdapter = VpnListAdapter()
        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list.adapter = vpnAdapter
        vpnAdapter?.itemClickListener = this
        vpnAdapter?.updateDatas(initData())
    }

    private fun initData(): List<VpnItemBean>{
        val japanBean = VpnItemBean(ConnectStatus.STOPPED,R.drawable.ic_japan,
                getString(R.string.str_japan),true,Constants.JAPAN_CONFIG)
        val singaporeBean = VpnItemBean(ConnectStatus.STOPPED,R.drawable.ic_singapore,
                getString(R.string.str_singapore),false,Constants.SINGAPORE_CONFIG)

        return arrayListOf(japanBean,singaporeBean)
    }

    private fun registerReceiver(){
        registerReceiver(broadcastReceiver, IntentFilter("vpn_stopped"))
        registerReceiver(broadcastReceiver, IntentFilter("vpn_started"))
        registerReceiver(broadcastReceiver, IntentFilter("vpn_start_err"))
        registerReceiver(broadcastReceiver, IntentFilter("vpn_start_err_dns"))
        registerReceiver(broadcastReceiver, IntentFilter("vpn_start_err_config"))
        registerReceiver(broadcastReceiver, IntentFilter("pong"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val intent = Intent(this, SimpleVpnService::class.java)
            startService(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        sendBroadcast(Intent("ping"))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        GGHelper.showExitGG()
    }

    private fun startNotification() {
        // Build Notification , setOngoing keeps the notification always in status bar
        val mBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("ExNor")
                .setContentText("Enjoy it")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager?.createNotificationChannel(createChannel(this))
        }

        // Create pending intent, mention the Activity which needs to be
        //triggered when user clicks on notification(StopScript.class in this case)
        val contentIntent = PendingIntent.getActivity(this, 0,
                Intent(this, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

        mBuilder.setContentIntent(contentIntent)

        // Builds the notification and issues it.
        Log.e("sentNotification","sent it...")
        mNotificationManager?.notify(mNotificationId, mBuilder.build())
    }

    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "vpn_stopped" -> {
                    VpnConnectMgr.curStatus = ConnectStatus.STOPPED
                    updateVpnItemStatus(ConnectStatus.STOPPED)
                    running = false
                    stopping = false
                    fab.setImageResource(android.R.drawable.ic_media_play)
                    mNotificationManager?.cancel(mNotificationId)
                }
                "vpn_started" -> {
                    VpnConnectMgr.curStatus = ConnectStatus.CONNECTED
                    updateVpnItemStatus(ConnectStatus.CONNECTED)
                    running = true
                    starting = false
                    fab.setImageResource(android.R.drawable.ic_media_pause)
                    fab.post {
                        startNotification()
                        showRateOrAdDialog()
                    }
                }
                "vpn_start_err" -> {
                    VpnConnectMgr.curStatus = ConnectStatus.STOPPED
                    updateVpnItemStatus(ConnectStatus.STOPPED)
                    running = false
                    starting = false
                    fab.setImageResource(android.R.drawable.ic_media_play)
                    context?.let {
                        showAlert(it, "Start VPN service failed")
                    }
                }
                "vpn_start_err_dns" -> {
                    VpnConnectMgr.curStatus = ConnectStatus.STOPPED
                    updateVpnItemStatus(ConnectStatus.STOPPED)
                    running = false
                    starting = false
                    fab.setImageResource(android.R.drawable.ic_media_play)
                    context?.let {
                        showAlert(it, "Start VPN service failed: Not configuring DNS right, must has at least 1 dns server and mustn't include \"localhost\"")
                    }
                }
                "vpn_start_err_config" -> {
                    VpnConnectMgr.curStatus = ConnectStatus.STOPPED
                    updateVpnItemStatus(ConnectStatus.STOPPED)
                    running = false
                    starting = false
                    fab.setImageResource(android.R.drawable.ic_media_play)
                    context?.let {
                        showAlert(it, "Start VPN service failed: Invalid V2Ray config.")
                    }
                }
                "pong" -> {
                    fab.setImageResource(android.R.drawable.ic_media_pause)
                    running = true
                    Preferences.putBool( getString(R.string.vpn_is_running), true)
                }
            }
        }
    }

    private fun showRateOrAdDialog() {
        val connectTimes = Preferences.getInt(Preferences.KEY_CONNECT_TIME, 1)
        if (connectTimes == 2) {

            if (ratingDialog == null) {
                ratingDialog = RateDialog(this, RateDialog.OnStarListener { starLevel ->
                    try {
                        if (starLevel < 4) {
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "message/rfc822" // 设置邮件格式
                            val mail = arrayOf("mambatech2020@gmail.com")
                            intent.putExtra(Intent.EXTRA_EMAIL, mail)
                            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_title))
                            startActivity(Intent.createChooser(intent, getString(R.string.select_mailbox)))
                        } else {
                            val intent = Intent()
                            intent.action = Intent.ACTION_VIEW
                            intent.data = Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                            startActivity(intent)
                        }
                    } catch (e: Exception) {

                    }

                })
            }

            ratingDialog?.show()
        } else {
//            GGHelper.showRewardVideoGG()
        }

        Preferences.putInt(Preferences.KEY_CONNECT_TIME,connectTimes + 1)
    }

    @TargetApi(26)
    fun createChannel(context: Context): NotificationChannel { //创建 通知通道
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

//    override fun onRewarded(reward: RewardItem?) {
//
//    }
//
//    override fun onGGClosed() {
//        fab?.postDelayed({
//            GGHelper.loadRewardVideoGG(this)
//        },1000)
//    }
}