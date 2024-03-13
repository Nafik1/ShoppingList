package com.example.shoppinglist.Presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.Domain.entity.shop_item
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.databinding.ItemShopDisabledBinding
import com.example.shoppinglist.databinding.ItemShopEnableBinding

class shopListAdapter : ListAdapter<shop_item,shopListAdapter.shopItemViewHolder>(shopItemDiffCallback()){

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
        val shopitem = getItem(position)
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



    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if(item.status) {
            ENABLED_VIEW_TYPE
        } else {
            DISABLED_VIEW_TYPE
        }
    }
//1
    class shopItemViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view) {
        val textVName = view.findViewById<TextView>(R.id.tv_name)
        val textVCount = view.findViewById<TextView>(R.id.tv_count)

    }

    companion object {
        const val ENABLED_VIEW_TYPE = 1;
        const val DISABLED_VIEW_TYPE = -1;
        const val MAX_POOL_SIZE = 15
    }

}
