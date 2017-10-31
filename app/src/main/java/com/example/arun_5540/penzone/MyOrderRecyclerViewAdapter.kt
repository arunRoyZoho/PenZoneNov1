package com.example.arun_5540.penzone

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView




class MyOrderRecyclerViewAdapter(private val mOrderList: MutableList<Int>) : RecyclerView.Adapter<MyOrderRecyclerViewAdapter.OrderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_order, parent, false)
        return OrderHolder(view,parent.context)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        val order = mOrderList[position]
        holder.bindOrder(order)
    }

    override fun getItemCount() =mOrderList.size

    inner class OrderHolder( mView: View, context: Context) : RecyclerView.ViewHolder(mView) {
        val cntxt1 = context
        private  var  mOrder: Int = 0

        private val name     = mView.findViewById<TextView>(R.id.nameOrder)
        private val brand    = mView.findViewById<TextView>(R.id.BrandOrder)
        private val quantity = mView.findViewById<TextView>(R.id.quantityOrder)
        private val amount   = mView.findViewById<TextView>(R.id.AmountOrder)
        private val image    = mView.findViewById<ImageView>(R.id.orderImage)

        fun bindOrder( orderId: Int) {
            mOrder = orderId
            val sq        = DataBaseHandler(cntxt1)
            val order     = sq.getUserOrder(orderId)
            val product   = sq.getProduct(order.productId)
            val img       = sq.getProductImage(order.productId)
            name.text     = product.name
            brand.text    = product.brand
            quantity.text = order.quantity.toString()
            image.setImageBitmap(img)
            val amnt: Float = product.price * order.quantity
            amount.text = amnt.toString()
        }
    }
}
