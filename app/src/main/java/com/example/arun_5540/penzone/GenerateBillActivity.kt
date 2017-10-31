package com.example.arun_5540.penzone

import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.util.*

class GenerateBillActivity : AppCompatActivity(), ProceedToBankFragment.ProceedToBankAction ,BankResponseFragment.BankResponseActions{


    private val paymentRequestCode = 1
    private val paymentResponseOk = 2
    private val paymentResponseFailed = 3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_bill)


       if(savedInstanceState == null) {
           val default = "guest"
           val sharedPref: SharedPreferences = application.getSharedPreferences("logIn", Context.MODE_PRIVATE)
           val userPhone = sharedPref.getString("user_phone", default)

           val db = DataBaseHandler(this)
           val userid = db.getUserId(userPhone)
           val args = Bundle()
           args.putInt("userId", userid)


           val proceedToBankFragment = ProceedToBankFragment() as Fragment
           proceedToBankFragment.arguments = args
           val frmng = fragmentManager.beginTransaction()
           frmng.replace(R.id.generateBillFrame, proceedToBankFragment, "showing bill").commit()

       }
    }

    override fun goToBankApp(amount: Float) {

        val merchantAccountNumber = 20162600005
        val merchantName = "penzone"
        val date = Calendar.getInstance()
        val dateInSecs = date.timeInMillis
        val order = "PZ017${dateInSecs % 1000}"
        val merchantIfSC = "SSBN0004444"
        val sendIntent = Intent()
        sendIntent.action = "SSB_PAYMENT"
        sendIntent.putExtra(Intent.EXTRA_TEXT, "payment")
        sendIntent.putExtra("merchantName", merchantName)
        sendIntent.putExtra("merchantAccountNumber", merchantAccountNumber)
        sendIntent.putExtra("amount", amount.toString())
        sendIntent.putExtra("orderNumber", order)
        sendIntent.putExtra("merchantIfsc", merchantIfSC)
        sendIntent.type = "text/plain"
        startActivityForResult(Intent.createChooser(sendIntent, "PAYMENT"), paymentRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val default = "guest"
        val sharedPref: SharedPreferences = application.getSharedPreferences("logIn", Context.MODE_PRIVATE)
        val userPhone =  sharedPref.getString("user_phone", default)

        val db = DataBaseHandler(this)
        val userid = db.getUserId(userPhone)

        val date = Calendar.getInstance()
        val dateInSeconds: Long= date.timeInMillis
        var amount:Float = -1f
        data?.let {
            amount = data.extras.getString("amount").toFloat()
        }



        val bill_id = "PNZ00$dateInSeconds"


        if (requestCode == paymentRequestCode && resultCode == paymentResponseOk) {

            var transactionId ="eeee"
            data?.let {
                transactionId = data.extras.getString("referenceNumber")
            }
            val bill = Bill(id = bill_id, user_id = userid, status = true ,date = dateInSeconds,amount = amount ,transaction_id = transactionId)
            db.addBill(bill)
            val listOfCart = db.getUserCart(userid)
            for(cart in listOfCart){
                db.addOrder(bill_id, cart.product_id,cart.quantity)
                db.removeCart(cart.id)
            }
        } else if (requestCode == paymentRequestCode && resultCode == paymentResponseFailed) {

            val responseMessage = data?.extras?.getString("responseMessage")
            Toast.makeText(this, responseMessage, Toast.LENGTH_LONG).show()
            val transactionId = "failed"
            val bill = Bill(id = bill_id,user_id = userid, status = false,date =  dateInSeconds,amount =  amount, transaction_id = transactionId)
            db.addBill(bill)
        }

        val bankResponse = BankResponseFragment() as Fragment
        val frMng = fragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("billId", bill_id)
        bankResponse.arguments = bundle

        frMng.replace(R.id.generateBillFrame, bankResponse, "showing bank Response").commit()
    }


    override fun responseToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        finish()
        startActivity(intent)

    }


    override fun onBackPressed() {

        val proceedToBkFragment = fragmentManager.findFragmentByTag("showing bill")

        if(proceedToBkFragment != null && proceedToBkFragment.isVisible){

           val builder = AlertDialog.Builder(this)
            builder.setTitle(" Aborting Transaction")
            builder.setMessage(" Do you want to Cancel this Transaction")
            builder.setCancelable(true)

            builder.setPositiveButton(" Yes" ){
                dialogInterface, i->

                val intent = Intent(this, MainActivity::class.java)
                finish()
                startActivity(intent)
                dialogInterface.cancel()

            }
            builder.setNegativeButton("No"){
                dialogInterface, i ->

                dialogInterface.cancel()
            }
            builder.show()
        }

        val bkResponse = fragmentManager.findFragmentByTag("showing bank Response")
        if(bkResponse != null && bkResponse.isVisible){
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
            startActivity(intent)

        }

    }
}
