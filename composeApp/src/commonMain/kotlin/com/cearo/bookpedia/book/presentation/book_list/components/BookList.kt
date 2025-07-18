package com.cearo.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cearo.bookpedia.book.domain.Book

/**
 * A composable function that displays a list of books using a `LazyColumn`.
 *
 * This function takes a list of `Book` objects and renders them vertically.
 * It utilizes `LazyColumn` for efficient rendering of potentially long lists,
 * only composing and laying out the items that are currently visible on screen.
 *
 * Each book item is represented by the `BookListItem` composable.
 *
 * A key is provided for each item based on the `book.id` to help Compose efficiently
 * identify and update items when the list changes.
 *
 * @param books The list of `Book` objects to display. This is a required parameter.
 * @param onBookClick A lambda function that will be invoked when a specific `BookListItem`
 *                    is clicked. It receives the clicked `Book` object as a parameter.
 *                    This is a required parameter.
 * @param scrollState The `LazyListState` used to control and observe the scrolling
 *                    position of the `LazyColumn`. Defaults to a new state remembered
 *                    by `rememberLazyListState()`. This allows for programmatic scrolling
 *                    or saving/restoring scroll position.
 * @param modifier The `Modifier` to be applied to the `LazyColumn` container.
 *                 Defaults to `Modifier`, allowing for customization of layout,
 *                 padding, background, etc., from the call site.
 */
@Composable
fun BookList(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    scrollState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = books,
            key = {
                it.id
            }
        ) { book ->
            BookListItem(
                book = book,
                modifier = Modifier.widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    onBookClick(book)
                }
            )
        }
    }
}