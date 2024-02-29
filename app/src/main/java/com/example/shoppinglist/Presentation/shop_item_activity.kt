package com.example.shoppinglist.Presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.Domain.shop_item
import com.example.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout

class shop_item_activity : AppCompatActivity(), shopItemFragment.onEditingFinishedListenner {
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = shop_item.UNDENFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> shopItemFragment.newInstanceEditItem(shopItemId)
            MODE_ADD -> shopItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
    supportFragmentManager.beginTransaction()
        .replace(R.id.shopItemContainer, fragment)
        .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOPITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOPITEM_ID, shop_item.UNDENFINED_ID)
        }
    }

    override fun onEditingFinished() {
        finish()
    }

    companion object {
        private const val EXTRA_MODE: String = "EXTRA_MODE"
        private const val MODE_EDIT = "mode_edit"
        private const val EXTRA_SHOPITEM_ID = "extra_Shop_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""
        fun newIntentAdd(context: Context): Intent {
            val intent = Intent(context, shop_item_activity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEdit(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, shop_item_activity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOPITEM_ID, shopItemId)
            return intent
        }
    }

}

