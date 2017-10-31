package com.example.arun_5540.penzone

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class CartAdapter( private val mCart:  MutableList<Cart>, context: Context) : RecyclerView.Adapter<CartAdapter.CartHolder>(){


    val cntxt = context

    class CartHolder(v: View, context: Context): RecyclerView.ViewHolder(v){


        val cntxt1 = context
        private lateinit var  mCart: Cart
         var   price: Float = 0f


        private val productName : TextView  = v.findViewById(R.id.nameCart)
        private val productBrand: TextView  = v.findViewById(R.id.BrandCart)
        private val productPrice: TextView  = v.findViewById(R.id.priceCart)
        private val productImage: ImageView = v.findViewById(R.id.cartImage)
        private val cartAmount  : TextView  = v.findViewById(R.id.AmountCart)
        val cartDelete : ImageButton        = v.findViewById(R.id.deleteCart)
        val cartSpinner                     = v.findViewById<Spinner>(R.id.cartSpinner)






        fun bindCart( cart: Cart){
           mCart = cart
            val sq = DataBaseHandler(cntxt1)
            val product = sq.getProduct(mCart.product_id)

            productName.text = product.name
            productBrand.text = product.brand
            productPrice.text = "Rs - ${ product.price}"

            val img = sq.getProductImage(mCart.product_id)
            productImage.setImageBitmap(img)

            price = product.price


            updateSpinner(cartSpinner, cart.quantity)

        }

        fun updateSpinner(quantitySpinner: Spinner, quantity: Int){
            val intItems = mutableListOf<Int>()
            for(i in 1..quantity){
                intItems.add(i)
            }
            val spinnerAdapter = ArrayAdapter<Int>(cntxt1, android.R.layout.simple_spinner_item , intItems)

            quantitySpinner.adapter = spinnerAdapter
            quantitySpinner.setSelection((intItems.size -1))
            val totalAmount:Float = quantity * price
            cartAmount.text = "Rs - $totalAmount"
        }
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        val cart = mCart[position]

        holder.bindCart(cart)
        val db =DataBaseHandler(cntxt)

        holder.cartDelete.setOnClickListener{
            db.removeCart(cart.id)
            removeItem(holder.adapterPosition)
        }

        holder.cartSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if( holder.cartSpinner.selectedItem != cart.quantity){

                    val builder = AlertDialog.Builder(cntxt)
                    builder.setTitle(" Changing Quantity")
                    builder.setMessage(" Do you want to Reduce the Quantity")
                    builder.setCancelable(true)

                    builder.setPositiveButton(" Yes" ){
                        dialogInterface, i->
                        val newUpperBound = Integer.parseInt(holder.cartSpinner.selectedItem.toString())
                        db.reduceCartQuantity(cart.id,newUpperBound )

                        holder.updateSpinner(holder.cartSpinner,newUpperBound )
                        mCart[ holder.adapterPosition].quantity = newUpperBound
                        dialogInterface.cancel()
                    }
                    builder.setNegativeButton("No"){
                        dialogInterface, i ->
                        holder.cartSpinner.setSelection(cart.quantity-1)
                        dialogInterface.cancel()
                    }
                    builder.show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }


    override fun getItemCount() = mCart.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder{
        val  inflatedView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_card,parent,false)
        return CartHolder(inflatedView,cntxt)
    }


    private fun  removeItem(position:Int)
    {
        mCart.removeAt(position)
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mCart.size)
    }


}
