package com.example.mamp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mamp.ui.screens.FirstLvlNoteScreen
import com.example.mamp.ui.screens.SecondLvlNoteScreen
import com.example.mamp.ui.theme.MAMPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MAMPTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "first_lvl_note_screen"
                ) {
                    composable("first_lvl_note_screen") {
                        FirstLvlNoteScreen(navController = navController)
                    }

                    composable("second_lvl_note_screen/{noteId}") { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
                        if (noteId != null) {
                            SecondLvlNoteScreen(
                                navController = navController,
                                noteId = noteId
                            )
                        }
                    }
                }
            }
        }
    }
}