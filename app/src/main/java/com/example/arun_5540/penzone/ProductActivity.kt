package com.example.arun_5540.penzone

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*

class ProductActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val productName         = findViewById(R.id.ProfileName)   as TextView
        val productBrand        = findViewById(R.id.ProfileBrandInput) as TextView
        val productColour       = findViewById(R.id.ProfileColourInput) as TextView
        val productPrice        = findViewById(R.id.ProfilePriceInput) as TextView
        val productStockLeft    = findViewById(R.id.ProfileStockLeftInput) as TextView
        val buttonAddToCart     = findViewById(R.id.ProceedToCart) as Button
        val buttonAddToWishList = findViewById(R.id.ProceedTowishlist) as Button
        val quantitySpinner     = findViewById(R.id.spinner) as Spinner
        val description         = findViewById(R.id.description) as TextView
        val productImage        = findViewById(R.id.imageView3)  as ImageView

        val intent = intent
        val id:Int = intent.getIntExtra("product_Id",-1)
        val db     = DataBaseHandler(this)
        val product:Product = db.getProduct(id)
        val img = db.getProductImage(id)

        productName.text      = product.name
        productBrand.text     = product.brand
        productColour.text    = product.colour
        productPrice.text     = "Rs- ${product.price}  "
        productStockLeft.text = product.stockLeft.toString()
        description.text       = product.description
        if(img != null){
            productImage.setImageBitmap(img)
        }


        updateSpinner(quantitySpinner, product)


        buttonAddToCart.setOnClickListener{
            if (quantitySpinner.selectedItem == null){
                Toast.makeText(this, "  product:${product.name}  is out of stock ", Toast.LENGTH_LONG).show()
            }else{
                val sharedPref: SharedPreferences = application.getSharedPreferences("logIn", Context.MODE_PRIVATE)
                val userPhone = sharedPref.getString("user_phone", "guest")
                if(userPhone == "guest"){
                    Toast.makeText(this, " Login to add to Cart", Toast.LENGTH_SHORT).show()
                }else{
                    val user_id = db.getUserId(userPhone)
                    val didItAdd = db.addCart(user_id,id,Integer.parseInt(quantitySpinner.selectedItem.toString()))
                    when(didItAdd) {
                        true -> {
                            Toast.makeText(this, " ${product.name} added to cart ", Toast.LENGTH_LONG).show()
                            onBackPressed()
                        }
                        false -> {
                            Toast.makeText(this, " ${product.name} does not have enough stock ", Toast.LENGTH_LONG).show()
                            updateSpinner(quantitySpinner, product)
                        }
                    }
                }
            }
        }


        buttonAddToWishList.setOnClickListener{
            val sharedPref: SharedPreferences = application.getSharedPreferences("logIn", Context.MODE_PRIVATE)
            val userPhone = sharedPref.getString("user_phone", "guest")
            if(userPhone == "guest"){
                Toast.makeText(this, " Login to add to Wish List", Toast.LENGTH_SHORT).show()
            }else {
                val user_id = db.getUserId(userPhone)
                val didItAdd = db.addWishList(user_id, product.id)
                when(didItAdd){
                    true ->{ Toast.makeText(this, " ${product.name} added to wish List "  , Toast.LENGTH_LONG).show()}
                    false->{ Toast.makeText(this, " ${product.name} already in wish List ", Toast.LENGTH_LONG).show()}
                }
                onBackPressed()
            }
        }
    }



    private fun updateSpinner(quantitySpinner: Spinner, product: Product){
        val intItems = mutableListOf<Int>()
        val upperBound = if(product.stockLeft >10) 10 else product.stockLeft
        for(i in 1..upperBound){
            intItems.add(i)
        }
        val spinnerAdapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item , intItems)
        quantitySpinner.adapter = spinnerAdapter


    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }

}
