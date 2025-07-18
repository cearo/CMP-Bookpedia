package com.cearo.bookpedia.book.presentation.book_list

import com.cearo.bookpedia.core.presentation.UiText
import com.cearo.bookpedia.book.domain.Book

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)
