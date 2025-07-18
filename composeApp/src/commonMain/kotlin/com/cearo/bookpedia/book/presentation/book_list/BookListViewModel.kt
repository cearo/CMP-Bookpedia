package com.cearo.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for the Book List screen.
 *
 * This ViewModel manages the state of the book list, represented by [BookListState],
 * including the search query, selected tab, and the list of books. It processes user
 * interactions and other events via [BookListAction]s, such as clicking on a book,
 * changing the search query, or selecting a tab.
 *
 * @property _state The mutable state flow that holds the current state of the book list.
 * @property state The state flow that exposes the current state of the book list to the UI.
 */
class BookListViewModel: ViewModel() {

    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()

    fun onAction(action: BookListAction) {
        when(action) {
            is BookListAction.OnBookClick -> TODO()
            is BookListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is BookListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }
}