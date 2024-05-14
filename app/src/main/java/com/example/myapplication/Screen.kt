package com.example.myapplication

enum class Screen{
    HOME,
    NOTES,
    GPS,
    WELCOME,
    LOCATION_AND_TIME
}

sealed class NavigationItem(val route: String){
    object Welcome : NavigationItem("welcome")
    object Home : NavigationItem("home")
    object Notes : NavigationItem("notes")
    object Gps : NavigationItem("gps")
    object LocationAndTime : NavigationItem("locationAndTime")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}