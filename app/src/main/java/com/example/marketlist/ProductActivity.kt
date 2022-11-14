package com.example.marketlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val product = intent.getSerializableExtra("product") as Product

        val nombre = findViewById<TextView>(R.id.txtNombreProd)
        val cantidad = findViewById<TextView>(R.id.txtCantidadProd)
        val descripcion = findViewById<TextView>(R.id.txtDescripcionProd)
        val precio = findViewById<TextView>(R.id.txtPrecioProd)
        val imagen = findViewById<ImageView>(R.id.imgProd)

        nombre.text = product.nombre
        cantidad.text = "${product.cantidad} pzs"
        descripcion.text = product.descripcion
        precio.text = "$${product.precio}"
        imagen.setImageResource(product.imagen)
    }
}