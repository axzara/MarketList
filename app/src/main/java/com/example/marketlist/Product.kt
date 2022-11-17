package com.example.marketlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "products")
class Product(
    val nombre: String,
    val descripcion: String,
    val cantidad: Int,
    val precio: Double,
    val imagen: Int,
    @PrimaryKey(autoGenerate = true)
    var idProduct: Int = 0
    ) : Serializable{
}