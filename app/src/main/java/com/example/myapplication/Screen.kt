package com.example.myapplication

enum class Screen{
    HOME,
    NOTES,
}

sealed class NavigationItem(val route: String){
    object Home : NavigationItem("home")
    object Notes : NavigationItem("notes")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}