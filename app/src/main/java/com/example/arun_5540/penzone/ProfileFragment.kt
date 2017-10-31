package com.example.arun_5540.penzone


import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val userName = view.findViewById<TextView>(R.id.userName)
        val userPhone = view.findViewById<TextView>(R.id.userPhone)
        val userEmail = view.findViewById<TextView>(R.id.userEmail)

        val sharedPref: SharedPreferences = activity.application.getSharedPreferences("logIn", Context.MODE_PRIVATE)
        val phone = sharedPref.getString("user_phone", "guest")
        if(phone != "guest"){
            val db = DataBaseHandler(activity)
            val user =  db.getUser(phone)

            userName.text  = user.name
            userPhone.text = user.phoneNumber
            userEmail.text = user.email
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_search).isVisible = false
    }
}
