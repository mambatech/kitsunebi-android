package com.exnor.vray.ui.dialog

import android.content.Context
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatDialog
import com.exnor.vray.R
import kotlinx.android.synthetic.main.dialog_app_update.*

/**
 */
class AppUpdateDialog @JvmOverloads constructor(
    mContext: Context,
    theme: Int = R.style.CustomDialog
) : AppCompatDialog(mContext, theme) {

    init {
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        setContentView(R.layout.dialog_app_update)
        initListener()
    }

    interface CommonLRDialogListener {
        fun onCancelClick()
        fun onCopyClick()
    }

    var clickListener: CommonLRDialogListener? = null

    private fun initListener() {
        tv_left?.setOnClickListener {
            dismiss()
            clickListener?.onCancelClick()
        }
        tv_right?.setOnClickListener {
            dismiss()
            clickListener?.onCopyClick()
        }
    }

    fun setUpdateLogTxt(subtitleStr: String?) {
        if (!TextUtils.isEmpty(subtitleStr)) {
            tv_sub_title?.visibility = View.VISIBLE
            tv_sub_title?.text = subtitleStr
        }
    }

}