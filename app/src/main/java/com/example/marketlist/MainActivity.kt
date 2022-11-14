/*  Programador : Alejandro Zarazúa Gutiérrez
    Fecha. : 04/11/22
    descripción. : Lista auxiliar para supermercado*/
package com.example.marketlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var txtFecha : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtFecha = findViewById(R.id.txtFechaCorte)
        val btnMainMenu = findViewById<Button>(R.id.btnGoMainMenu)

        val list = findViewById<ListView>(R.id.list)

        val product = Product("Prod1", "Desc1" , 3,200.5, R.drawable.ic_launcher_background )
        val product2 = Product("Prod2", "Desc2" , 10,15.0, R.drawable.ic_launcher_background )
        val product3 = Product("Prod2", "Desc2" , 10,15.0, R.drawable.ic_launcher_background )
        val product4 = Product("Prod2", "Desc2" , 10,15.0, R.drawable.ic_launcher_background )
        val product5 = Product("Prod2", "Desc2" , 10,15.0, R.drawable.ic_launcher_background )
        val product6 = Product("Prod2", "Desc2" , 10,15.0, R.drawable.ic_launcher_background )
        val product7 = Product("Prod2", "Desc2" , 10,15.0, R.drawable.ic_launcher_background )
        val product8 = Product("Prod2", "Desc2" , 10,15.0, R.drawable.ic_launcher_background )

        val listProducts = listOf(product,product2,product3,product4,product5,product6,product7,product8)

        val adapter = ProductAdapter(this,listProducts)
        list.adapter = adapter

        list.setOnItemClickListener {parent, view, position, id ->
            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("product", listProducts[position])
            startActivity(intent)
        }

        btnMainMenu.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }

        txtFecha.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment{day, month, year -> onDaySelected(day, month, year)}
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun onDaySelected(day: Int, month: Int, year: Int){
        txtFecha.text = "$day/$month/$year"
    }
}