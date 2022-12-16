package org.sopt.sample.util

sealed interface UiState {
    object Success: UiState
    object Failure: UiState
    object Error: UiState
}

