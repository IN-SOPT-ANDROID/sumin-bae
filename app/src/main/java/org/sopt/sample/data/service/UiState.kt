package org.sopt.sample.data.service

sealed interface UiState {
    object Success: UiState
    object Failure: UiState
    object Error: UiState
}

