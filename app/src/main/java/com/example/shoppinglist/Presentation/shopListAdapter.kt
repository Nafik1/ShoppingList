package com.example.shoppinglist.Presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.Domain.shop_item
import com.example.shoppinglist.R

class shopListAdapter : RecyclerView.Adapter<shopListAdapter.shopItemViewHolder>() {
    var shopList = listOf<shop_item>()
        set(value) {
            val callback = shopListDiffCallback(shopList,value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }
    var onShopItemLongClick : ((shop_item) -> Unit)? = null
    var onShopItemClick : ((shop_item) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): shopItemViewHolder {
        val layout = if(viewType == ENABLED_VIEW_TYPE) {
            R.layout.item_shop_enable
        } else {
            R.layout.item_shop_disabled
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return shopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: shopItemViewHolder, position: Int) {
        val shopitem = shopList[position]
        holder.textVName.text = shopitem.name
        holder.textVCount.text = shopitem.count.toString()
        holder.view.setOnLongClickListener {
            onShopItemLongClick?.invoke(shopitem)
            true
        }
        holder.view.setOnClickListener {
            onShopItemClick?.invoke(shopitem)
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }


    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if(item.status) {
            ENABLED_VIEW_TYPE
        } else {
            DISABLED_VIEW_TYPE
        }
    }

    class shopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textVName = view.findViewById<TextView>(R.id.tv_name)
        val textVCount = view.findViewById<TextView>(R.id.tv_count)
    }

    interface OnShopItemLongClick {
        fun onClickShopItemLong(shopItem : shop_item)
    }

    companion object {
        const val ENABLED_VIEW_TYPE = 1;
        const val DISABLED_VIEW_TYPE = -1;
        const val MAX_POOL_SIZE = 15
    }

}
