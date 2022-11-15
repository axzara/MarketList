package com.example.marketlist

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CreateProduct : AppCompatActivity() {
    lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_product)

        val btnCamera = findViewById<ImageButton>(R.id.btnCamera)

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
                        val photoURI: Uri = FileProvider.getUriForFile(
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
            val imageBitMap = BitmapFactory.decodeFile(currentPhotoPath)
            val imageCamera = findViewById<ImageView>(R.id.imgProd)
            //ponemos la imagen en el imageview
            imageCamera.setImageBitmap(imageBitMap)
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