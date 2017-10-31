package com.example.arun_5540.penzone


data class User(
        val name: String,
        val phoneNumber: String,
        val email: String,
        val password: String
)

data class Address(
        val id: Int,
        val user_id: Int,
        val address: String
)

data class Product(
        val id: Int,
        val name:String,
        val brand: String,
        val colour: String,
        val price: Float,
        val stockLeft: Int,
        val description: String

)

data class Cart(
        val id: Int,
        val user_id: Int,
        val product_id: Int,
        var quantity: Int

)


data class WishList(
        val id: Int,
        val user_id: Int,
        val product_id: Int
)

data class  Order(
        val id: Int,
        val bill_id: String,
        val productId: Int,
        val quantity: Int
)

data class Bill(
        val id: String,
        val user_id: Int,
        val status: Boolean,
        val date: Long,
        val amount: Float,
        val transaction_id: String

)


