package com.yousrasdn.businesscardgenerator.presentation.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.CreateBusinessCardEvent

@Composable
fun HomeScreen(
    backStack: SnapshotStateList<Any>
) {

    Button(onClick = {backStack.removeLastOrNull()}) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
    }

}