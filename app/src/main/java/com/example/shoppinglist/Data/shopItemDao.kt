package com.example.shoppinglist.Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface shopItemDao  {
    @Query("SELECT * FROM shopItems")
    fun getAllItem() : LiveData<List<ShopItemDBModel>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopitem(shopItemDBModel: ShopItemDBModel)
    @Query("DELETE FROM shopItems WHERE id=:shopItemId")
    suspend fun deleteShopitem(shopItemId : Int)
    @Query("SELECT * FROM shopItems WHERE id=:shopItemId LIMIT 1")
    suspend fun getOneItem(shopItemId: Int) : ShopItemDBModel

}