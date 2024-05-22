package com.example.myapplication

enum class Screen{
    STARTTIME,
    WELCOME,
    WATCHRUNNING,
    STOPPEDTIME,
    TIMER,
    TIMERISRUNNING
}

sealed class NavigationItem(val route: String){
    object Welcome : NavigationItem("welcome")
    object StartTime : NavigationItem("startTime")
    object WatchRunning: NavigationItem("watchRunning")
    object StoppedTime : NavigationItem("stoppedTime")
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