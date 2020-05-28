package com.moriarity_code.practiceapp2.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.moriarity_code.practiceapp2.R
import com.moriarity_code.practiceapp2.database.BookDatabase
import com.moriarity_code.practiceapp2.database.BookEntity
import com.moriarity_code.practiceapp2.util.ConnectionManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_description.*
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {
    lateinit var bookName: TextView
    lateinit var bookAuthor: TextView
    lateinit var bookPrice: TextView
    lateinit var bookRating: TextView
    lateinit var bookImage: ImageView
    lateinit var bookDesc: TextView
    lateinit var btnAddtoFav: Button
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    var bookId: String? = "100"
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        //setToolbar()

        bookName = findViewById(R.id.txtBookName)
        bookAuthor = findViewById(R.id.txtAuthorName)
        bookPrice = findViewById(R.id.txtBookCost)
        bookRating = findViewById(R.id.txtBookRating)
        bookImage = findViewById(R.id.imgBookCover)
        bookDesc = findViewById(R.id.txtBookDescription)
        btnAddtoFav = findViewById(R.id.btnAddToFav)
        progressBar = findViewById(R.id.progressBar)
        progressLayout = findViewById(R.id.progressLayout)

        toolbar = findViewById(R.id.toolbarDescription)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Description"

        progressLayout.visibility = View.VISIBLE

        if (intent != null) {
            bookId = intent.getStringExtra("bookId")
        } else {
            finish()
            Toast.makeText(
                this@DescriptionActivity,
                "Some Unexpected error occurred",
                Toast.LENGTH_SHORT
            ).show()
        }
        if (ConnectionManager().checkConnectivity(this@DescriptionActivity))// to check whether there is a internet connection: if yes then make request
        {
            val queue = Volley.newRequestQueue(this@DescriptionActivity)
            val url = "http://13.235.250.119/v1/book/get_book/"

            val jsonParams = JSONObject()
            jsonParams.put("book_id", bookId)

            val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonParams,
                Response.Listener
                {
                    try        // handling the JSON Exception
                    {
                        progressLayout.visibility = View.GONE
                        val success = it.getBoolean("success")
                        println(success)
                        if (success) {
                            val bookJsonObject = it.getJSONObject("book_data")
                            val bookImageUrl = bookJsonObject.getString("image")
                            Picasso.get().load(bookJsonObject.getString("image"))
                                .error(R.drawable.ic_book).into(imgBookCover)
                            bookName.text = bookJsonObject.getString("name")
                            bookAuthor.text = bookJsonObject.getString("author")
                            bookPrice.text = bookJsonObject.getString("price")
                            bookRating.text = bookJsonObject.getString("rating")
                            bookDesc.text = bookJsonObject.getString("description")

                            val bookEntity = BookEntity(
                                bookId?.toInt() as Int,
                                txtBookName.text.toString(),
                                txtAuthorName.text.toString(),
                                txtBookCost.text.toString(),
                                txtBookRating.text.toString(),
                                txtBookDescription.text.toString(),
                                bookImageUrl
                            )
                            val checkFav = DBAsyncTask(applicationContext, bookEntity, 1).execute()
                            val isFav = checkFav.get()
                            if (isFav) {
                                btnAddtoFav.text = "Remove from Favourites"
                                val nofavColor = ContextCompat.getColor(
                                    applicationContext,
                                    R.color.colorFavourite
                                )
                                btnAddtoFav.setBackgroundColor(nofavColor)
                            } else {
                                btnAddtoFav.text = "Add to Favourites"
                                val nofavColor =
                                    ContextCompat.getColor(applicationContext, R.color.colorPrimary)
                                btnAddtoFav.setBackgroundColor(nofavColor)
                            }
                            btnAddtoFav.setOnClickListener {
                                if (!DBAsyncTask(applicationContext, bookEntity, 1).execute()
                                        .get()
                                ) {
                                    val async =
                                        DBAsyncTask(applicationContext, bookEntity, 2).execute()
                                    val result = async.get()
                                    if (result) {
                                        Toast.makeText(
                                            this@DescriptionActivity,
                                            "Added to favourites",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        btnAddtoFav.text = "Remove from Favourites"
                                        val nofavColor = ContextCompat.getColor(
                                            applicationContext,
                                            R.color.colorFavourite
                                        )
                                        btnAddtoFav.setBackgroundColor(nofavColor)
                                    } else {
                                        Toast.makeText(
                                            this@DescriptionActivity,
                                            "Some error occured",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    val async =
                                        DBAsyncTask(applicationContext, bookEntity, 3).execute()
                                    val result = async.get()
                                    if (result) {
                                        Toast.makeText(
                                            this@DescriptionActivity,
                                            "Removed from favourites",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        btnAddtoFav.text = "Add to Favourites"
                                        val nofavColor = ContextCompat.getColor(
                                            applicationContext,
                                            R.color.colorPrimary
                                        )
                                        btnAddtoFav.setBackgroundColor(nofavColor)
                                    } else {
                                        Toast.makeText(
                                            this@DescriptionActivity,
                                            "Some error occured",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                }
                            }
                        } else {
                            Toast.makeText(
                                this@DescriptionActivity,
                                "Some Error has occured!!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Something Unexpected has occured!!!\n ${e.message} ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener
                {
                    //here we will handle the errors
                    Toast.makeText(
                        this@DescriptionActivity,
                        "Network connection is unstable",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
                    headers["token"] = "ff94a4233d0da6"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        } else {
            val dialog = AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Failed!!")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                this@DescriptionActivity?.finish()
            }
            dialog.setNegativeButton("Exit") { text, listener ->
                ActivityCompat.finishAffinity(this@DescriptionActivity)

            }
            dialog.create()
            dialog.show()
        }
    }
    override fun onBackPressed()
    {
        val intent = Intent(this@DescriptionActivity, MainActivity::class.java)
        startActivity(intent)
    }
    override fun onPause() {
        super.onPause()
        finish()
    }

    /*private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Description"
    }*/
    class DBAsyncTask(val context: Context, val bookEntity: BookEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        /*
        Mode 1 -> Check DB is the book is favourite or not
        Mode 2 -> Save the book into DB as favourites
        Mode 3 -> Remove the book from the favourites
         */

        val db = Room.databaseBuilder(context, BookDatabase::class.java, "books-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    val book: BookEntity? = db.bookDao().getBookById(bookEntity.book_id.toString())
                    db.close()
                    return book != null
                }
                2 -> {
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }
                3 -> {
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true
                }

            }
            return false
        }

    }
}
