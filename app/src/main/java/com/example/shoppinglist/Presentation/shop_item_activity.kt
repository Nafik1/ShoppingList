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

class shop_item_activity : AppCompatActivity() {
    private lateinit var si_viewmodel: shopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = shop_item.UNDENFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        si_viewmodel = ViewModelProvider(this)[shopItemViewModel::class.java]
        initViews()
        launchRightMode()
        observeView()
        addTextChangeListeners()
    }

    private fun observeView() {
        si_viewmodel.errorInputCount.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }
        si_viewmodel.errorInputName.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        si_viewmodel.close.observe(this) {
            finish()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launch_edit_mode()
            MODE_ADD -> launch_add_mode()
        }
    }

    private fun addTextChangeListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                si_viewmodel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                si_viewmodel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun launch_edit_mode() {
        si_viewmodel.getShopItem(shopItemId)
        si_viewmodel.shopItem.observe(this) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            si_viewmodel.editShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun launch_add_mode() {
        buttonSave.setOnClickListener {
            si_viewmodel.addItem(etName.text?.toString(), etCount.text?.toString())
        }

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

    private fun initViews() {
        tilCount = findViewById(R.id.til_count)
        tilName = findViewById(R.id.til_name)
        etName = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        buttonSave = findViewById(R.id.save_button)
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

