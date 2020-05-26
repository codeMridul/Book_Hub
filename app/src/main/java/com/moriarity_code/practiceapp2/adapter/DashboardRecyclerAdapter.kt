package com.moriarity_code.practiceapp2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moriarity_code.practiceapp2.R
import com.moriarity_code.practiceapp2.activity.DescriptionActivity
import com.moriarity_code.practiceapp2.model.Book
import com.squareup.picasso.Picasso

class DashboardRecyclerAdapter(val context: Context, val itemList: ArrayList<Book>) :
    RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {
    class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var bookName: TextView = view.findViewById(R.id.txtBookName)
        var bookAuthor: TextView = view.findViewById(R.id.txtAuthorName)
        var bookPrice: TextView = view.findViewById(R.id.txtBookCost)
        var bookRating: TextView = view.findViewById(R.id.txtBookRating)
        var bookImage: ImageView = view.findViewById(R.id.imgBookCover)
        var llContent: LinearLayout = view.findViewById(R.id.llContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_dashboard_single_row, parent, false)
        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book = itemList[position]
        holder.bookName.text = book.bookName
        holder.bookAuthor.text = book.bookAuthor
        holder.bookPrice.text = book.bookPrice
        holder.bookRating.text = book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.ic_book).into(holder.bookImage)

        holder.llContent.setOnClickListener {
            val intent = Intent(context, DescriptionActivity::class.java)
            intent.putExtra("bookId", book.bookId)
            context.startActivity(intent)
        }
    }
}