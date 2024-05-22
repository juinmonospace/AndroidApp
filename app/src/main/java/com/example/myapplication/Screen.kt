package com.example.myapplication

enum class Screen{
    STOPWATCH,
    WELCOME,
    WATCHRUNNING,
    TIMER,
    TIMERISRUNNING
}

sealed class NavigationItem(val route: String){
    object Welcome : NavigationItem("welcome")
    object Stopwatch : NavigationItem("stopwatch")
    object WatchRunning: NavigationItem("watchRunning")
    object Timer : NavigationItem("timer")
    object TimerIsRunning : NavigationItem("timerIsRunning")
    //arguments = listOf(navArgument("remainingTime") { type = NavType.LongType })

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}