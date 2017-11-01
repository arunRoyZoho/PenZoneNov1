package com.example.arun_5540.penzone


import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup



class WishListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle?): View {

       super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_wish_list, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mRecyclerView  = view.findViewById<RecyclerView>(R.id.WishListRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))

        val bundle: Bundle = arguments
        val userId = bundle.getInt("userId")
        val db = DataBaseHandler(activity)
        val mWishList = db.getUserWishList(userId)


        val  mAdapter = WishListAdapter(mWishList,activity)
        mRecyclerView.adapter = mAdapter
        }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_search).isVisible = false
    }
}



