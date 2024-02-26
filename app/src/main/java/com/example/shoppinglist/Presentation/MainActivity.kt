package com.example.shoppinglist.Presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewmodel: MainViewModel
    private lateinit var adapter: shopListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRw()
        viewmodel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewmodel.shoplist.observe(this) {
            adapter.submitList(it)
        }
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddItem.setOnClickListener {
            val intent = shop_item_activity.newIntentAdd(this)
            startActivity(intent)
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
            val intent = shop_item_activity.newIntentEdit(this, it.id)
            startActivity(intent)
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
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewmodel.deleteItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

}


