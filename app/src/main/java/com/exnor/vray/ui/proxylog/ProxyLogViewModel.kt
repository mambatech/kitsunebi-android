package com.exnor.vray.ui.proxylog

import com.exnor.vray.storage.Preferences
import com.exnor.vray.storage.ProxyLog
import com.exnor.vray.storage.ProxyLogDatabase
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class ProxyLogViewModel constructor(application: Application)
    : AndroidViewModel(application) {

    private var proxyLogLiveData: LiveData<PagedList<ProxyLog>>

    init {
        lateinit var factory: DataSource.Factory<Int, ProxyLog>

        // TODO using global constant string
        val isHideDns = Preferences.getBool( "is_hide_dns_logs", null)
        if (isHideDns) {
            factory = ProxyLogDatabase.getInstance(getApplication()).proxyLogDao().getAllNonDnsPaged()
        } else {
            factory = ProxyLogDatabase.getInstance(getApplication()).proxyLogDao().getAllPaged()
        }

        val pagedListBuilder: LivePagedListBuilder<Int, ProxyLog> = LivePagedListBuilder<Int, ProxyLog>(factory,
                30)
        proxyLogLiveData = pagedListBuilder.build()
    }

    fun getProxyLogLiveData() = proxyLogLiveData
}