package com.example.arun_5540.penzone

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


class WishListAdapter( private val mWishList:  MutableList<WishList>, context: Context) : RecyclerView.Adapter<WishListAdapter.WishHolder>(){


    val cntxt = context

    class WishHolder(v: View, context: Context): RecyclerView.ViewHolder(v){


        val cntxt1 = context
        private lateinit var  mWishList: WishList

        private val productName : TextView  = v.findViewById(R.id.nameWishList)
        private val productBrand: TextView  = v.findViewById(R.id.BrandWishList)
        private val productPrice: TextView  = v.findViewById(R.id.priceWishList)
        private val productImage: ImageView = v.findViewById(R.id.wishListImage)

        val addToCart : Button       = v.findViewById(R.id.WishToCart)
        val wishDelete : ImageButton = v.findViewById(R.id.deleteWishList)



        fun bindWishList( wishList: WishList){
            mWishList = wishList

            val sq = DataBaseHandler(cntxt1)
            val product = sq.getProduct(wishList.product_id)
            productName.text = product.name
            productBrand.text = product.brand
            productPrice.text = "Rs - ${ product.price}"
            val img = sq.getProductImage(product.id)
            productImage.setImageBitmap(img)
        }
    }

    override fun onBindViewHolder(holder: WishHolder, position: Int) {
        val wish = mWishList[position]
        holder.bindWishList(wish)

        holder.wishDelete.setOnClickListener{
            val db =DataBaseHandler(cntxt)
            db.deleteWish(wish.id)
            removeItem(holder.adapterPosition)
        }

        holder.addToCart.setOnClickListener{
            val db =DataBaseHandler(cntxt)
            val didItAdd = db.addCart(wish.user_id, wish.product_id, 1)
            when(didItAdd){
                true->{db.deleteWish(wish.id)
                    removeItem(holder.adapterPosition)
                    Toast.makeText(cntxt, " added to cart", Toast.LENGTH_SHORT).show()
                }
                false->{
                    Toast.makeText(cntxt, "cannot add to Cart, not enough stock", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun getItemCount() = mWishList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishHolder{
        val  inflatedView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.wishlist_card,parent,false)
        return WishHolder(inflatedView, parent.context)
    }


    private fun  removeItem(position:Int)
    {
        mWishList.removeAt(position)
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mWishList.size)
    }

}