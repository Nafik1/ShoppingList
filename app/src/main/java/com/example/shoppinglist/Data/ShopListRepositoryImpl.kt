package com.example.shoppinglist.Data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.Domain.shop_item
import com.example.shoppinglist.Domain.shop_list_repository
import kotlin.random.Random

object ShopListRepositoryImpl: shop_list_repository {
    private val shoplistLD = MutableLiveData<List<shop_item>>()
    private val shopList = sortedSetOf(Comparator<shop_item> { o1, o2 -> o1.id.compareTo(o2.id) })
    private var autoIncrementId = 0
    init {
        for(i in 0 until 100) {
            val item = shop_item("Name$i", i, Random.nextBoolean())
            addShopList(item)
        }
    }

    override fun addShopList(shopitem: shop_item) {
        if (shopitem.id == shop_item.UNDENFINED_ID) {
            shopitem.id = autoIncrementId++
        }
        shopList.add(shopitem)
        updatelist()

    }

    override fun deleteShopList(shopitem: shop_item) {
        shopList.remove(shopitem)
        updatelist()
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

    override fun getShopList(): LiveData<List<shop_item>> {
        return shoplistLD
    }
    private fun updatelist() {
        shoplistLD.value = shopList.toList()
    }
}