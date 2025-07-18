package com.cearo.bookpedia.book.presentation.book_list

import com.cearo.bookpedia.book.domain.Book

/**
 * Represents the different actions that can be performed on the book list screen.
 * These actions are typically triggered by user interactions and are used to communicate
 * events from the UI layer to the presentation layer.
 *
 * This sealed interface defines a closed set of possible actions, ensuring type safety
 * and making it easier to handle all possible user interactions in a structured way.
 * Each specific action is represented by a data class that implements this interface.
 *
 * The actions defined are:
 * - `OnSearchQueryChange`: Indicates that the user has modified the search query.
 * - `OnBookClick`: Indicates that the user has clicked on a specific book in the list.
 * - `OnTabSelected`: Indicates that the user has selected a different tab (e.g., "All Books", "Favorites").
 */
sealed interface BookListAction {
    /**
     * Represents an action triggered when the user modifies the search query
     * in the book list screen.
     *
     * @property query The new search query string entered by the user.
     */
    data class OnSearchQueryChange(val query: String): BookListAction
    /**
     * Represents the action of a user clicking on a book in the list.
     * This action is triggered when a user selects a book, typically to view its details.
     *
     * @property book The [Book] object that was clicked by the user.
     */
    data class OnBookClick(val book: Book): BookListAction
    /**
     * Represents the action of selecting a tab in the book list screen.
     * This action is triggered when the user taps on a different tab to filter
     * or categorize the displayed books.
     *
     * @property index The zero-based index of the newly selected tab.
     */
    data class OnTabSelected(val index:Int): BookListAction
}