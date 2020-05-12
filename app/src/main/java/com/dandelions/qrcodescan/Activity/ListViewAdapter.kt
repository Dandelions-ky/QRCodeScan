package com.dandelions.qrcodescan.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dandelions.qrcodescan.Entity.ScanResult
import com.dandelions.qrcodescan.R
import com.dandelions.qrcodescan.Other.ShareUtils
import kotlinx.android.synthetic.main.activity_capture.*

class ListViewAdapter(private val context: Context, private val data: MutableList<ScanResult>) :
    RecyclerView.Adapter<ListViewAdapter.myViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.textView.text = data[data.size - 1 - position].scanResult
        holder.imageView.setOnClickListener {
            ShareUtils.shareResult(context, data[data.size - 1 - position].scanResult)
        }
        holder.textView.setOnClickListener {
            val intent = Intent(context, ContentActivity().javaClass)
            intent.putExtra("APP_SCAN_RESULT", data[data.size - 1 - position].scanResult.toString())
            context.startActivity(intent)
        }
    }

    inner class myViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.text_result)

        var imageView: ImageView = view.findViewById(R.id.share_result)
    }


}