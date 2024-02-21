package com.example.shoppinglist.Presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.Domain.shop_item
import com.example.shoppinglist.R

class shopListAdapter : RecyclerView.Adapter<shopListAdapter.shopItemViewHolder>() {
    val list = listOf<shop_item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): shopItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shop_enable, parent, false)
        return shopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: shopItemViewHolder, position: Int) {
        val shopitem = list[position]
        holder.textVName.text = shopitem.name
        holder.textVCount.text = shopitem.count.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class shopItemViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val textVName = view.findViewById<TextView>(R.id.tv_name)
        val textVCount = view.findViewById<TextView>(R.id.tv_count)
    }
}
