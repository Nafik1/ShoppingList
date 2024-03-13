package com.example.shoppinglist.Presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.Data.ShopListRepositoryImpl
import com.example.shoppinglist.Domain.usecase.delete_shoplist_usecase
import com.example.shoppinglist.Domain.usecase.edit_shopitem_usecase
import com.example.shoppinglist.Domain.usecase.get_shoplist_usecase
import com.example.shoppinglist.Domain.entity.shop_item
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)
    private val getshoplistUseCase = get_shoplist_usecase(repository)
    private val deleteshoplistUseCase = delete_shoplist_usecase(repository)
    private val editshoplistUseCase = edit_shopitem_usecase(repository)


    val shoplist = getshoplistUseCase.getShopList()

    fun deleteItem(item: shop_item) {
        viewModelScope.launch {
            deleteshoplistUseCase.deleteShopList(item)
        }
    }

    fun changeEnableState(item: shop_item) {
        viewModelScope.launch {
            val newItem = item.copy(status = !(item.status))
            editshoplistUseCase.editShopItem(newItem)
        }
    }

}