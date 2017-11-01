package com.example.arun_5540.penzone

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class LoginActivity : AppCompatActivity(), LoginFragment.loginFragmentActions {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(savedInstanceState == null) {
            val loginInitial: Fragment = LoginFragment() as Fragment
            val frMng1 = fragmentManager.beginTransaction()

            frMng1.add(R.id.registerMainFrame, loginInitial, "loginInitial").commit()
        }
    }


    override fun continueToProducts() {
      val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }


}





