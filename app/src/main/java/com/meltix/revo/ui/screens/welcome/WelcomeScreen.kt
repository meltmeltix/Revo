package com.meltix.revo.ui.screens.welcome

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.meltix.revo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun WelcomeScreen(navController: NavHostController) {

}

fun callSnackBar(
    context: Context,
    coroutineScope: CoroutineScope,
    stringMessage: String,
    stringAction: String,
    hostState: SnackbarHostState,
) {
    coroutineScope.launch {
        val snackBar = hostState.showSnackbar(
            message = stringMessage,
            actionLabel = stringAction,
            duration = SnackbarDuration.Short
        )

        when(snackBar) {
            SnackbarResult.Dismissed -> {  }
            SnackbarResult.ActionPerformed -> {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:" + R.string.app_packageName)
                context.startActivity(intent)
            }
        }
    }
}