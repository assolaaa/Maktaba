package com.ElOuedUniv.maktaba.presentation.book.add

import androidx.lifecycle.ViewModel
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: AddBookUiAction) {
        when (action) {
            is AddBookUiAction.OnTitleChange -> {
                _uiState.update { it.copy(title = action.title) }
                validateInputs()
            }
            is AddBookUiAction.OnIsbnChange -> {
                _uiState.update { it.copy(isbn = action.isbn) }
                validateInputs()
            }
            is AddBookUiAction.OnPagesChange -> {
                _uiState.update { it.copy(nbPages = action.pages) }
                validateInputs()
            }
            is AddBookUiAction.OnImagePicked -> {
                _uiState.update { it.copy(imageUri = action.uri) }
                }
            is AddBookUiAction.OnAddClick -> {
                addBook()
            }
        }
    }
        private fun validateInputs() {
            val currentState = _uiState.value

            val titleError = if (currentState.title.isBlank()) "Title cannot be empty" else null
            val isbnError = if (currentState.isbn.length != 13) "ISBN must be 13 digits" else null
            val pagesInt = currentState.nbPages.toIntOrNull()
            val pagesError = if (pagesInt == null || pagesInt <= 0) "Pages must be a positive number" else null

            val isFormValid = titleError == null && isbnError == null && pagesError == null

            _uiState.update {
                it.copy(
                    titleError = titleError,
                    isbnError = isbnError,
                    nbPagesError = pagesError,
                    isFormValid = isFormValid
                )
            }
        }
    private fun addBook() {
        if (!_uiState.value.isFormValid) return
        val currentState = _uiState.value
        val book = Book(
            isbn = currentState.isbn,
            title = currentState.title,
            nbPages = currentState.nbPages.toIntOrNull() ?: 0,
            imageUrl = currentState.imageUri?.toString()
        )
        addBookUseCase(book)
        _uiState.update { it.copy(isSuccess = true) }
    }
}
