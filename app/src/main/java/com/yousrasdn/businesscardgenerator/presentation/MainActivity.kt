package com.yousrasdn.businesscardgenerator.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.yousrasdn.businesscardgenerator.core.navigation.ScreensListing
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.AddBusinessCardScreen
import com.yousrasdn.businesscardgenerator.presentation.screens.onboarding.OnboardingScreen
import com.yousrasdn.businesscardgenerator.presentation.screens.scan_card.ScanCardScreen
import com.yousrasdn.businesscardgenerator.ui.theme.BusinessCardGeneratorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCardGeneratorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val backStack = remember { mutableStateListOf<Any>(ScreensListing.Onboarding) }

                    NavDisplay(
                        backStack = backStack,
                        modifier = Modifier.padding(innerPadding),
                        onBack = {  },
                        entryProvider = entryProvider {
                            entry<ScreensListing.Onboarding> {
                                OnboardingScreen(backStack)
                            }
                            entry<ScreensListing.CardProfile> {
                                AddBusinessCardScreen(backStack)
                            }
                            entry<ScreensListing.ScanCard> {
                                ScanCardScreen(backStack)
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun BusinessCardApp(name: String, modifier: Modifier = Modifier) {

}

