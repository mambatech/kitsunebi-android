package com.exnor.vray.record

import java.util.*

/**
 * 广告相关埋点
 *
 * Created by cyclone on 2020/6/2.
 */

object RecordGG : RecordBase() {
    private const val EVENT_GG_LOAD = "GG_LOAD"
    private const val EVENT_GG_LOAD_RESULT = "GG_LOAD_RESULT"
    private const val EVENT_GG_SHOW = "GG_SHOW"
    private const val EVENT_GG_SHOW_RESULT = "GG_SHOW_RESULT"
    private const val EVENT_GG_CLICK = "GG_CLICK"

    private const val KEY_RESULT = "result"
    private const val KEY_CODE_ID = "code_id"
    private const val KEY_TYPE = "type"
    private const val KEY_ERROR_CODE = "error_code"

    const val VALUE_SUCCESS = 1
    const val VALUE_FAIL = 0
    const val VALUE_NATIVE = "native"
    const val VALUE_REWARD = "reward"
    const val VALUE_FULL_SCREEN = "full_screen"

    private const val VALUE_NO_CACHE = 0
    const val VALUE_VIDEO_ERROR = 1

    fun recordGGLoad(codeId: String,
                     type: String) {
        record(EVENT_GG_LOAD, hashMapOf<String, Any>().apply {
            this[KEY_CODE_ID] = codeId
            this[KEY_TYPE] = type
        })
    }

    fun recordGGLoadResult(codeId: String,
                           type: String,
                           result: Int,
                           errorCode: Int = 0) {
        record(EVENT_GG_LOAD_RESULT, hashMapOf<String, Any>().apply {
            this[KEY_CODE_ID] = codeId
            this[KEY_TYPE] = type
            this[KEY_RESULT] = result
            this[KEY_ERROR_CODE] = errorCode
        })
    }

    fun recordGGShow(codeId: String,
                     type: String) {
        record(EVENT_GG_SHOW, hashMapOf<String, Any>().apply {
            this[KEY_CODE_ID] = codeId
            this[KEY_TYPE] = type
        })
    }

    fun recordGGShowResult(codeId: String,
                           type: String,
                           result: Int,
                           errorCode: Int = VALUE_NO_CACHE) {
        record(EVENT_GG_SHOW_RESULT, hashMapOf<String, Any>().apply {
            this[KEY_CODE_ID] = codeId
            this[KEY_TYPE] = type
            this[KEY_RESULT] = result
            this[KEY_ERROR_CODE] = errorCode
        })
    }

    fun recordGGClick(codeId: String,
                           type: String) {
        record(EVENT_GG_CLICK, hashMapOf<String, Any>().apply {
            this[KEY_CODE_ID] = codeId
            this[KEY_TYPE] = type
        })
    }
}