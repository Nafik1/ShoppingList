package com.example.shoppinglist.Presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.Domain.shop_item
import com.example.shoppinglist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewmodel: MainViewModel
    private lateinit var adapter : shopListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRw()
        viewmodel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewmodel.shoplist.observe(this) {
            adapter.shopList = it
        }
    }
    private fun setupRw() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = shopListAdapter()
        rvShopList.adapter = adapter
        rvShopList.recycledViewPool.setMaxRecycledViews(
            shopListAdapter.ENABLED_VIEW_TYPE,
            shopListAdapter.MAX_POOL_SIZE
        )
        rvShopList.recycledViewPool.setMaxRecycledViews(
            shopListAdapter.DISABLED_VIEW_TYPE,
            shopListAdapter.MAX_POOL_SIZE
        )
        longclick_activeItem()
        itemclick_infolist()
        swipe(rvShopList)
    }

    private fun longclick_activeItem() {
        adapter.onShopItemLongClick = {
            viewmodel.changeEnableState(it)
        }
    }

    private fun itemclick_infolist() {
        adapter.onShopItemClick = {
            Log.d("asjdsajd", it.toString())
        }
    }

    private fun swipe(rvShopList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.shopList[viewHolder.adapterPosition]
                viewmodel.deleteItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

}


