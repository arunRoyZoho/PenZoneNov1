package com.example.arun_5540.penzone

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri

class MyContentProvider : ContentProvider() {

    private  val AUTHORITY            = "com.example.arun_5540.penzone.MyContentProvider"
    private  val PENZONE_USER_URL     = "content://$AUTHORITY/${DataBaseHandler.TABLE_PENZONE_USER}"
    private  val PENZONE_ADDRESS_URL  = "CONTENT://$AUTHORITY/${DataBaseHandler.TABLE_PENZONE_ADDRESS}"
    private  val PENZONE_PRODUCT_URL  = "content://$AUTHORITY/${DataBaseHandler.TABLE_PENZONE_PRODUCT}"
    private  val PENZONE_WISHLIST_URL = "content://$AUTHORITY/${DataBaseHandler.TABLE_PENZONE_WISHLIST}"
    private  val PENZONE_ORDER_URL    = "content://$AUTHORITY/${DataBaseHandler.TABLE_PENZONE_ORDER}"
    private  val PENZONE_BILL_URL     = "content://$AUTHORITY/${DataBaseHandler.TABLE_PENZONE_BILL}"
    private  val PENZONE_CART_URL     = "content://$AUTHORITY/${DataBaseHandler.TABLE_PENZONE_CART}"


    val CONTENT_PENZONE_USER_URI:     Uri = Uri.parse(PENZONE_USER_URL)
    val CONTENT_PENZONE_ADDRESS_URI:  Uri = Uri.parse(PENZONE_ADDRESS_URL)
    val CONTENT_PENZONE_PRODUCT_URI:  Uri = Uri.parse(PENZONE_PRODUCT_URL)
    val CONTENT_PENZONE_WISHLIST_URI: Uri = Uri.parse(PENZONE_WISHLIST_URL)
    val CONTENT_PENZONE_ORDER_URI:    Uri = Uri.parse(PENZONE_ORDER_URL)
    val CONTENT_PENZONE_BILL_URI:     Uri = Uri.parse(PENZONE_BILL_URL)
    val CONTENT_PENZONE_CART_URI:     Uri = Uri.parse(PENZONE_CART_URL)

    private val PENZONE_USER      = 1
    private val PENZONE_ADDRESS   = 2
    private val PENZONE_PRODUCT   = 3
    private val PENZONE_WISHLIST  = 4
    private val PENZONE_ORDER     = 5
    private val PENZONE_BILL      = 6
    private val PENZONE_CART      = 7



    private val uri_matcher =UriMatcher(UriMatcher.NO_MATCH)


    init {
        uri_matcher.addURI( AUTHORITY, DataBaseHandler.TABLE_PENZONE_USER    , PENZONE_USER)
        uri_matcher.addURI( AUTHORITY, DataBaseHandler.TABLE_PENZONE_ADDRESS , PENZONE_ADDRESS)
        uri_matcher.addURI( AUTHORITY, DataBaseHandler.TABLE_PENZONE_PRODUCT , PENZONE_PRODUCT)
        uri_matcher.addURI( AUTHORITY, DataBaseHandler.TABLE_PENZONE_WISHLIST, PENZONE_WISHLIST)
        uri_matcher.addURI( AUTHORITY, DataBaseHandler.TABLE_PENZONE_ORDER   , PENZONE_ORDER)
        uri_matcher.addURI( AUTHORITY, DataBaseHandler.TABLE_PENZONE_BILL    , PENZONE_BILL)
        uri_matcher.addURI( AUTHORITY, DataBaseHandler.TABLE_PENZONE_CART    , PENZONE_CART)
    }

    var db: DataBaseHandler? = null






    override fun insert(uri: Uri, values: ContentValues?): Uri? {

        val uriType: Int = uri_matcher.match(uri)
        val sqDB: SQLiteDatabase = db!!.writableDatabase

        var id= when(uriType){
            PENZONE_USER      -> sqDB.insert(DataBaseHandler.TABLE_PENZONE_USER     , null, values)
            PENZONE_ADDRESS   -> sqDB.insert(DataBaseHandler.TABLE_PENZONE_ADDRESS  , null, values)
            PENZONE_PRODUCT   -> sqDB.insert(DataBaseHandler.TABLE_PENZONE_PRODUCT  , null, values)
            PENZONE_WISHLIST  -> sqDB.insert(DataBaseHandler.TABLE_PENZONE_WISHLIST , null, values)
            PENZONE_ORDER     -> sqDB.insert(DataBaseHandler.TABLE_PENZONE_ORDER    , null, values)
            PENZONE_BILL      -> sqDB.insert(DataBaseHandler.TABLE_PENZONE_BILL     , null, values)
            PENZONE_CART      -> sqDB.insert(DataBaseHandler.TABLE_PENZONE_CART     , null, values)
        else -> throw UnsupportedOperationException ("  uri Unknown "+ uri)
        }
        context.contentResolver.notifyChange(uri, null)
        return Uri.parse(DataBaseHandler.TABLE_PENZONE_USER+ "/" + id)
    }



    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {

        val uriType: Int = uri_matcher.match(uri)
        val sqDB = db!!.writableDatabase

        val id = when(uriType) {
            PENZONE_ADDRESS  ->{sqDB.delete(DataBaseHandler.TABLE_PENZONE_ADDRESS , selection, selectionArgs)}
            PENZONE_WISHLIST ->{sqDB.delete(DataBaseHandler.TABLE_PENZONE_WISHLIST, selection, selectionArgs)}
            PENZONE_CART     ->{sqDB.delete(DataBaseHandler.TABLE_PENZONE_CART    , selection, selectionArgs)}
            else -> throw UnsupportedOperationException ("  uri Unknown "+ uri)
        }
        return  id
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate(): Boolean {
        db = DataBaseHandler(context)
        return false
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val queryBuilder = SQLiteQueryBuilder()
        val uriType = uri_matcher.match(uri)

        when(uriType){
            PENZONE_USER     ->{ queryBuilder.tables = DataBaseHandler.TABLE_PENZONE_USER }
            PENZONE_ADDRESS  ->{ queryBuilder.tables = DataBaseHandler.TABLE_PENZONE_ADDRESS }
            PENZONE_PRODUCT  ->{ queryBuilder.tables = DataBaseHandler.TABLE_PENZONE_PRODUCT }
            PENZONE_WISHLIST ->{ queryBuilder.tables = DataBaseHandler.TABLE_PENZONE_WISHLIST }
            PENZONE_BILL     ->{ queryBuilder.tables = DataBaseHandler.TABLE_PENZONE_BILL}
            PENZONE_ORDER    ->{ queryBuilder.tables = "${DataBaseHandler.TABLE_PENZONE_ORDER} NATURAL JOIN ${DataBaseHandler.TABLE_PENZONE_PRODUCT}" }
            PENZONE_CART     ->{ queryBuilder.tables = DataBaseHandler.TABLE_PENZONE_CART}
            else ->throw UnsupportedOperationException("Query; Uri Unknown" + uri)
        }

        val cursor = queryBuilder.query( db?.readableDatabase, projection, selection, selectionArgs, null, null, sortOrder)
        cursor.setNotificationUri(context.contentResolver, uri)
        return cursor
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        val uriType = uri_matcher.match(uri)
        val sqDB = db!!.writableDatabase

        var rowsUpdated = when(uriType){
            PENZONE_USER      -> sqDB.update(DataBaseHandler.TABLE_PENZONE_USER     , values, selection, selectionArgs)
            PENZONE_ADDRESS   -> sqDB.update(DataBaseHandler.TABLE_PENZONE_ADDRESS  , values, selection, selectionArgs)
            PENZONE_PRODUCT   -> sqDB.update(DataBaseHandler.TABLE_PENZONE_PRODUCT  , values, selection, selectionArgs)
            PENZONE_WISHLIST  -> sqDB.update(DataBaseHandler.TABLE_PENZONE_WISHLIST , values, selection, selectionArgs)
            PENZONE_ORDER     -> sqDB.update(DataBaseHandler.TABLE_PENZONE_ORDER    , values, selection, selectionArgs)
            PENZONE_BILL      -> sqDB.update(DataBaseHandler.TABLE_PENZONE_BILL     , values, selection, selectionArgs)
            PENZONE_CART      -> sqDB.update(DataBaseHandler.TABLE_PENZONE_CART     , values, selection, selectionArgs)
            else ->throw IllegalArgumentException("URI Unknown Uri:" + uri)
        }
        context.contentResolver.notifyChange(uri,null)
        return  rowsUpdated
    }
}
