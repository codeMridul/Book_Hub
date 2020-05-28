package com.moriarity_code.practiceapp2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moriarity_code.practiceapp2.R
import com.moriarity_code.practiceapp2.database.BookEntity
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context, val bookList: List<BookEntity>) : RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_favourite_single_element, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val book = bookList[position]

        holder.bookName.text = book.bookName
        holder.bookAuthor.text = book.bookAuthor
        holder.bookPrice.text = book.bookPrice
        holder.bookRating.text = book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.ic_book).into(holder.bookImage)
    }

    class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var bookName: TextView = view.findViewById(R.id.txtBookNameFav)
        var bookAuthor: TextView = view.findViewById(R.id.txtAuthorNameFav)
        var bookPrice: TextView = view.findViewById(R.id.txtBookPriceFav)
        var bookRating: TextView = view.findViewById(R.id.txtRatingFav)
        var bookImage: ImageView = view.findViewById(R.id.imgBookCoverFav)
        var llContent: LinearLayout = view.findViewById(R.id.llContentFav)
    }
}