package com.example.arun_5540.penzone

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class RecyclerFragment : Fragment(){

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    */

    lateinit var  mAdapter: RecyclerAdapter
    var mProductList = mutableListOf<Int>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
         super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mRecyclerView  = view.findViewById<RecyclerView>(R.id.recyclerView)

        mRecyclerView.layoutManager = GridLayoutManager(activity,2)



        val db = DataBaseHandler(activity)
        mProductList = db.getAllProducts()

        val b: Bundle = arguments
        val searchTerm = b.getString("SearchQuery")
       /* if(searchTerm != "no search"){
          val list =   mProductList.filter { it.name.contains(searchTerm) || it.brand.contains(searchTerm) }
            mProductList.clear()
            mProductList.addAll(list)
        } */
        mAdapter = RecyclerAdapter(mProductList)
        mRecyclerView.adapter= mAdapter
    }

    interface ProductListAction{
        fun goToProductPage(productId: Int)
    }

    /*override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_search).isVisible = true
    }
    */
}
