package com.example.arun_5540.penzone


import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast


class CartFragment: Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_cart, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val buyButton:Button = view.findViewById<Button>(R.id.button)

        val b: Bundle = arguments
        val userId = b.getInt("userId")

        val db = DataBaseHandler(activity)
        val listOfCart = db.getUserCart(userId)

        val mRecyclerView  = view.findViewById<RecyclerView>(R.id.CartRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        val  mAdapter = CartAdapter(listOfCart, activity)
        mRecyclerView.adapter = mAdapter


        buyButton.setOnClickListener{
            val updatedCartList = db.getUserCart(userId)

            if(updatedCartList.isEmpty()){

                Toast.makeText(activity,"Cart Empty", Toast.LENGTH_SHORT ).show()
            }else{
                val intent = Intent(activity, GenerateBillActivity::class.java )
                activity.finish()
                startActivity(intent)
            }


        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
       menu.findItem(R.id.action_search).isVisible = false
        menu.findItem(R.id.action_cart).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }




}

