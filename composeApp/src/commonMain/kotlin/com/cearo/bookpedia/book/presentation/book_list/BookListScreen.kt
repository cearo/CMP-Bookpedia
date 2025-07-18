package com.cearo.bookpedia.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cearo.bookpedia.book.domain.Book
import com.cearo.bookpedia.book.presentation.book_list.components.BookSearchBar
import com.cearo.bookpedia.core.presentation.DarkBlue
import org.koin.compose.viewmodel.koinViewModel

/**
 * Root composable for the Book List Screen.
 *
 * This function serves as the entry point for the Book List feature,
 * managing the [BookListViewModel] and handling navigation events.
 *
 * @param viewModel The [BookListViewModel] instance, typically injected by Koin.
 * @param onBookClick Callback function that is invoked when a [Book] is clicked.
 *                    This is used to propagate the selected [Book] up to the navigation controller.
 */
@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookListScreen(
        state = state,
        onAction = { action ->
            // Propagating Book selection up to Nav controller for BookDetails
            if (action is BookListAction.OnBookClick) onBookClick(action.book)
            // Otherwise forward actions to the viewModel
            else viewModel.onAction(action)
        }
    )
}

/**
 * Displays the list of books and handles user interactions.
 *
 * This composable is responsible for rendering the UI elements of the book list screen,
 * including the search bar and the list of books. It observes the [BookListState]
 * and triggers actions based on user input. Will hide the keyboard in [BookSearchBar] `onImeSearch` callback.
 *
 * @param state The current state of the book list, containing data such as the search query,
 *              list of favorite books, and loading status.
 * @param onAction A callback function that is invoked when a user action occurs, such as
 *                 clicking a book or changing the search query. This function passes the
 *                 [BookListAction] up to the `BookListScreenRoot` for processing.
 */
@Composable
private fun BookListScreen (
    state: BookListState,
    onAction: (BookListAction) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(BookListAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}