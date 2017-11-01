package com.example.arun_5540.penzone

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream


class DataBaseHandler (context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION ){


    private val cntxt = context
    private val myCR = context.contentResolver
    private val myCP = MyContentProvider()
    private val defaultDatabase = DefaultDatabase()


    override fun onCreate(db: SQLiteDatabase) {

        val CREATE_TABLE_PRODUCT  = "CREATE TABLE $TABLE_PENZONE_PRODUCT ($PRODUCT_ID  INTEGER PRIMARY KEY              , $PRODUCT_NAME TEXT   ,  $PRODUCT_BRAND TEXT   , $PRODUCT_COLOUR TEXT   ,  $PRODUCT_PRICE INTEGER,  $PRODUCT_STOCKLEFT INTEGER , $PRODUCT_DESCRIPTION TEXT, $PRODUCT_IMAGE BLOB )"
        val CREATE_TABLE_USER     = "CREATE TABLE $TABLE_PENZONE_USER    ($USER_ID     INTEGER PRIMARY KEY AUTOINCREMENT, $USER_NAME    TEXT   ,  $USER_PHONE    INTEGER, $USER_EMAIL     TEXT   ,  $USER_PASSWORD TEXT    )"
        val CREATE_TABLE_BILL     = "CREATE TABLE $TABLE_PENZONE_BILL    ($BILL_ID     TEXT PRIMARY KEY                 , $USER_ID      INTEGER,  $BILL_STATUS   TEXT   , $BILL_DATE      INTEGER   ,  $BILL_AMOUNT   INTEGER, $BILL_TRANSACTIONID  TEXT   , FOREIGN KEY ($USER_ID) REFERENCES $TABLE_PENZONE_USER ($USER_ID))"
        val CREATE_TABLE_ORDER    = "CREATE TABLE $TABLE_PENZONE_ORDER   ($ORDER_ID    INTEGER PRIMARY KEY AUTOINCREMENT, $BILL_ID      TEXT   ,  $PRODUCT_ID    INTEGER, $ORDER_QUANTITY INTEGER,  FOREIGN KEY ($BILL_ID) REFERENCES $TABLE_PENZONE_BILL ($BILL_ID))"
        val CREATE_TABLE_ADDRESS  = "CREATE TABLE $TABLE_PENZONE_ADDRESS ($ADDRESS_ID  INTEGER PRIMARY KEY AUTOINCREMENT, $USER_ID      INTEGER,  $ADDRESS       TEXT   ,  FOREIGN KEY ($USER_ID) REFERENCES $TABLE_PENZONE_USER ($USER_ID))"
        val CREATE_TABLE_WISHLIST = "CREATE TABLE $TABLE_PENZONE_WISHLIST($WISHLIST_ID INTEGER PRIMARY KEY AUTOINCREMENT, $USER_ID      INTEGER,  $PRODUCT_ID    INTEGER)"
        val CREATE_TABLE_CART     = "CREATE TABLE $TABLE_PENZONE_CART    ($CART_ID     INTEGER PRIMARY KEY AUTOINCREMENT, $USER_ID      INTEGER,  $PRODUCT_ID    INTEGER, $CART_QUANTITY INTEGER) "

        db.execSQL(CREATE_TABLE_USER)
        db.execSQL(CREATE_TABLE_ADDRESS)
        db.execSQL(CREATE_TABLE_PRODUCT)
        db.execSQL(CREATE_TABLE_WISHLIST)
        db.execSQL(CREATE_TABLE_ORDER)
        db.execSQL(CREATE_TABLE_BILL)
        db.execSQL(CREATE_TABLE_CART)

        updateProduct(db)
        updateUser(db)
        insertProductImages(db)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        if(!db.isReadOnly){
            db.execSQL("PRAGMA foreign_keys =ON;")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PENZONE_PRODUCT")
    }

    private fun updateUser(db: SQLiteDatabase){
        val defaultUserList = defaultDatabase.addUser()
        for(user in defaultUserList){
            val values = ContentValues()
            values.put(USER_NAME, user.name)
            values.put(USER_PHONE, user.phoneNumber)
            values.put(USER_EMAIL, user.email)
            values.put(USER_PASSWORD, user.password)
            db.insert(DataBaseHandler.TABLE_PENZONE_USER, null, values)
        }


    }

    private fun updateProduct(db: SQLiteDatabase){
        val defaultProductList = defaultDatabase.updateProducts()

        for (product in defaultProductList) {
            val values = ContentValues()
            values.put(PRODUCT_NAME, product.name)
            values.put(PRODUCT_BRAND, product.brand)
            values.put(PRODUCT_COLOUR, product.colour)
            values.put(PRODUCT_PRICE, product.price)
            values.put(PRODUCT_STOCKLEFT, product.stockLeft)
            values.put(PRODUCT_DESCRIPTION, product.description)

            db.insert(DataBaseHandler.TABLE_PENZONE_PRODUCT,null, values)
        }
    }

    private fun insertProductImages(db: SQLiteDatabase){

        insertImagesToDatabase( R.drawable.cellogripper           , 1,  db)
        insertImagesToDatabase( R.drawable.parkerjotter           , 2,  db)
        insertImagesToDatabase( R.drawable.bullet                 , 3,  db)
        insertImagesToDatabase( R.drawable.fishersprf             , 4,  db)
        insertImagesToDatabase( R.drawable.zebramini              , 5,  db)
        insertImagesToDatabase( R.drawable.neogold                , 6,  db)
        insertImagesToDatabase( R.drawable.reynoldsjiffy          , 7,  db)
        insertImagesToDatabase( R.drawable.cellofinegrip          , 8,  db)
        insertImagesToDatabase( R.drawable.parkermatte            , 9,  db)
        insertImagesToDatabase( R.drawable.pilotv7hitehcpointrt   , 10, db)
        insertImagesToDatabase( R.drawable.cruisercwm301          , 11, db)
        insertImagesToDatabase( R.drawable.carandache             , 12, db)
        insertImagesToDatabase( R.drawable.pilotv10               , 13, db)
        insertImagesToDatabase( R.drawable.crossfranklincovey     , 14, db)
        insertImagesToDatabase( R.drawable.crosscalaischrome      , 15, db)
        insertImagesToDatabase( R.drawable.crossclassiccentury    , 16, db)
        insertImagesToDatabase( R.drawable.crossclassic3502       , 17, db)
        insertImagesToDatabase( R.drawable.crossedgeroller        , 18, db)
        insertImagesToDatabase( R.drawable.crossclick             , 19, db)
        insertImagesToDatabase( R.drawable.lapisbird              , 20, db)




    }

    private fun insertImagesToDatabase(image: Int, productId: Int , db: SQLiteDatabase){
        val img =  convertToByteArray(image)
        val values = ContentValues()
        values.put(PRODUCT_IMAGE, img)
        val selection = PRODUCT_ID + "=?"
        val selectionArgs = arrayOf(productId.toString())
        db.update(DataBaseHandler.TABLE_PENZONE_PRODUCT,values,selection,selectionArgs)
    }

    private fun convertToByteArray(image: Int): ByteArray{
        val bitmap = BitmapFactory.decodeResource(cntxt.resources,image)
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos)
        return bos.toByteArray()
    }

    internal fun getUser(phoneNumber: String): User{
        val projection = arrayOf(USER_ID, USER_NAME, USER_PHONE, USER_EMAIL, USER_PASSWORD)
        val selection  = USER_PHONE + "=?"
        val selectionArgs = arrayOf(phoneNumber)


        val cursor = myCR.query(myCP.CONTENT_PENZONE_USER_URI, projection, selection, selectionArgs, null)
        if(cursor.moveToFirst())
        {

            val name = cursor.getString(1)
            val phone = cursor.getString(2)
            val email = cursor.getString(3)
            val password = cursor.getString(4)
            val user = User(name,phone, email,password)
            cursor.close()
            return user
        }
        return User("no user","0","","")
    }

    internal fun getUserId(phone : String):Int{
        val projection = arrayOf(USER_ID)
        val selection  = USER_PHONE + "=?"
        val selectionArgs = arrayOf(phone)

        val cursor = myCR.query(myCP.CONTENT_PENZONE_USER_URI, projection, selection, selectionArgs, null)
        if(cursor.moveToFirst()){
            val userId = cursor.getInt(0)
            cursor.close()
            return userId
        }
        return -1
    }

    internal fun getUserWithId(userId: Int): User{
        val projection = arrayOf(USER_ID, USER_NAME, USER_PHONE, USER_EMAIL, USER_PASSWORD)
        val selection  = USER_ID + "=?"
        val selectionArgs = arrayOf(userId.toString())


        val cursor = myCR.query(myCP.CONTENT_PENZONE_USER_URI, projection, selection, selectionArgs, null)
        if(cursor.moveToFirst())
        {

            val name = cursor.getString(1)
            val phone = cursor.getString(2)
            val email = cursor.getString(3)
            val password = cursor.getString(4)
            val user = User(name,phone, email,password)
            cursor.close()
            return user
        }
        return User("no user","0","","")
    }

//    internal fun getAllProducts(): MutableList<Int>{
//        val projection = arrayOf( PRODUCT_ID)
//        val cursor = myCR.query(myCP.CONTENT_PENZONE_PRODUCT_URI, projection,null,null,null)
//        val listOfProduct = mutableListOf<Int>()
//        if(cursor.moveToFirst()){
//            do{
//                val id       = cursor.getInt(0)
//                listOfProduct.add(id)
//            }while (cursor.moveToNext())
//        }
//        return  listOfProduct
//    }

    internal fun getAllProductsWithImage(): MutableList<ProductImage>{
        val projection = arrayOf( PRODUCT_ID, PRODUCT_NAME, PRODUCT_BRAND, PRODUCT_COLOUR, PRODUCT_PRICE, PRODUCT_STOCKLEFT, PRODUCT_DESCRIPTION, PRODUCT_IMAGE)
        val cursor = myCR.query(myCP.CONTENT_PENZONE_PRODUCT_URI, projection,null,null,null)
        val listOfProduct = mutableListOf<ProductImage>()
        if(cursor.moveToFirst()){
            do{
                val id           = cursor.getInt(0)
                val name         = cursor.getString(1)
                val brand        = cursor.getString(2)
                val colour       = cursor.getString(3)
                val price        = cursor.getFloat(4)
                val stockLeft    = cursor.getInt(5)
                val description  = cursor.getString(6)
                val image        = cursor.getBlob(7)
                val img = BitmapFactory.decodeByteArray(image, 0, image.size)
                listOfProduct.add(ProductImage(id, name, brand, colour, price, stockLeft, description, img))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return  listOfProduct
    }

    internal fun getProduct(id: Int):Product {
        val projection = arrayOf( PRODUCT_ID, PRODUCT_NAME, PRODUCT_BRAND, PRODUCT_COLOUR, PRODUCT_PRICE,  PRODUCT_STOCKLEFT, PRODUCT_DESCRIPTION)
        val selection = PRODUCT_ID + "=?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = myCR.query(myCP.CONTENT_PENZONE_PRODUCT_URI, projection,selection,selectionArgs,null)

        if(cursor.moveToFirst()){

            val name         = cursor.getString(1)
            val brand        = cursor.getString(2)
            val colour       = cursor.getString(3)
            val price        = cursor.getFloat(4)
            val stockLeft    = cursor.getInt(5)
            val description  = cursor.getString(6)
            val product      = Product(id,name,brand,colour,price,stockLeft,description)
            cursor.close()
            return product
        }
        return Product(-1,"","","",0f,0, "")
    }


    internal fun getProductImage(productId: Int): Bitmap?{
        val projection    = arrayOf(PRODUCT_IMAGE)
        val selection     = PRODUCT_ID + "=?"
        val selectionArgs = arrayOf(productId.toString())
        val cursor        = myCR.query(myCP.CONTENT_PENZONE_PRODUCT_URI, projection, selection, selectionArgs , null)

        if(cursor.moveToFirst()){
            val img   = cursor.getBlob(0)
            val image = BitmapFactory.decodeByteArray(img, 0, img.size)
            cursor.close()
            return image
        }
        return null
    }

    internal fun deleteWish(wish_id: Int){
        val selection  = WISHLIST_ID +"=?"
        val selectionArgs = arrayOf(wish_id.toString())
        myCR.delete(myCP.CONTENT_PENZONE_WISHLIST_URI, selection, selectionArgs)
    }

    internal fun addWishList(userId: Int, productId: Int):Boolean{

        val projection = arrayOf(WISHLIST_ID)
        val selection = "$USER_ID =? and $PRODUCT_ID =?"
        val selectionArgs = arrayOf(userId.toString(), productId.toString())
        val cursor = myCR.query(myCP.CONTENT_PENZONE_WISHLIST_URI, projection, selection, selectionArgs, null)
        if(cursor.moveToFirst()){
            cursor.close()
            return false
        }
        val values = ContentValues()
        values.put(USER_ID,userId)
        values.put(PRODUCT_ID, productId)
        myCR.insert(myCP.CONTENT_PENZONE_WISHLIST_URI, values)
        return true
    }


    internal fun getUserOrderList(user_id: Int):MutableList<Int>{
        val proj = arrayOf(BILL_ID)
        val select = USER_ID + "=?"
        val selectArgs = arrayOf(user_id.toString())
        val listOfOrder = mutableListOf<Int>()

        val cur = myCR.query(myCP.CONTENT_PENZONE_BILL_URI , proj, select, selectArgs, null)

        if(cur.moveToFirst()){
           do{
               val billId = cur.getString(0)
               val projection = arrayOf(ORDER_ID)
               val selection = BILL_ID + "=?"
               val selectionArgs = arrayOf(billId)
               val cursor = myCR.query(myCP.CONTENT_PENZONE_ORDER_URI, projection, selection, selectionArgs, null)

               if(cursor.moveToFirst()){
                   do{
                       listOfOrder.add(cursor.getInt(0))
                   }while (cursor.moveToNext())
               }
               cursor.close()
           }while (cur.moveToNext())
        }
        cur.close()
        return  listOfOrder
    }

    internal fun getUserOrder(orderId: Int): Order{
        val projection    = arrayOf(ORDER_ID, BILL_ID, PRODUCT_ID, ORDER_QUANTITY)
        val selection     = ORDER_ID + "=?"
        val selectionArgs = arrayOf(orderId.toString())
        val cursor        = myCR.query(myCP.CONTENT_PENZONE_ORDER_URI, projection, selection, selectionArgs, null)
        if(cursor.moveToFirst()){
            val id        = cursor.getInt(0)
            val billId    = cursor.getString(1)
            val productId = cursor.getInt(2)
            val quantity  = cursor.getInt(3)
            val order     = Order(id,billId,productId,quantity)
            cursor.close()
            return order
        }
        return Order(0, "",0,0)
    }

    internal fun getUserWishList(user_id: Int): MutableList<WishList>{
        val projection = arrayOf(WISHLIST_ID, USER_ID, PRODUCT_ID)
        val selection = USER_ID + "=?"
        val selectionArgs = arrayOf(user_id.toString())
        val cursor = myCR.query(myCP.CONTENT_PENZONE_WISHLIST_URI, projection, selection, selectionArgs, null)

        val listOfWish = mutableListOf<WishList>()
        if(cursor.moveToFirst()){
            do{
                listOfWish.add(WishList(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2)))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return  listOfWish
    }


    internal fun addCart(userId: Int, productId: Int, quantity: Int): Boolean{

        val product = getProduct(productId)
        if(quantity > product.stockLeft){
            return false
        }else {
            val projection  = arrayOf(CART_ID, CART_QUANTITY)
            val selection = "$USER_ID =? and $PRODUCT_ID =?"
            val selectionArgs = arrayOf(userId.toString(), productId.toString())
            val cursor = myCR.query(myCP.CONTENT_PENZONE_CART_URI, projection, selection, selectionArgs, null)
            val cartId: Int
            val cartQuantity: Int
            if(cursor.moveToFirst()){
                cartId = cursor.getInt(0)
                cartQuantity = cursor.getInt(1)
                val selection1 = CART_ID + "=?"
                val selectionArgs1 = arrayOf(cartId.toString())
                val values = ContentValues()
                val updatedQuantity = if((cartQuantity + quantity) >10) 10 else  (cartQuantity + quantity)

                values.put(CART_QUANTITY, updatedQuantity)

                myCR.update(myCP.CONTENT_PENZONE_CART_URI,values, selection1, selectionArgs1)
            }else{

                val values = ContentValues()
                values.put(USER_ID, userId)
                values.put(PRODUCT_ID, productId)
                values.put(CART_QUANTITY, quantity)
                myCR.insert(myCP.CONTENT_PENZONE_CART_URI, values)
            }
            changeProductStock(productId, (-quantity))
            cursor.close()
            return true
        }

    }

     private fun changeProductStock(productId: Int, quantity: Int){
         val selection = PRODUCT_ID +"=?"
         val selectionArgs = arrayOf(productId.toString())
         val product = getProduct(productId)
         val newQuantity = product.stockLeft + quantity
         val values = ContentValues()
         values.put(PRODUCT_STOCKLEFT,newQuantity )
         myCR.update(myCP.CONTENT_PENZONE_PRODUCT_URI,values, selection, selectionArgs)
     }

    internal  fun getUserCart( userId: Int): MutableList<Cart>{
        val projection = arrayOf(CART_ID, USER_ID, PRODUCT_ID, CART_QUANTITY)
        val selection = USER_ID + "=?"
        val selectionArgs = arrayOf(userId.toString())
        val cursor = myCR.query(myCP.CONTENT_PENZONE_CART_URI, projection, selection, selectionArgs, null)

        val listOfCart = mutableListOf<Cart>()
        if(cursor.moveToFirst()){
            do{
                listOfCart.add(Cart(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3)))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return  listOfCart
    }

    internal fun removeCart(cartId: Int){

        val projection = arrayOf(PRODUCT_ID, CART_QUANTITY)
        val selection = CART_ID + "=?"
        val selectionArgs = arrayOf(cartId.toString())
        val cursor = myCR.query(myCP.CONTENT_PENZONE_CART_URI, projection, selection,selectionArgs, null)
        if(cursor.moveToFirst()){
            val productId = cursor.getInt(0)
            val cartQuantity = cursor.getInt(1)

            changeProductStock(productId, cartQuantity)
            myCR.delete(myCP.CONTENT_PENZONE_CART_URI, selection, selectionArgs)
        }
        cursor.close()
    }

    internal fun reduceCartQuantity(cartId: Int, quantity: Int){
        val projection = arrayOf(PRODUCT_ID, CART_QUANTITY)
        val selection = CART_ID + "=?"
        val selectionArgs = arrayOf(cartId.toString())
        val cursor = myCR.query(myCP.CONTENT_PENZONE_CART_URI, projection, selection,selectionArgs, null)
        if(cursor.moveToFirst()){
            val productId = cursor.getInt(0)
            val cartQuantity = cursor.getInt(1)
            val newQuantity = cartQuantity- quantity

            changeProductStock(productId, quantity)
            val values = ContentValues()
            values.put(CART_QUANTITY, newQuantity)
            myCR.update(myCP.CONTENT_PENZONE_CART_URI,values, selection, selectionArgs )
        }
        cursor.close()
    }

//    fun addAddress( address: Address){
//        val values = ContentValues()
//        values.put(USER_ID, address.user_id)
//        values.put(ADDRESS, address.address)
//        myCR.insert(myCP.CONTENT_PENZONE_ADDRESS_URI,values)
//    }

    internal fun addOrder(billId: String, productId: Int, quantity: Int){
        val values = ContentValues()
        values.put(BILL_ID, billId)
        values.put(PRODUCT_ID, productId)
        values.put(ORDER_QUANTITY, quantity)
        myCR.insert(myCP.CONTENT_PENZONE_ORDER_URI, values)
    }

    internal fun addBill(bill: Bill){
        val values = ContentValues()
        values.put(BILL_ID, bill.id)
        values.put(USER_ID, bill.user_id)
        values.put(BILL_DATE, bill.date)
        values.put(BILL_TRANSACTIONID, bill.transaction_id)
        values.put(BILL_STATUS,bill.status.toString())
        values.put(BILL_AMOUNT, bill.amount)

        myCR.insert(myCP.CONTENT_PENZONE_BILL_URI, values)
    }

    internal fun getBill(billId: String): Bill{
        val projection = arrayOf(BILL_ID, USER_ID, BILL_STATUS, BILL_DATE, BILL_AMOUNT, BILL_TRANSACTIONID)
        val selection = BILL_ID + "=?"
        val selectionArgs = arrayOf(billId)
        val cursor = myCR.query(myCP.CONTENT_PENZONE_BILL_URI, projection, selection, selectionArgs, null)


        if(cursor.moveToFirst()){

            val status  = when(cursor.getString(2)){
                "true"->true
                "false"->false
                else-> false
            }
            val bill = Bill(cursor.getString(0),cursor.getInt(1),status,cursor.getLong(3), cursor.getFloat(4),cursor.getString(5))
            cursor.close()
            return bill
        }
        return  Bill(" ", 0, false, 0, 0f, " ")
    }


    companion object {
        val DB_NAME = "penZone.db"
        val DB_VERSION = 1

        // tables
        val TABLE_PENZONE_USER = "penzone_user"
        val TABLE_PENZONE_ADDRESS = "penzone_address"
        val TABLE_PENZONE_PRODUCT = "penzone_product"
        val TABLE_PENZONE_WISHLIST = "penzone_wishlist"
        val TABLE_PENZONE_ORDER = "penzone_order"
        val TABLE_PENZONE_BILL = "penzone_bill"
        val TABLE_PENZONE_CART = "penzone_cart"


        // user table columns
        val USER_ID ="user_ID"
        val USER_NAME ="user_name"
        val USER_PHONE = "user_phoneNumber"
        val USER_EMAIL = "user_email"
        val USER_PASSWORD = "user_password"


        // address table
        val ADDRESS_ID = "address_id"
        val ADDRESS = "address"


        // product TABLE Columns
        val PRODUCT_ID = "product_id"
        val PRODUCT_NAME = "product_name"
        val PRODUCT_BRAND = "product_brand"
        val PRODUCT_COLOUR = "product_colour"
        val PRODUCT_PRICE = "product_price"
        val PRODUCT_STOCKLEFT = "product_stockleft"
        val PRODUCT_DESCRIPTION = "product_description"
        val PRODUCT_IMAGE = "product_image"


        // wishlist Table
        val WISHLIST_ID = "wishlist_id"

        //order table columns
        val ORDER_ID = "order_id"
        val ORDER_QUANTITY = "order_quantity"

        //bill table
        val BILL_ID = "bill_id"
        val BILL_STATUS = "bill_status"
        val BILL_DATE = "bill_date"
        val BILL_AMOUNT = "bill_amount"
        val BILL_TRANSACTIONID = "bill_transactionid"


        //cart table
        val CART_ID = "cart_id"
        val CART_QUANTITY = "cart_quantity"


    }
}




