package com.example.shoppinglist.Data

import com.example.shoppinglist.Domain.shop_item
import com.example.shoppinglist.Domain.shop_list_repository

object ShopListRepositoryImpl: shop_list_repository {

    private val shopList = mutableListOf<shop_item>()

    private var autoIncrementId = 0
    init {
        for(i in 0 until 10) {
            val item = shop_item("Name$i", i, true)
            addShopList(item)
        }
    }

    override fun addShopList(shopitem: shop_item) {
        if (shopitem.id == shop_item.UNDENFINED_ID) {
            shopitem.id = autoIncrementId++
        }
        shopList.add(shopitem)

    }

    override fun deleteShopList(shopitem: shop_item) {
        shopList.remove(shopitem)
    }

    override fun editShopItem(shopItem: shop_item) {
        val old_element = getItemShop(shopItem.id)
        shopList.remove(old_element)
        addShopList(shopItem)
    }

    override fun getItemShop(shopItemId: Int): shop_item {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun getShopList(): List<shop_item> {
        return shopList.toList()
    }
}