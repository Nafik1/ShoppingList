package com.example.shoppinglist.Presentation

import  android.content.Context

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.Domain.entity.shop_item
import com.example.shoppinglist.Presentation.viewmodel.shopItemViewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.FragmentShopItemBinding

class shopItemFragment
 : Fragment() {
    private var _binding : FragmentShopItemBinding? = null
    private val binding : FragmentShopItemBinding
        get() = _binding ?: throw RuntimeException("oao")
    private lateinit var si_viewmodel: shopItemViewModel
    private lateinit var onEditing: onEditingFinishedListenner



    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = shop_item.UNDENFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("Kursedzxca","OnAttach")
        if (context is onEditingFinishedListenner) {
            onEditing = context
        } else {
            throw RuntimeException("Activity must implements")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Kursedzxca","OnCreate")
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("Kursedzxca","OnCreateView")
        _binding = FragmentShopItemBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Kursedzxca","OnViewCreated")
        si_viewmodel = ViewModelProvider(this)[shopItemViewModel::class.java]
        launchRightMode()
        observeView()
        addTextChangeListeners()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Kursedzxca","OnDestroyView")
        _binding = null
    }


    private fun observeView() {
        si_viewmodel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            binding.tilCount.error = message
        }
        si_viewmodel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            binding.tilName.error = message
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
    interface onEditingFinishedListenner {
        fun onEditingFinished()
    }
    private fun addTextChangeListeners() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                si_viewmodel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.etCount.addTextChangedListener(object : TextWatcher {
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
            binding.etName.setText(it.name)
            binding.etCount.setText(it.count.toString())
        }
        binding.saveButton.setOnClickListener {
            si_viewmodel.editShopItem(binding.etName.text?.toString(), binding.etCount.text?.toString())
        }
    }

    private fun launch_add_mode() {
        binding.saveButton.setOnClickListener {
            si_viewmodel.addItem(binding.etName.text?.toString(), binding.etCount.text?.toString())
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
            shopItemId = args.getInt(EXTRA_SHOPITEM_ID, shop_item.UNDENFINED_ID)
        }
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