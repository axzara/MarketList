/*  Programador : Alejandro Zarazúa Gutiérrez
    Fecha. : 04/11/22
    descripción. : Lista auxiliar para supermercado*/
package com.example.marketlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = findViewById<ListView>(R.id.list)

        val product = Product("Prod1", "Desc1" , 3,200.5, R.drawable.ic_launcher_background )
        val product2 = Product("Prod2", "Desc2" , 10,15.0, R.drawable.ic_launcher_background )

        val listProducts = listOf(product,product2)

        val adapter = ProductAdapter(this,listProducts)
        list.adapter = adapter
    }
}