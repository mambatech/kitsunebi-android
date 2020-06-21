package com.exnor.vray.ui

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
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.exnor.vray.MApplication
import com.exnor.vray.R
import com.exnor.vray.bean.ConnectStatus
import com.exnor.vray.bean.ServersConfigItem
import com.exnor.vray.bean.VpnItemBean
import com.exnor.vray.common.Constants
import com.exnor.vray.common.showAlert
import com.exnor.vray.gg.GGDanceHelper
import com.exnor.vray.gg.GGDelegate
import com.exnor.vray.gg.GGHelper
import com.exnor.vray.helper.AEStool
import com.exnor.vray.helper.AppUpdateHelper
import com.exnor.vray.helper.VpnConnectMgr
import com.exnor.vray.net.ApiMgr
import com.exnor.vray.service.SimpleVpnService
import com.exnor.vray.storage.Preferences
import com.exnor.vray.ui.adapter.VpnListAdapter
import com.exnor.vray.ui.dialog.LoadingDialog
import com.exnor.vray.ui.dialog.RateDialog
import com.exnor.vray.utils.GsonUtils
import com.exnor.vray.utils.Utils
import com.google.android.gms.ads.reward.RewardItem
import com.google.gson.reflect.TypeToken
import com.gyf.immersionbar.ImmersionBar
import com.umeng.analytics.MobclickAgent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : BaseActivity(),
        VpnListAdapter.VpnItemListener, GGHelper.RewardGGListener {

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
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.theme_green)
                .init()

        setSupportActionBar(toolbar)
        GGDanceHelper.initDanceGG(this)
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
                VpnConnectMgr.curStatus = ConnectStatus.CONNECTING
                updateConnectTxt()
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

        AppUpdateHelper().checkAndShowUpdateDialog(this)

        // APP启动次数+1
        Preferences.enterTimes++
    }

    private fun loadServerConfig() {
        ApiMgr.updateConfigApi()
                .getServerConfig(Utils.getVersionCode(this), MApplication.sIns.resources.configuration.locale.country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    if (it.code == 0) {
                        try {
                            val plainTxt = AEStool.decrypt(it.rawConfig)
//                            Log.e("loadServerConfig","plainTxt::$plainTxt")
                            val type= object : TypeToken<List<ServersConfigItem?>?>() {}.type
                            val configs = GsonUtils.fromJson<List<ServersConfigItem>>(plainTxt,type)
                            parseServerConfig(configs)
                        } catch (e: Exception) {
//                            Log.e("loadServerConfig","parseException")
                            vpnAdapter?.updateDatas(initLocalData())
                        }

                    }

//                    Log.e("loadServerConfig","parseException:code:${it.code}")
                },
                        { err ->
                            err.printStackTrace()
//                            Log.e("loadServerConfig","fail:${err.printStackTrace()}")
                            vpnAdapter?.updateDatas(initLocalData())
                        })
    }

    private fun parseServerConfig(configs: List<ServersConfigItem>) {
        val newConfigList = mutableListOf<VpnItemBean>()
        for (item in configs) {
            var tmp0 = Constants.BASE_CONFIG.replace(Constants.KEY_IP, item.IP)
            tmp0 = tmp0.replace(Constants.KEY_PORT, "\"port\":${item.Port}")
            tmp0 = tmp0.replace(Constants.KEY_UUID, item.UUID)
            newConfigList.add(VpnItemBean(ConnectStatus.STOPPED, item.icon_url,
                    item.country_name, false, tmp0))
        }

        var randomIndex = 0
        if (VpnConnectMgr.curStatus != ConnectStatus.CONNECTED) {
            randomIndex = Random.nextInt(newConfigList.size)
            curSelectedPosition = randomIndex
            val selectBean = newConfigList[randomIndex]
            selectBean.isSelected = true
            VpnConnectMgr.curVpnConfig = selectBean.configJson
        }

        dismissLoadingDialog()
        vpnAdapter?.updateDatas(newConfigList)
        Toast.makeText(this, getString(R.string.lines_updates), Toast.LENGTH_SHORT).show()
    }

    private fun loadGGAndShow() {
        GGDelegate.loadRewardGG()
        GGDelegate.loadMainPageGGAndShow(fl_ad_container)
        // 仅当3的倍数数轮进入APP的时候，展示进入广告
        if (Preferences.enterTimes % 3 == 2) {
            GGDelegate.loadEnterFullScreenGGAndShow(this)
        }
    }

    override fun onItemClicked(position: Int) {
        val dataList = vpnAdapter?.dataList
        if (dataList?.isNotEmpty() == true) {
            VpnConnectMgr.curVpnConfig = dataList[position].configJson
            Log.e("itemclicked","port:${dataList[position].configJson}")

            for (i in 0 until dataList.size) {
                val bean = dataList[i]
                if (i == position) {
                    curSelectedPosition = i
                    bean.isSelected = true
                } else {
                    bean.isSelected = false
                }
            }

            vpnAdapter?.notifyDataSetChanged()
        }
    }

    private fun updateUI() {
        configString = VpnConnectMgr.curVpnConfig
        vpnAdapter = VpnListAdapter()
        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list.adapter = vpnAdapter
        vpnAdapter?.itemClickListener = this
        vpnAdapter?.updateDatas(initLocalData())
        loadServerConfig()
        updateConnectTxt()
    }

    private fun updateConnectTxt() {
        when (VpnConnectMgr.curStatus) {
            ConnectStatus.CONNECTED -> {
                tv_state_txt.text = getString(R.string.state_connected)
            }

            ConnectStatus.CONNECTING -> {
                tv_state_txt.text = getString(R.string.state_connecting)
            }

            ConnectStatus.STOPPED -> {
                tv_state_txt.text = getString(R.string.state_stopped)
            }
        }
    }

    private fun initLocalData(): List<VpnItemBean> {
        val japanBean1 = VpnItemBean(ConnectStatus.STOPPED, "",
                getString(R.string.str_japan_1), false, Constants.JAPAN_CONFIG_1)
        val singaporeBean1 = VpnItemBean(ConnectStatus.STOPPED, "",
                getString(R.string.str_singapore_1), false, Constants.SINGAPORE_CONFIG_1)

        val japanBean2 = VpnItemBean(ConnectStatus.STOPPED, "",
                getString(R.string.str_japan_2), false, Constants.JAPAN_CONFIG_2)
        val singaporeBean2 = VpnItemBean(ConnectStatus.STOPPED, "",
                getString(R.string.str_singapore_2), false, Constants.SINGAPORE_CONFIG_2)

        val beanList = arrayListOf(singaporeBean1, singaporeBean2,japanBean1, japanBean2)
        var randomIndex = 0
        if (VpnConnectMgr.curStatus != ConnectStatus.CONNECTED) {
            randomIndex = Random.nextInt(beanList.size)
            curSelectedPosition = randomIndex
            val selectBean = beanList[randomIndex]
            selectBean.isSelected = true
            VpnConnectMgr.curVpnConfig = selectBean.configJson

        }

        return beanList
    }

    private fun registerReceiver() {
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
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onBackPressed() {
        super.onBackPressed()
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
        Log.e("sentNotification", "sent it...")
        mNotificationManager?.notify(mNotificationId, mBuilder.build())
    }

    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "vpn_stopped" -> {
                    VpnConnectMgr.curStatus = ConnectStatus.STOPPED
                    updateConnectTxt()
                    running = false
                    stopping = false
                    fab.setImageResource(android.R.drawable.ic_media_play)
                    mNotificationManager?.cancel(mNotificationId)
                    GGDanceHelper.loadRewardAd(GGDanceHelper.CODE_REWARD_SCREEN_GG)
                }
                "vpn_started" -> {
                    VpnConnectMgr.curStatus = ConnectStatus.CONNECTED
                    updateConnectTxt()
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
                    updateConnectTxt()
                    running = false
                    starting = false
                    fab.setImageResource(android.R.drawable.ic_media_play)
                    context?.let {
                        showAlert(it, "Start VPN service failed")
                    }
                }
                "vpn_start_err_dns" -> {
                    VpnConnectMgr.curStatus = ConnectStatus.STOPPED
                    updateConnectTxt()
                    running = false
                    starting = false
                    fab.setImageResource(android.R.drawable.ic_media_play)
                    context?.let {
                        showAlert(it, "Start VPN service failed: Not configuring DNS right, must has at least 1 dns server and mustn't include \"localhost\"")
                    }
                }
                "vpn_start_err_config" -> {
                    VpnConnectMgr.curStatus = ConnectStatus.STOPPED
                    updateConnectTxt()
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
                    Preferences.putBool(getString(R.string.vpn_is_running), true)
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
            GGDelegate.showRewardGG(this)
        }

        Preferences.putInt(Preferences.KEY_CONNECT_TIME, connectTimes + 1)
    }

    private fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun showLoadingDialog(isCancelable: Boolean) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog()
            loadingDialog?.isCancelable = isCancelable
        }

        if (loadingDialog?.isAdded == false) {
            loadingDialog?.show(supportFragmentManager, "LOADING_DIALOG")
        }
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

    override fun onRewarded(reward: RewardItem?) {

    }

    override fun onGGClosed() {}
}