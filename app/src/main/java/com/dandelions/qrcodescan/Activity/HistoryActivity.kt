package com.dandelions.qrcodescan.Activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dandelions.qrcodescan.Activity.ListViewAdapter
import com.dandelions.qrcodescan.Entity.ScanResult
import com.dandelions.qrcodescan.Other.DaoUtils
import com.dandelions.qrcodescan.R
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : Activity() {

    private var data: MutableList<ScanResult> = ArrayList<ScanResult>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        getData()
        initView()

    }

    private fun initView() {
        val manage: LinearLayoutManager = LinearLayoutManager(this)
        val myAdapter: ListViewAdapter = ListViewAdapter(this, data)
        list_view.layoutManager = manage
        list_view.adapter = myAdapter
    }

    fun clickBack(view: View) {
        finish()
    }

    private fun getData() {
        data = DaoUtils.findAll()!!
    }


}