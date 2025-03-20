package com.example.codebrahma

sealed class Screens (val screen: String) {
    object Home : Screens("Home")
    object SavedCodeSnippets : Screens("Saved Code Snippets")
    object AiLearningPaths : Screens("AI Learning Paths")
    object SearchDocumentation : Screens("Search Documentation")
    object CodeOptimizationandDebugging : Screens("Optimizer and Debugger")
    object TaskandReminderManager : Screens("Task And Reminder Manager")
    object WebScrapperInsights : Screens("Latest Coding resources fetched by Ai")
    object VoiceCommandDebugging : Screens("Speak Issues and get real time Fixes")
    object OfflineMode : Screens("Offline AI Based Functionality")

    //Bottom App Bar
    object AutoWriteCode : Screens("AutoWriteCode")
    object AIDebugAssistant : Screens("Ai debug Assist")
    object Bookmarks : Screens("Saved Docs and Snippets")
    object Profile : Screens("Profile")

    //FAB (Floating Action Button)
    object ScanCode : Screens ("Scan Code")

    object Settings : Screens("Settings")
}
















