package com.yousrasdn.businesscardgenerator.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.yousrasdn.businesscardgenerator.core.navigation.ScreensListing
import com.yousrasdn.businesscardgenerator.presentation.root.RootDestination
import com.yousrasdn.businesscardgenerator.presentation.root.RootViewModel
import com.yousrasdn.businesscardgenerator.debug.DevToolsScreen
import com.yousrasdn.businesscardgenerator.presentation.screens.create_card.BusinessCardFormScreen
import com.yousrasdn.businesscardgenerator.presentation.screens.home.HomeScreen
import com.yousrasdn.businesscardgenerator.presentation.screens.onboarding.OnboardingScreen
import com.yousrasdn.businesscardgenerator.presentation.screens.profile.ProfileViewScreen
import com.yousrasdn.businesscardgenerator.presentation.screens.scanned_card_details.ScannedCardDetailsScreen
import com.yousrasdn.businesscardgenerator.presentation.screens.scan_card.QRScannerScreen
import com.yousrasdn.businesscardgenerator.presentation.screens.scan_card.QRScannerViewModel
import com.yousrasdn.businesscardgenerator.ui.theme.BusinessCardGeneratorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private val rootViewModel: RootViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            BusinessCardGeneratorTheme {

                val destination by rootViewModel.destination.collectAsStateWithLifecycle()
                
                if (destination != RootDestination.Loading) {
                    when (destination) {
                        RootDestination.Onboarding -> {
                            AppNavigation(startDestination = ScreensListing.Onboarding)
                        }
                        RootDestination.Home -> {
                            AppNavigation(startDestination = ScreensListing.Home)
                        }
                        else -> {
//                            todo improve show loading indicator
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun AppNavigation(startDestination: Any) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val backStack = remember { mutableStateListOf(startDestination) }

        NavDisplay(
            backStack = backStack,
            modifier = Modifier.padding(innerPadding),
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<ScreensListing.Onboarding> {
                    OnboardingScreen(backStack)
                }
                entry<ScreensListing.CardProfile> { screen ->
                    BusinessCardFormScreen(backStack, isEditMode = screen.isEditMode)
                }
                entry<ScreensListing.ScanCard> {
                    val viewModel: QRScannerViewModel = hiltViewModel()
                    
                    QRScannerScreen(
                        onQRCodeScanned = { qrData ->
                            viewModel.handleScannedQRCode(
                                qrData = qrData,
                                onSuccess = {
                                    backStack.removeLastOrNull()
                                    if (backStack.lastOrNull() !is ScreensListing.Home) {
                                        backStack.clear()
                                        backStack.add(ScreensListing.Home)
                                    }
                                },
                                onError = { errorMessage ->
                                    android.util.Log.e("QRScanner", "Error: $errorMessage")
                                    backStack.removeLastOrNull()
                                }
                            )
                        },
                        onClose = { 
                            backStack.removeLastOrNull() 
                        }
                    )
                }
                entry<ScreensListing.Home> {
                    HomeScreen(backStack)
                }
                entry<ScreensListing.Profile> {
                    ProfileViewScreen(backStack)
                }
                entry<ScreensListing.ScannedCardDetails> { screen ->
                    ScannedCardDetailsScreen(
                         cardId = screen.cardId,
                         backStack = backStack
                    )
                }
                entry<ScreensListing.DevTool> {
                    DevToolsScreen(backStack)
                }
            },
        )
    }
}

