package com.example.shoppinglist.Presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewmodel : MainViewModel
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewmodel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewmodel.shoplist.observe(this) {
            Log.d("Szxc2js12",it.toString())
            if (count == 0) {
                count++
                val item = it[0]
                viewmodel.changeEnableState(item)
            }
        }

    }
}