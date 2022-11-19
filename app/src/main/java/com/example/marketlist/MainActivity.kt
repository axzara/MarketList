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
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    private lateinit var txtFecha : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtFecha = findViewById(R.id.txtFechaCorte)
        val btnMainMenu = findViewById<Button>(R.id.btnGoMainMenu)
        val btnMyLocations = findViewById<Button>(R.id.btnGoLocations)


        val list = findViewById<ListView>(R.id.list)

        var listProducts = emptyList<Product>()

        val database = AppDatabase.getDatabase(this)
        database.products().getAll().observe(this, Observer {
            listProducts = it
            val adapter = ProductAdapter(this,listProducts)
            list.adapter = adapter
        })

        list.setOnItemClickListener {parent, view, position, id ->
            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("id", listProducts[position].idProduct)
            startActivity(intent)
        }

        btnMainMenu.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }
        btnMyLocations.setOnClickListener(){
            val intent = Intent(this, MyLocations::class.java)
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