package com.example.arun_5540.penzone

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class RecyclerAdapter( private val mProducts: List<ProductImage>) :RecyclerView.Adapter<RecyclerAdapter.ProductHolder>(){



    class ProductHolder(v:View): RecyclerView.ViewHolder(v),View.OnClickListener{


        private  lateinit var  mProductImage:ProductImage

        init {
            v.setOnClickListener(this)
        }

        private val productName:  TextView  = v.findViewById(R.id.name)
        private val productBrand: TextView  = v.findViewById(R.id.Brand)
        private val productPrice: TextView  = v.findViewById(R.id.price)
        private val productPhoto: ImageView = v.findViewById(R.id.productImage)

        fun bindProduct(  productImage: ProductImage){
            mProductImage     = productImage
            productName.text  = mProductImage.name
            productBrand.text = mProductImage.brand
            productPrice.text = mProductImage.price.toString()
            productPhoto.setImageBitmap(mProductImage.img)
         }

        override fun onClick(v: View) {
            Log.d("recycler view" ,"click registered")
            val fr1 = v.context as RecyclerFragment.ProductListAction
            fr1.goToProductPage(mProductImage.id)
        }
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val productImage = mProducts[position]
        holder.bindProduct(productImage)
    }


    override fun getItemCount() = mProducts.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder{
        val  inflatedView:View = LayoutInflater.from(parent.context)
                                .inflate(R.layout.product_card,parent,false)
        return ProductHolder(inflatedView)
    }


}