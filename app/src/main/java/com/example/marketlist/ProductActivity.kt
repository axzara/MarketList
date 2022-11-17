package com.example.marketlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var product: Product
    private lateinit var productLiveData: LiveData<Product>
    private val editActivity = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val nombre = findViewById<TextView>(R.id.editTxtNombreProd)
        val cantidad = findViewById<TextView>(R.id.editTxtCantidadProd)
        val descripcion = findViewById<TextView>(R.id.txtDescripcionProd)
        val precio = findViewById<TextView>(R.id.txtPrecioProd)
        val imagen = findViewById<ImageView>(R.id.editImgProd)

        database = AppDatabase.getDatabase(this)

        val idProduct = intent.getIntExtra("id", 0)
        val photoUri = ImageController.getPhotoUri(this, idProduct.toLong())
        imagen.setImageURI(photoUri)
        productLiveData = database.products().get(idProduct)
        productLiveData.observe(this, Observer {
            product = it

            nombre.text = product.nombre
            cantidad.text = "${product.cantidad} pzs"
            descripcion.text = product.descripcion
            precio.text = "$${product.precio}"
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.edit_item ->{
                val intent = Intent(this,CreateProduct::class.java)
                intent.putExtra("product", product)
                startActivityForResult(intent, editActivity)
            }
            R.id.delete_item ->{
                productLiveData.removeObservers(this)
                CoroutineScope(Dispatchers.IO).launch {
                    database.products().delete(product)
                    ImageController.deleteImage(this@ProductActivity, product.idProduct.toLong())
                    this@ProductActivity.finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imagen = findViewById<ImageView>(R.id.editImgProd)
        when{
            requestCode == editActivity && resultCode == Activity.RESULT_OK -> {
                imagen.setImageURI(data!!.data)
            }
        }
    }
}