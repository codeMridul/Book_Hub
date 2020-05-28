package com.moriarity_code.practiceapp2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.moriarity_code.practiceapp2.model.Book

@Dao
interface BookDao
{
    @Insert
    fun insertBook(bookEntity: BookEntity)

    @Delete
    fun deleteBook(bookEntity: BookEntity)

    @Query("SELECT * FROM books")
    fun getAllBooks(): List<BookEntity>

    @Query("SELECT * FROM books WHERE book_id= :bookId")
    fun getBookById(bookId: String): BookEntity
}