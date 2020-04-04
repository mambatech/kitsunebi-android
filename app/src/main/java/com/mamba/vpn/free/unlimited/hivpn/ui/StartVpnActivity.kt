package com.mamba.vpn.free.unlimited.hivpn.ui

import com.mamba.vpn.free.unlimited.hivpn.R
import com.mamba.vpn.free.unlimited.hivpn.service.SimpleVpnService
import android.app.Activity
import android.content.Intent
import android.net.VpnService
import android.os.Bundle

class StartVpnActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_start_vpn)

        val intent = VpnService.prepare(this)
        if (intent != null) {
            startActivityForResult(intent, 1)
        } else {
            onActivityResult(1, Activity.RESULT_OK, null);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val intent = Intent(this, SimpleVpnService::class.java)
            startService(intent)
        }
        finish()
    }
}