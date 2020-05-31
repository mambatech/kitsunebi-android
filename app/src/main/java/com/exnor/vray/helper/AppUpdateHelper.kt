package com.exnor.vray.helper

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.exnor.vray.MApplication
import com.exnor.vray.R
import com.exnor.vray.bean.ConfigBean
import com.exnor.vray.bean.NewVersion
import com.exnor.vray.net.ApiMgr
import com.exnor.vray.ui.dialog.AppUpdateDialog
import com.exnor.vray.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

/**
created by edison 2020/5/31
 */
class AppUpdateHelper : AppUpdateDialog.CommonLRDialogListener {

    var dialog: AppUpdateDialog? = null
    var copyLink: String? = null
    var mContext: Context? = null

    fun checkAndShowUpdateDialog(context: Context) {
        mContext = context
        ApiMgr.updateConfigApi()
                .getUpdateConfig()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("updateApi", "msg:success")
                    if (it != null) {
                        showUpdateDialog(context, it)
                    }
                }, {
                    Log.e("updateApi", "msg:${it.message}")
                })
    }

    private fun showUpdateDialog(context: Context, configBean: ConfigBean) {
        try {
            val newVersions = configBean.newVersion
            val curVersion = Utils.getVersionCode(MApplication.sIns)
            var minGap = 0
            var newOne: NewVersion? = null
            for (i in newVersions.indices) {
                val newVersion = newVersions[i]
                if (minGap == 0) {
                    minGap = newVersion.version
                }

                //找出最近的升级版本
                if (newVersion.version <= minGap) {
                    newOne = newVersion
                    minGap = newVersion.version
                }
            }

            if (newOne != null && newOne.version > curVersion) {
                dialog = AppUpdateDialog(context)
                dialog?.clickListener = this
                dialog?.setUpdateLogTxt(configBean.updateLog)
                copyLink = if (!newOne.useGp && !TextUtils.isEmpty(configBean.apkLink)) {
                    configBean.apkLink
                } else {
                    configBean.gpLink
                }

                dialog?.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCancelClick() {
        dialog?.dismiss()
    }

    override fun onCopyClick() {
        goUpdate()
        dialog?.dismiss()
    }

    private fun goUpdate() {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse("https://www.baidu.com")
        mContext?.startActivity(intent)
    }

    private fun copyAndToast() {
        val clipboardManager = MApplication.sIns.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.text = copyLink
        Toast.makeText(MApplication.sIns, MApplication.sIns.getString(R.string.str_copy_success), Toast.LENGTH_LONG).show()
    }

}