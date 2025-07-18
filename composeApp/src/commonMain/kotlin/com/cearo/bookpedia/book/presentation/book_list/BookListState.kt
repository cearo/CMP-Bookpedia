package com.cearo.bookpedia.book.presentation.book_list

import com.cearo.bookpedia.core.presentation.UiText
import com.cearo.bookpedia.book.domain.Book

/**
 * Represents the state of the book list screen.
 *
 * @property searchQuery The current search query entered by the user. Defaults to "Kotlin".
 * @property searchResults The list of books matching the search query. Defaults to an empty list.
 * @property favoriteBooks The list of books marked as favorites by the user. Defaults to an empty list.
 * @property isLoading Indicates whether data is currently being loaded. Defaults to `false`.
 * @property selectedTabIndex The index of the currently selected tab (e.g., "Search" or "Favorites"). Defaults to 0.
 * @property errorMessage A message to display to the user in case of an error. This property is nullable and defaults to `null` if there is no error.
 */
data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)
