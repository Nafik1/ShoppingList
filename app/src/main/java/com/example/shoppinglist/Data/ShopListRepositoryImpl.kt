package com.example.shoppinglist.Data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.shoppinglist.Domain.entity.shop_item
import com.example.shoppinglist.Domain.shop_list_repository

class ShopListRepositoryImpl(
    application: Application
) : shop_list_repository {
    private val shopListDao = DataBase.getInstance(application).shopitemdao()
    private val mapper: Mapper = Mapper()

    override suspend fun addShopList(shopitem: shop_item) {
        shopListDao.addShopitem(mapper.mapEntityToDbModel(shopitem))
    }

    override suspend fun deleteShopList(shopitem: shop_item) {
        shopListDao.deleteShopitem(shopitem.id)
    }

    override suspend fun editShopItem(shopItem: shop_item) {
        shopListDao.addShopitem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getItemShop(shopItemId: Int): shop_item {
        val dbModel = shopListDao.getOneItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<shop_item>> = MediatorLiveData<List<shop_item>>()
        .apply {
            addSource(shopListDao.getAllItem()) {
                value = mapper.mapListDbModelToListEntity(it)
            }
        }

}