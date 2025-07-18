package com.cearo.bookpedia.book.domain

/**
 * Represents a book with its details.
 *
 * @property id The unique identifier of the book.
 * @property title The title of the book.
 * @property imageUrl The URL of the book's cover image.
 * @property authors A list of authors of the book.
 * @property description A description of the book. Can be null if not available.
 * @property languages A list of languages the book is available in.
 * @property firstPublishedYear The year the book was first published. Can be null if not available.
 * @property averageRating The average rating of the book. Can be null if not available.
 * @property ratingsCount The number of ratings the book has received. Can be null if not available.
 * @property numPages The number of pages in the book. Can be null if not available.
 * @property numEditions The number of editions the book has.
 */
data class Book(
    val id: String,
    val title: String,
    val imageUrl: String,
    val authors: List<String>,
    val description: String?,
    val languages: List<String>,
    val firstPublishedYear: String?,
    val averageRating: Double?,
    val ratingsCount: Int?,
    val numPages: Int?,
    val numEditions: Int
)