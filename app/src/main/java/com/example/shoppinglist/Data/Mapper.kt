package com.example.shoppinglist.Data

import com.example.shoppinglist.Domain.entity.shop_item

class Mapper {
    fun mapEntityToDbModel(shopitem : shop_item) : ShopItemDBModel {
        return ShopItemDBModel(
            id = shopitem.id,
            name = shopitem.name,
            count = shopitem.count,
            status = shopitem.status
        )
    }
    fun mapDbModelToEntity(shopitem : ShopItemDBModel) : shop_item {
        return shop_item(
            id = shopitem.id,
            name = shopitem.name,
            count = shopitem.count,
            status = shopitem.status
        )
    }
    fun mapListDbModelToListEntity(list : List<ShopItemDBModel>) = list.map { mapDbModelToEntity(it)}
}