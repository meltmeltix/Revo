package com.meltix.revo.ui.screens.welcome

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.meltix.revo.R
import com.meltix.revo.ui.permissionsList
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.permissions.checkPermissions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun WelcomeScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val snackBarHostState = remember { SnackbarHostState() }
    val snackBarMessage = stringResource(id = R.string.str_permStorageDenied)
    val snackBarAction = stringResource(id = R.string.str_settings)

    val buttonForwardEnabled = checkPermissions()
    val buttonStorageEnabled = remember { mutableStateOf(true) }

    val mediaPermissionState = rememberPermissionState(
        permission = permissionsList[0]
    ) { isGranted ->
        if(isGranted) { buttonStorageEnabled.value = false }
        else {
            callSnackBar(
                context = context,
                coroutineScope = scope,
                hostState = snackBarHostState,
                stringMessage = snackBarMessage,
                stringAction = snackBarAction
            )
        }
    }

    RevoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { LargeTopAppBar(title = { WelcomeHeader() }) },
                bottomBar = {
                    BottomAppBar {
                        ForwardAppButton(
                            enabledState = buttonForwardEnabled,
                            navController = navController
                        )
                    }
                },
                snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize()
                    ) {
                        HorizontalDivider()

                        ListPermissionRow(
                            modifier = Modifier,
                            number = 1,
                            stringMainTitle = stringResource(id = R.string.str_storageAccess),
                            stringSubtitle = stringResource(id = R.string.info_storageAccess),
                            unitButton = {
                                FilledTonalButton(
                                    onClick = { mediaPermissionState.launchPermissionRequest() },
                                    modifier = Modifier.fillMaxWidth(),
                                    enabled = buttonStorageEnabled.value
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_outlined_sd_card_24),
                                        contentDescription = stringResource(id = R.string.str_grantAccess),
                                        modifier = Modifier
                                            .padding(end = 8.dp)
                                            .size(ButtonDefaults.IconSize)
                                    )

                                    Text(text = stringResource(id = R.string.str_grantAccess))
                                }
                            }
                        )
                    }
                }
            )
        }
    }
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