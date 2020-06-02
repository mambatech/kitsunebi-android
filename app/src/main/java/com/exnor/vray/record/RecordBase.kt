package com.exnor.vray.record

import com.exnor.vray.MApplication
import com.umeng.analytics.MobclickAgent
import kotlin.collections.HashMap

/**
 * Record基类
 * Created by cyclone on 2020/6/2.
 */

open class RecordBase {
    fun record(event: String, map: HashMap<String, Any>?) {
        MobclickAgent.onEventObject(MApplication.sIns, event, map)
    }

    fun record(event: String) {
        MobclickAgent.onEvent(MApplication.sIns, event)
    }
}