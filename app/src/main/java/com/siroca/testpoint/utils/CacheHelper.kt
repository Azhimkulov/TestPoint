package com.siroca.testpoint.utils

import com.preference.PowerPreference

/**
 * Created by azamat  on 9/15/19.
 */
object CacheHelper {
    fun writeSuccess(){
        val successCount = PowerPreference.getFileByName("process").getInt("success", 0)
        PowerPreference.getFileByName("process").putInt("success", successCount+1)
        val totalCount = PowerPreference.getFileByName("process").getInt("total", 0)
        PowerPreference.getFileByName("process").putInt("total", totalCount+1)
    }

    fun writeFail(){
        val totalCount = PowerPreference.getFileByName("process").getInt("total", 0)
        PowerPreference.getFileByName("process").putInt("total", totalCount+1)
    }

    fun getStat():Pair<String, String>{
        val successCount = PowerPreference.getFileByName("process").getInt("success", 0)
        val totalCount = PowerPreference.getFileByName("process").getInt("total", 0)
        return Pair(successCount.toString(), totalCount.toString())
    }
}