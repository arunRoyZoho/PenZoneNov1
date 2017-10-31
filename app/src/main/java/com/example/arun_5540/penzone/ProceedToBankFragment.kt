package com.example.arun_5540.penzone


import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


/**
 * A simple [Fragment] subclass.
 */
class ProceedToBankFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_proceed_to_bank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val totalAmount = view!!.findViewById<TextView>(R.id.priceInBill)
        val makePayment = view!!.findViewById<Button>(R.id.buttonSSB)

        val b: Bundle = arguments
        val userId = b.getInt("userId")
        val db = DataBaseHandler(activity)
        val cartList = db.getUserCart(userId)

        var amountCalculated = 0f
        for (cart in cartList){
            val product = db.getProduct(cart.product_id)
            val amount:Float = cart.quantity * product.price

            amountCalculated += amount
        }

        totalAmount.text = " Total amount- Rs $amountCalculated "
        makePayment.setOnClickListener{

            val frm = activity as ProceedToBankAction
            frm.goToBankApp(amountCalculated)
        }



        val mProductView  = view!!.findViewById<RecyclerView>(R.id.productNameRecyclerView)
        mProductView.layoutManager = LinearLayoutManager(activity)
        val  mAdapter = ProductListAdapter(cartList)
        mProductView.adapter = mAdapter
    }

    interface ProceedToBankAction{
        fun goToBankApp(amount: Float)
    }

}
