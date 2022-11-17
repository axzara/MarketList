package com.example.marketlist

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CreateProduct : AppCompatActivity() {
    lateinit var currentPhotoPath: String
    private var photoURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_product)

        val btnCamera = findViewById<ImageButton>(R.id.btnCamera)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val nombre = findViewById<EditText>(R.id.editTxtNombreProd)
        val cantidad = findViewById<EditText>(R.id.editTxtCantidadProd)
        val imagen = findViewById<ImageView>(R.id.editImgProd)
        val descripcion = findViewById<EditText>(R.id.editTxtDescripcion)
        val precio = findViewById<EditText>(R.id.editTxtPrecio)

        var idProduct: Int? = null
        if(intent.hasExtra("product")){
            val prod = intent.extras?.getSerializable("product") as Product

            nombre.setText(prod.nombre)
            cantidad.setText(prod.cantidad.toString())
            descripcion.setText(prod.descripcion)
            precio.setText(prod.precio.toString())
            idProduct = prod.idProduct

            val photoUri = ImageController.getPhotoUri(this,idProduct.toLong())
            imagen.setImageURI(photoUri)
        }

        val database = AppDatabase.getDatabase(this)

        btnGuardar.setOnClickListener{
            val nom = nombre.text.toString()
            val cant = cantidad.text.toString().toInt()
            //val img = imagen.drawable.toString().toInt()
            val desc = descripcion.text.toString()
            val price = precio.text.toString().toDouble()

            val product = Product(nom, desc, cant, price, R.drawable.ic_launcher_background)

            if(idProduct!=null){
                CoroutineScope(Dispatchers.IO).launch {
                    product.idProduct=idProduct
                    database.products().update(product)
                    photoURI?.let {
                        val intent = Intent()
                        setResult(Activity.RESULT_OK, intent)
                        ImageController.saveImage(this@CreateProduct, idProduct.toLong(), it)
                    }
                    this@CreateProduct.finish()
                }
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    val id = database.products().insertAll(product)[0]
                    photoURI?.let {
                        ImageController.saveImage(this@CreateProduct, id, it)
                    }
                    this@CreateProduct.finish()
                }
            }
        }

        //Evento onClick del boton de camara
        btnCamera.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                //Validamos que hay una actividad de camara para manipular el intent
                takePictureIntent.resolveActivity(packageManager)?.also {
                    //Creamos el archivo donde la foto debe estar
                    val photoFile: File? = try {
                        createImageFile()

                    }catch (ex: IOException){
                        //Error al crear la foto
                        null
                    }
                    //Si el archivo se ha creado correctamente
                    photoFile?.also {
                        photoURI = FileProvider.getUriForFile(
                            this,
                            "com.example.android.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        cameraLauncher.launch(takePictureIntent)
                    }
                }
            }

        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result: ActivityResult ->
        //validamos que los datos sean correctos
        if (result.resultCode == Activity.RESULT_OK){
            //recibimos toda la informacion
            //val intent = result.data
            //val imageBitMap = BitmapFactory.decodeFile(currentPhotoPath)
            //val imageCamera =
                findViewById<ImageView>(R.id.editImgProd).setImageURI(photoURI)
            //ponemos la imagen en el imageview
            //imageCamera.setImageBitmap(imageBitMap)
            Toast.makeText(this, "Imagen Guardada en " + currentPhotoPath , Toast.LENGTH_SHORT).show()
        }

    }

    //Metodo para crear un archivo temporal de la imagen tomada por la cámara
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val imageName : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            //Le damos el formato al nombre del archivo
            "JPEG_${imageName}_",
            ".jpg",
            storageDir
        ).apply {
            //Guardamos un archivo:
            currentPhotoPath = absolutePath
        }
    }
}