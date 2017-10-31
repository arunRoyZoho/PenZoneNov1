package com.example.arun_5540.penzone

import android.app.AlertDialog
import android.app.Fragment
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener ,NoProfileFragment.ProfileChange, RecyclerFragment.ProductListAction{






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleIntent(intent)

        if (savedInstanceState == null) {
            val status = checkLogin()

            val default = "not found"
            if (status == default) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            val bundle = Bundle()
            bundle.putString("SearchQuery", "no search")
            val recyclerProduct = RecyclerFragment() as Fragment
            recyclerProduct.arguments = bundle
            val frgMng = fragmentManager.beginTransaction()
            frgMng.replace(R.id.mainActivityFrame, recyclerProduct, "showing products")
            frgMng.commit()


        }
            val myToolbar = findViewById(R.id.my_toolbar) as Toolbar
            setSupportActionBar(myToolbar)

            val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
            val toggle = ActionBarDrawerToggle(this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            toggle.syncState()
            val navigationView = findViewById(R.id.nav_view) as NavigationView
            navigationView.setNavigationItemSelectedListener(this)



    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            val frm = fragmentManager.findFragmentByTag("showing products")

            if(frm != null && frm.isVisible){
                val builder = AlertDialog.Builder(this)
                builder.setMessage(" Do you want to Exit")
                builder.setCancelable(true)

                builder.setPositiveButton(" Yes" ){
                    dialogInterface, i->
                    finish()
                }
                builder.setNegativeButton("No"){
                    dialogInterface, i ->
                    dialogInterface.cancel()
                }
                builder.show()
            }else{
                super.onBackPressed()
            }
        }
    }

    

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenu = menu.findItem(R.id.action_search)
        val searchView: SearchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        val closeButton = searchView.findViewById<ImageView>(R.id.search_close_btn)

        closeButton.setOnClickListener {
            val eText: EditText = findViewById(R.id.search_src_text) as EditText
            eText.text = SpannableStringBuilder("")
            searchView.setQuery("", false)
            searchView.onActionViewCollapsed()
            searchMenu.collapseActionView()
            val bundle = Bundle()
            bundle.putString("SearchQuery","no search")
            val recyclerProduct = RecyclerFragment() as Fragment
            recyclerProduct.arguments = bundle
            val frgMng = fragmentManager.beginTransaction()
            frgMng.replace(R.id.mainActivityFrame, recyclerProduct, "showing products")
            frgMng.commit()

        }
        searchView.isSubmitButtonEnabled = true
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        return if (id == R.id.action_cart) {

            val status = checkLogin()
            if(status != "guest") {

                val db = DataBaseHandler(this)
                val userid = db.getUserId(status)
                val listOfUserCart = db.getUserCart(userid)
                if(listOfUserCart.isNotEmpty()){


                    val args = Bundle()
                    args.putInt("userId", userid)
                    val recyclerCart = CartFragment() as Fragment
                    recyclerCart.arguments = args
                    val frgMng = fragmentManager.beginTransaction()
                    frgMng.replace( R.id.mainActivityFrame,recyclerCart ,"showing carts" ).addToBackStack("showing carts").commit()
                }else{

                    val emptyCartFragment = EmptyCartFragment() as Fragment
                    val frgMng = fragmentManager.beginTransaction()
                    frgMng.replace( R.id.mainActivityFrame,emptyCartFragment ,"showing empty carts" ).addToBackStack("showing empty carts").commit()
                }

            }else{
                Toast.makeText(this, " user Not logged In", Toast.LENGTH_SHORT).show()
            }

            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        when (id) {

            R.id.nav_home->{
                val recyclerFragment = fragmentManager.findFragmentByTag("showing products")

                if(recyclerFragment != null && !recyclerFragment.isVisible){
                    val bundle = Bundle()
                    bundle.putString("SearchQuery","no search")
                    val recyclerProduct = RecyclerFragment() as Fragment
                    recyclerProduct.arguments = bundle
                    val frgMng = fragmentManager.beginTransaction()
                    frgMng.replace(R.id.mainActivityFrame, recyclerProduct, "showing products")
                    frgMng.commit()
                }

            }

            R.id.nav_Profile -> {

                val status = checkLogin()
                when(status){
                    "guest" ->{
                        val noProfileFragment = NoProfileFragment() as Fragment
                        val frmng = fragmentManager.beginTransaction()
                        frmng.replace(R.id.mainActivityFrame, noProfileFragment, "empty profile fragment")
                        frmng.addToBackStack("empty profile fragment")
                        frmng.commit()

                    }
                    else ->{
                        val profileFragment = ProfileFragment() as Fragment
                        val frMng = fragmentManager.beginTransaction()
                        frMng.replace(R.id.mainActivityFrame, profileFragment, "profile")
                        frMng.addToBackStack("profile")
                        frMng.commit()
                    }
                }
            }

            R.id.nav_Orders ->{
                val status = checkLogin()
                when(status){
                    "guest" ->{
                        val noProfileFragment = NoProfileFragment() as Fragment
                        val frmng = fragmentManager.beginTransaction()
                        frmng.replace(R.id.mainActivityFrame, noProfileFragment, "empty order fragment")
                                .addToBackStack("empty order fragment").commit()
                    }
                    else ->{
                        val db = DataBaseHandler(this)
                        val userid = db.getUserId(status)
                        val listOfOrder = db.getUserOrderList(userid)
                        if(listOfOrder.isNotEmpty()){
                            val arg =Bundle()
                            arg.putInt("userId", userid)
                            val orderFragment = OrderFragment() as Fragment
                            orderFragment.arguments = arg
                            val frgMng = fragmentManager.beginTransaction()
                            frgMng.replace( R.id.mainActivityFrame,orderFragment ,"showing order List" )
                                    .addToBackStack("showing order list").commit()

                        }else{
                            val emptyCartFragment = EmptyCartFragment() as Fragment
                            val frgMng = fragmentManager.beginTransaction()
                            frgMng.replace( R.id.mainActivityFrame,emptyCartFragment ,"showing empty orders" ).addToBackStack("showing empty orders").commit()
                        }
                    }
                }
            }
            R.id.nav_WishList->{
                val status = checkLogin()
                when(status){
                    "guest" ->{
                        val noProfileFragment = NoProfileFragment() as Fragment
                        val frmng = fragmentManager.beginTransaction()
                        frmng.replace(R.id.mainActivityFrame, noProfileFragment, "empty profile fragment")
                                .addToBackStack("empty profile  fragment").commit()
                    }
                    else->{
                        val db = DataBaseHandler(this)
                        val userid = db.getUserId(status)
                        val listOfWishList = db.getUserWishList(userid)
                        if(listOfWishList.isNotEmpty()){

                            val arg =Bundle()
                            arg.putInt("userId", userid)
                            val recyclerWish = WishListFragment() as Fragment
                            recyclerWish.arguments = arg
                            val frgMng = fragmentManager.beginTransaction()
                            frgMng.replace( R.id.mainActivityFrame,recyclerWish ,"showing wish List" )
                                    .addToBackStack("showing wish list").commit()
                        }else{
                            val emptyCartFragment = EmptyCartFragment() as Fragment
                            val frgMng = fragmentManager.beginTransaction()
                            frgMng.replace( R.id.mainActivityFrame,emptyCartFragment ,"showing empty carts" ).addToBackStack("showing empty carts").commit()
                        }

                    }
                }
            }


            R.id.nav_logout ->{
                val sharedPref: SharedPreferences = application.getSharedPreferences("logIn", Context.MODE_PRIVATE)
                val status = sharedPref.getString("user_phone","no one")
                if(status == "guest"){
                    Toast.makeText(this, " User not logged in", Toast.LENGTH_SHORT).show()
                }else{
                    val editor = sharedPref.edit()
                    editor.remove("user_phone").apply()
                    editor.putString("user_phone", "guest").apply()
                    Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()

                    val bun = Bundle()
                    bun .putString("SearchQuery","no search")
                    val recyclerProduct = RecyclerFragment() as Fragment
                    recyclerProduct.arguments = bun
                    val frgMng = fragmentManager.beginTransaction()
                    frgMng.replace(R.id.mainActivityFrame, recyclerProduct, "showing products")
                    frgMng.commit()
                }
            }

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun checkLogin(): String{
        val default = "not found"
        val sharedPref: SharedPreferences = application.getSharedPreferences("logIn", Context.MODE_PRIVATE)
      return  sharedPref.getString("user_phone", default)
    }


    override fun continueToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        finish()
        startActivity(intent)
    }

    override fun continueToProduct() {
        val recyclerProduct = RecyclerFragment() as Fragment
        val bundle = Bundle()
        bundle.putString("SearchQuery", "no search")
        recyclerProduct.arguments = bundle
        val frgMng = fragmentManager.beginTransaction()
        frgMng.replace( R.id.mainActivityFrame,recyclerProduct ,"showing products" ).commit()
    }

    private fun handleIntent(intent: Intent?){
        Log.d("tag", "inside handleIntent ajsjsjjdjjdjjdjjjdjjdjj")
        if(Intent.ACTION_SEARCH == intent?.action){
            val query: String =intent.getStringExtra(SearchManager.QUERY)
            showResults(query)
        }
    }

   private fun showResults(query: String){

       val args = Bundle()
       args.putString("SearchQuery", query)
       val recyclerProduct = RecyclerFragment() as Fragment
       recyclerProduct.arguments =args
       val frgMng = fragmentManager.beginTransaction()
       frgMng.replace(R.id.mainActivityFrame, recyclerProduct, "showing products with search")
       frgMng.commit()
    }


    override fun goToProductPage( mProductId: Int) {

        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra("product_Id",mProductId)
        startActivity(intent)
    }

}


