package com.cearo.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.accessibility_arrow_right_to_details
import cmp_bookpedia.composeapp.generated.resources.accessibility_book_cover_image
import cmp_bookpedia.composeapp.generated.resources.accessibility_ratings_star
import cmp_bookpedia.composeapp.generated.resources.book_error_2
import cmp_bookpedia.composeapp.generated.resources.exception_image_failed_to_load
import cmp_bookpedia.composeapp.generated.resources.exception_invalid_image_size
import coil3.compose.rememberAsyncImagePainter
import com.cearo.bookpedia.book.domain.Book
import com.cearo.bookpedia.core.presentation.LightBlue
import com.cearo.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round

/**
 * Represents errors that can occur during API interactions.
 * This sealed interface defines specific exception types for more granular error handling:
 * - `InvalidImageSizeException`: Thrown when an image retrieved from the API has invalid dimensions (e.g., width or height is less than or equal to 1).
 * - `ImageFailedToLoad`: Thrown when there's a general failure in loading an image from the API, not covered by `InvalidImageSizeException`.
 */
sealed interface APIError {
    /**
     * Thrown when an image retrieved from the API has invalid dimensions (e.g., width or height is less than or equal to 1).
     */
    data class InvalidImageSizeException(override val message: String): Exception(message)

    /**
     * Thrown when there's a general failure in loading an image from the API, not covered by `InvalidImageSizeException`.
     */
    data class ImageFailedToLoadException(override val message: String): Exception(message)
}

/**
 * Displays a single book item within a list.
 *
 * This Composable renders the visual representation of a [Book], including its cover image,
 * title, author, and average rating. It's designed to be interactive, allowing users
 * to tap on it to view more details.
 *
 * The composable handles potential errors during book cover image loading by displaying
 * a placeholder image. Consumers of this composable should be aware that image loading
 * is an asynchronous operation and might fail.
 *
 * @param book The [Book] object containing the data to be displayed.
 * @param onClick A lambda function invoked when the book item is clicked.
 *                It typically receives the `book.id` (or the [Book] object itself)
 *                to navigate to a dedicated book details screen (e.g., `BookDetailsScreen`).
 * @param modifier An optional [Modifier] to be applied to the root Composable of this item.
 *                 Defaults to [Modifier].
 *
 * @throws APIError.InvalidImageSizeException
 * @see APIError.InvalidImageSizeException
 *
 * @throws APIError.ImageFailedToLoadException
 * @see APIError.ImageFailedToLoadException
 *
 * @see LazyColumn (This item is often used within a [LazyColumn] for displaying a list of books)
 * @see Book // The data class representing
**/
@Composable
fun BookListItem(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier.clickable(onClick = onClick),
        color = LightBlue.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier.height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                var imageLoadResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }
                val invalidImageSizeMessage = stringResource(Res.string.exception_invalid_image_size)
                val imageFailedToLoadMessage = stringResource(Res.string.exception_image_failed_to_load)

                val painter = rememberAsyncImagePainter(
                    model = book.imageUrl,
                    onSuccess = {
                        val intrinsicSize: Size = it.painter.intrinsicSize
                        if(intrinsicSize.width > 1 && intrinsicSize.height > 1) {
                            Result.success(it.painter)
                        } else {
                            Result.failure(
                                exception = APIError.InvalidImageSizeException(invalidImageSizeMessage)
                            )
                        }
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        imageLoadResult = Result.failure(APIError.ImageFailedToLoadException(imageFailedToLoadMessage))
                    }
                )

                val bookCoverImageContentDescriptionResource = stringResource(Res.string.accessibility_book_cover_image)
                when(val result = imageLoadResult) {
                    null -> CircularProgressIndicator()
                    else -> {
                        Image(
                            painter = if(result.isSuccess) painter
                            // TODO: expand else to throw up a Toast of the error message.
                            else painterResource(Res.drawable.book_error_2),
                            contentDescription = "$bookCoverImageContentDescriptionResource : ${book.title}",
                            contentScale = if(result.isSuccess) ContentScale.Crop
                            else ContentScale.Fit,
                            modifier = Modifier.aspectRatio(
                                ratio = 0.65f,
                                matchHeightConstraintsFirst = true)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                book.authors.firstOrNull()?.let { authorName ->
                    Text(
                        text = authorName,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                book.averageRating?.let { rating ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            /** Rounds the rating to one decimal place.
                             * ex: [rating] = 4.76
                             * [rating] * 10 = 47.6
                             * 47.6 -> [round] = 48
                             * 48 / 10.0 = 4.8 Stars
                             * */
                            text = "${round(rating * 10) / 10.0}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(Res.string.accessibility_ratings_star),
                            tint = SandYellow
                        )
                    }
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(Res.string.accessibility_arrow_right_to_details),
                modifier = Modifier.size(36.dp)
            )
        }
    }
}
