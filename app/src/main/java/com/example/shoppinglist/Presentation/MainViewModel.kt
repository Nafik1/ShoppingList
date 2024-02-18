package com.example.shoppinglist.Presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.Data.ShopListRepositoryImpl
import com.example.shoppinglist.Domain.delete_shoplist_usecase
import com.example.shoppinglist.Domain.edit_shopitem_usecase
import com.example.shoppinglist.Domain.get_shoplist_usecase
import com.example.shoppinglist.Domain.shop_item

class MainViewModel(application : Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl
    private val getshoplistUseCase = get_shoplist_usecase(repository)
    private val deleteshoplistUseCase = delete_shoplist_usecase(repository)
    private val editshoplistUseCase = edit_shopitem_usecase(repository)

    val shoplist = MutableLiveData<List<shop_item>>()
    fun getShopList() {
        val list = getshoplistUseCase.getShopList()
        shoplist.value = list
    }
    fun deleteItem(item : shop_item) {
        deleteshoplistUseCase.deleteShopList(item)
        getShopList()
    }
    fun changeEnableState(item : shop_item) {
        if (item.status != null) {
            val newItem = item.copy(status = !(item.status))
            editshoplistUseCase.editShopItem(newItem)
            getShopList()
        }
    }
}