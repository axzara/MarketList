package com.example.marketlist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductsDao {
    @Query("SELECT * FROM products")
    fun getAll(): LiveData<List<Product>>

    @Query("SELECT * FROM products WHERE idProduct = :id")
    fun get(id:Int): LiveData<Product>

    @Insert
    fun insertAll(vararg products: Product): List<Long>

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)
}