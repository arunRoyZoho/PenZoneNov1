package com.example.arun_5540.penzone

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ProductListAdapter(private val mCartList: MutableList<Cart>) : RecyclerView.Adapter<ProductListAdapter.OrderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.bill_card, parent, false)
        return OrderHolder(view,parent.context)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        val cart = mCartList[position]
        holder.bindOrder(cart)
    }

    override fun getItemCount() =mCartList.size

    inner class OrderHolder(mView: View, context: Context) : RecyclerView.ViewHolder(mView) {
        val cntxt1 = context
        private lateinit var  mCart: Cart

        private val name     = mView.findViewById<TextView>(R.id.productNameBill)

        private val quantity = mView.findViewById<TextView>(R.id.productQuantityBill)
        private val amount   = mView.findViewById<TextView>(R.id.productPriceBill)



        fun bindOrder( cart: Cart) {
            mCart = cart

            val sq = DataBaseHandler(cntxt1)
            val product = sq.getProduct(cart.product_id)
            name.text = product.name
            quantity.text= cart.quantity.toString()
            val amnt: Float = product.price * cart.quantity
            amount.text = amnt.toString()
        }
    }
}

