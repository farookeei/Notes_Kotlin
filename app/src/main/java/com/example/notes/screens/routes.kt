package com.example.notes.screens

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", title = "My Notes")
    object AddNotes : Screen("addNotes", title = "Add Note")

    companion object {
        fun getTitleByRoute(route: String?): String {
            return when {
                route == Home.route -> Home.title
                route?.startsWith(AddNotes.route) == true -> AddNotes.title
                else -> "Notes"
            }
        }
    }
}


