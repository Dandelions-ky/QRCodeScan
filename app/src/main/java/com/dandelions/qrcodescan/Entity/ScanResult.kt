package com.dandelions.qrcodescan.Entity

import org.litepal.crud.LitePalSupport

class ScanResult(val scanResult: String) : LitePalSupport() {
    val id: Long = 0
}