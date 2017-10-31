package com.example.arun_5540.penzone

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class RecyclerAdapter( private val mProducts: List<Int>) :RecyclerView.Adapter<RecyclerAdapter.ProductHolder>(){



    class ProductHolder(v:View, context: Context): RecyclerView.ViewHolder(v),View.OnClickListener{

        val cntxt = context
        private  var  mProductId: Int = 0

        init {
            v.setOnClickListener(this)
        }

        private val productName:  TextView  = v.findViewById(R.id.name)
        private val productBrand: TextView  = v.findViewById(R.id.Brand)
        private val productPrice: TextView  = v.findViewById(R.id.price)
        private val productImage: ImageView = v.findViewById(R.id.productImage)

        fun bindProduct(  productId: Int){
            mProductId = productId
            val db = DataBaseHandler(cntxt)
            val product = db.getProduct(mProductId)
            productName.text = product.name
            productBrand.text = product.brand
            productPrice.text = product.price.toString()
            val img = db.getProductImage(product.id)
            productImage.setImageBitmap(img)
         }

        override fun onClick(v: View) {
            Log.d("recycler view" ,"click registered")
            val fr1 = v.context as RecyclerFragment.ProductListAction
            fr1.goToProductPage(mProductId)
        }
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val productId = mProducts[position]
        holder.bindProduct(productId)
    }


    override fun getItemCount() = mProducts.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder{
        val  inflatedView:View = LayoutInflater.from(parent.context)
                                .inflate(R.layout.product_card,parent,false)
        return ProductHolder(inflatedView, parent.context)
    }


}