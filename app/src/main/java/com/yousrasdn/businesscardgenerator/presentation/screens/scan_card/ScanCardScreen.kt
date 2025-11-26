package com.yousrasdn.businesscardgenerator.presentation.screens.scan_card

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList

@Composable
fun ScanCardScreen(backStack: SnapshotStateList<Any>) {
    Text("Scan Profile Screen")
    Button(onClick = {backStack.removeLastOrNull()}) {
        Text("Go Back")
    }
}