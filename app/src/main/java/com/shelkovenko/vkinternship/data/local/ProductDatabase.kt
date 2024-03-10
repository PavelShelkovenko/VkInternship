package com.shelkovenko.vkinternship.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {

        const val PRODUCTS_TABLE_NAME = "products"
        const val DB_NAME = "products_database.db"
    }
}