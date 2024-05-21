package com.example.myapplication

enum class Screen{
    NOTES,
    STARTTIME,
    WELCOME,
    LOCATION_AND_TIME,
    WATCHRUNNING,
    STOPPEDTIME,
    TIMER,
    TIMERISRUNNING
}

sealed class NavigationItem(val route: String){
    object Welcome : NavigationItem("welcome")
    object Notes : NavigationItem("notes")
    object StartTime : NavigationItem("startTime")
    object WatchRunning: NavigationItem("watchRunning")
    object StoppedTime : NavigationItem("stoppedTime")
    object Timer : NavigationItem("timer")
    object TimerIsRunning : NavigationItem("timerIsRunning")
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