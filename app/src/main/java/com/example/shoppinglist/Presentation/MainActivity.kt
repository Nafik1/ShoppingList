package com.example.shoppinglist.Presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.Domain.shop_item
import com.example.shoppinglist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewmodel : MainViewModel
    private lateinit var shoplistlinearlayout :LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shoplistlinearlayout = findViewById(R.id.LinearLayoutShopList)
        viewmodel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewmodel.shoplist.observe(this) {
            showList(it)
        }
    }
    private fun showList(list : List<shop_item>) {
        shoplistlinearlayout.removeAllViews()
        for(shopitem in list) {
                val layoutId = if (shopitem.status) {
                    R.layout.item_shop_enable
                } else {
                    R.layout.item_shop_disabled
                }
            val view = LayoutInflater.from(this).inflate(layoutId, shoplistlinearlayout, false)
            val textVName = view.findViewById<TextView>(R.id.tv_name)
            val textVCount = view.findViewById<TextView>(R.id.tv_count)
            textVName.text = shopitem.name
            textVCount.text = shopitem.count.toString()
            view.setOnLongClickListener {
                viewmodel.changeEnableState(shopitem)
                true
            }
            shoplistlinearlayout.addView(view)

        }
    }
}