package com.example.marketlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ProductAdapter(private val mContext: Context, private val listProducts: List<Product>): ArrayAdapter<Product>(mContext,0,listProducts) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_product,parent,false)
        val product = listProducts[position]
        layout.findViewById<TextView>(R.id.txtNombre).text = product.nombre
        layout.findViewById<TextView>(R.id.txtCantidad).text = "${product.cantidad} pzs"
        val photoUri = ImageController.getPhotoUri(mContext,product.idProduct.toLong())
        layout.findViewById<ImageView>(R.id.imageView).setImageURI(photoUri)


        return layout
    }
}