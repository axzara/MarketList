package com.example.marketlist

import java.io.Serializable

class Product(
    val nombre: String,
    val descripcion: String,
    val cantidad: Int,
    val precio: Double,
    val imagen: Int) : Serializable{
}