package com.example.myapplication

enum class Screen{
    STOPWATCH,
    WELCOME,
    WATCHRUNNING,
    TIMER,
}

sealed class NavigationItem(val route: String){
    object Welcome : NavigationItem("welcome")
    object Stopwatch : NavigationItem("stopwatch")
    object WatchRunning: NavigationItem("watchRunning")
    object Timer : NavigationItem("timer")

}