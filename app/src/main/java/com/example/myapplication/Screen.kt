package com.example.myapplication

enum class Screen{
    HOME,
    NOTES,
    GPS,
    WELCOME
}

sealed class NavigationItem(val route: String){
    object Welcome : NavigationItem("welcome")
    object Home : NavigationItem("home")
    object Notes : NavigationItem("notes")
    object Gps : NavigationItem("gps")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}