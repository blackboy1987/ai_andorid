package com.bootx.ai.ui.navigation

sealed class Destinations(val route: String) {
    data object MainFrame : Destinations("MainFrame")
    data object HomeFrame : Destinations("HomeFrame")
    data object LoginCodeFrame : Destinations("LoginCodeFrame")
    data object AppFrame : Destinations("AppFrame")
    data object WriteFrame : Destinations("WriteFrame")

}
