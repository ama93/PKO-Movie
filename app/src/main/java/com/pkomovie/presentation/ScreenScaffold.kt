package com.pkomovie.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun ScreenScaffold() {
    Scaffold { _ ->
        Box(modifier = Modifier.fillMaxSize()) {
            val navController = rememberAnimatedNavController()

            val bottomSheetNavigator = rememberBottomSheetNavigator()
            navController.navigatorProvider += bottomSheetNavigator

            ModalBottomSheetLayout(
                sheetBackgroundColor = MaterialTheme.colorScheme.background,
                bottomSheetNavigator = bottomSheetNavigator,
                sheetShape = RoundedCornerShape(16.dp),
            ) {
                DestinationsNavHost(
                    navController = navController,
                    engine = rememberAnimatedNavHostEngine(),
                    navGraph = NavGraphs.root
                )
            }
        }
    }

}
