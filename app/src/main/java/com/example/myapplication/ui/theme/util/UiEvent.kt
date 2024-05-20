package com.example.myapplication.ui.theme.util

sealed interface UiEvent {
    data class Navigate(val route: String): UiEvent
    object NavigateBack : UiEvent
}