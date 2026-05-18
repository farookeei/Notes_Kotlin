package com.example.notes.screens

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", title = "My Notes")
    object AddNotes : Screen("addNotes", title = "Add Note")

    object EditNotes : Screen(route = "editNotes", title = "Edit Notes")

    companion object {
        fun getTitleByRoute(route: String?): String {
            return when {
                route == Home.route -> Home.title
                route == AddNotes.route -> AddNotes.title
                route?.startsWith(EditNotes.route) == true -> EditNotes.title
                else -> "Notes"
            }
        }
    }
}


