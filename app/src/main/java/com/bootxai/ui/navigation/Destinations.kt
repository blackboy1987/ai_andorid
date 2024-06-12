package com.bootxai.ui.navigation

sealed class Destinations(val route: String) {
    data object MainFrame : Destinations("MainFrame")
    data object HomeFrame : Destinations("HomeFrame")

}
