package com.example.shoppinglist.Presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.Data.ShopListRepositoryImpl
import com.example.shoppinglist.Domain.usecase.add_shoplist_usecase
import com.example.shoppinglist.Domain.usecase.edit_shopitem_usecase
import com.example.shoppinglist.Domain.usecase.get_shopitem_usecase
import com.example.shoppinglist.Domain.entity.shop_item
import kotlinx.coroutines.launch

class shopItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)
    private val addShopItem = add_shoplist_usecase(repository)
    private val getShopItem = get_shopitem_usecase(repository)
    private val editShopitemUsecase = edit_shopitem_usecase(repository)


    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName
    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<shop_item>()
    val shopItem: LiveData<shop_item>
        get() = _shopItem

    private val _close = MutableLiveData<Unit>()
    val close: LiveData<Unit>
        get() = _close

    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val item = getShopItem.getItemShop(shopItemId)
            _shopItem.value = item
        }
    }

    fun addItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            viewModelScope.launch {
                val shopitem = shop_item(name, count, true)
                addShopItem.addShopList(shopitem)
                finishWork()
            }

        }


    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name, count = count)
                    editShopitemUsecase.editShopItem(item)
                    finishWork()
                }

            }
        }

    }

    fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _close.value = Unit
    }

    override fun onCleared() {
        super.onCleared()
    }
}