package com.example.arun_5540.penzone


import android.app.Fragment
import android.graphics.Color


import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView



/**
 * A simple [Fragment] subclass.
 */
class BankResponseFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_bank_response, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


            val amount = view.findViewById<TextView>(R.id.AmountResponse)
            val status = view.findViewById<TextView>(R.id.billStatus)
            val date = view.findViewById<TextView>(R.id.billDate)
            val name = view.findViewById<TextView>(R.id.nameResponse)
            val billIdView = view.findViewById<TextView>(R.id.billResponse)
            val referenceId = view.findViewById<TextView>(R.id.transactionResponse)
            val buttonContinue = view.findViewById<Button>(R.id.continueResponse)

            val bund: Bundle = arguments
            val billId = bund.getString("billId")

            val db = DataBaseHandler(activity)
            val bill = db.getBill(billId)

            amount.text = bill.amount.toString()
             when (bill.status) {
                true -> {
                    status.text = "success"
                status.setTextColor(Color.parseColor("#4CAF50"))
                }
                false -> {
                    status.text = "failed"
                    status.setTextColor(Color.RED)
                }
            }

            val dateString = DateFormat.format("dd/MM/yyyy", bill.date)
            val user = db.getUserWithId(bill.user_id)

            name.text = user.name
            billIdView.text = bill.id
            referenceId.text = bill.transaction_id
            date.text = dateString


            buttonContinue.setOnClickListener {
                val fr2 = activity as BankResponseActions
                fr2.responseToMain()
            }

    }

    interface BankResponseActions{
        fun responseToMain()
    }
}
