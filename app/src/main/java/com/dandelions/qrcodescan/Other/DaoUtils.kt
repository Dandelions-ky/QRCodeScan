package com.dandelions.qrcodescan.Other

import android.content.ContentValues
import com.dandelions.qrcodescan.Entity.ScanResult
import org.litepal.LitePal
import org.litepal.extension.update

object DaoUtils {

    /**
     * 保存数据
     */
    fun saveResult(text: String) {
        val result = ScanResult(text);
        result.save()
    }

    /**
     * 根据id修改数据
     */
    fun amendData(id: Long, contents: String) {
        val cv = ContentValues()
        cv.put("scanResult", "contents")
        LitePal.update(ScanResult::class.java, cv, id)
    }

    /**
     * 查询所有数据
     */
    fun findAll(): MutableList<ScanResult>? {
        return LitePal.findAll(ScanResult::class.java)
    }
}