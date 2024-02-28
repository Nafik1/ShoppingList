package com.example.shoppinglist.Presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.Domain.shop_item
import com.example.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout

class shopItemFragment
 : Fragment() {
    private lateinit var si_viewmodel: shopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = shop_item.UNDENFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        si_viewmodel = ViewModelProvider(this)[shopItemViewModel::class.java]
        initViews(view)
        launchRightMode()
        observeView()
        addTextChangeListeners()
    }


    private fun observeView() {
        si_viewmodel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }
        si_viewmodel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        si_viewmodel.close.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
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
        si_viewmodel.shopItem.observe(viewLifecycleOwner) {
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

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(EXTRA_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD ) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if(screenMode == MODE_EDIT) {
            if(!args.containsKey(EXTRA_SHOPITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(EXTRA_SHOPITEM_ID,shop_item.UNDENFINED_ID)
        }
    }

    private fun initViews(view: View) {
        tilCount = view.findViewById(R.id.til_count)
        tilName = view.findViewById(R.id.til_name)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        buttonSave = view.findViewById(R.id.save_button)
    }

    companion object {
        private const val EXTRA_MODE: String = "EXTRA_MODE"
        private const val MODE_EDIT = "mode_edit"
        private const val EXTRA_SHOPITEM_ID = "extra_Shop_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): shopItemFragment {
            return shopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): shopItemFragment {
            return shopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE,MODE_EDIT)
                    putInt(EXTRA_SHOPITEM_ID,shopItemId)
                }
            }
        }

    }
}