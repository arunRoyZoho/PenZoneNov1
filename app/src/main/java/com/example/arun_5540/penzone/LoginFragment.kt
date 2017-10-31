package com.example.arun_5540.penzone

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class LoginFragment : Fragment() {





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



            val mobileBox = view.findViewById<EditText>(R.id.editMobile)
            val passwordBox = view.findViewById<EditText>(R.id.editPasswordLogin)
            val buttonContinue = view.findViewById<Button>(R.id.buttonContinue)
            val buttonSkip = view.findViewById<Button>(R.id.buttonSkip)


            mobileBox.hint = " Mobile Number"
            passwordBox.hint = "Password"


            buttonContinue.setOnClickListener {
                if (mobileBox.text.isEmpty() || passwordBox.text.isEmpty()) {
                    Toast.makeText(activity, "Cannot Leave Fields Empty", Toast.LENGTH_SHORT).show()
                } else {

                    val db = DataBaseHandler(activity)
                    val user = db.getUser(mobileBox.text.toString())

                    if (user.password == passwordBox.text.toString()) {
                        Toast.makeText(activity, " welcome ${user.name}", Toast.LENGTH_LONG).show()

                        val sharedPref: SharedPreferences = activity.application.getSharedPreferences("logIn", Context.MODE_PRIVATE)

                        val editor: SharedPreferences.Editor = sharedPref.edit()
                        editor.putString("user_phone", mobileBox.text.toString()).apply()
                        Log.d("entering pref", "written to preference")


                        val fr1 = activity as loginFragmentActions
                        fr1.continueToProducts()
                    } else {
                        Toast.makeText(activity, "incorrect Password", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            buttonSkip.setOnClickListener {


                val sharedPref: SharedPreferences = activity.application.getSharedPreferences("logIn", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPref.edit()
                editor.putString("user_phone", "guest").apply()
                val fr2 = activity as loginFragmentActions
                fr2.continueToProducts()

            }

    }





    interface loginFragmentActions {

        fun continueToProducts() {}

    }
}

