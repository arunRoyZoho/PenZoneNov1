package com.example.arun_5540.penzone


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button


/**
 * A simple [Fragment] subclass.
 */
class NoProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val buttonProfileLogin = view.findViewById<Button>(R.id.ButtonProfileLogin)
        val buttonProfileSkip  = view.findViewById<Button>(R.id.ButtonProfileSkip)

        buttonProfileLogin.setOnClickListener{
            val fr1 = activity as ProfileChange
            fr1.continueToLogin()
        }

        buttonProfileSkip.setOnClickListener{
            val fr1 = activity as ProfileChange
            fr1.continueToProduct()
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_search).isVisible = false
    }

    interface ProfileChange{
        fun continueToLogin()
        fun continueToProduct()


    }
}
