package com.example.shoppinglist.Data

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDBModel::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    companion object {
        private var db: DataBase? = null
        private const val db_name = "main.db"
        private val LOCK = Any()

        fun getInstance(application: Application): DataBase {
            db?.let { return it}
            kotlin.synchronized(LOCK) {
                db?.let { return it}
                val instance = Room.databaseBuilder(application, DataBase::class.java, db_name)
                    .fallbackToDestructiveMigration()
                    .build()
                db = instance
                return instance
            }
        }
    }
    abstract fun shopitemdao() : shopItemDao
}