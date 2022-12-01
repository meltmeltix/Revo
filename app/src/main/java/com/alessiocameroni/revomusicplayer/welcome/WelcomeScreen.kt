package com.alessiocameroni.revomusicplayer.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.navigation.Screens
import com.alessiocameroni.revomusicplayer.permissions.checkPermissions
import com.alessiocameroni.revomusicplayer.permissionsList
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme
import com.alessiocameroni.revomusicplayer.welcome.components.WelcomePermissionRow
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun WelcomeScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()

    val snackBarHostState = remember { SnackbarHostState() }

    val buttonForwardEnabled = checkPermissions()
    val buttonStorageEnabled = remember { mutableStateOf(true) }

    val mediaPermissionRationale = remember { mutableStateOf(true) }
    val mediaPermissionState = rememberPermissionState(
        permission = permissionsList[0]
    ) { isGranted ->
        if(isGranted) { buttonStorageEnabled.value = false }
    }

    RevoMusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    LargeTopAppBar(
                        title = {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val stringRevo = stringResource(id = R.string.app_name)

                                Text(text = stringResource(id = R.string.str_heyThere))
                                Text(
                                    buildAnnotatedString {
                                        append(stringResource(id = R.string.str_welcome))
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        ) {
                                            append(" $stringRevo")
                                        }
                                    }
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        content = {
                            Button(
                                onClick = {
                                    navController.navigate(Screens.MainScreen.route) {
                                        popUpTo(Screens.WelcomeScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .padding(horizontal = 15.dp)
                                    .fillMaxWidth(),
                                enabled = buttonForwardEnabled
                            ) {
                                Icon(
                                    painter =
                                    painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                                    contentDescription = stringResource(id = R.string.str_letsGo),
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                        .size(ButtonDefaults.IconSize)
                                )

                                Text(text = stringResource(id = R.string.str_letsGo))
                            }
                        }
                    )
                },
                snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize()
                    ) {
                        Divider()

                        WelcomePermissionRow(
                            modifier = Modifier,
                            number = 1,
                            stringMainTitle = stringResource(id = R.string.str_storageAccess),
                            stringSubtitle = stringResource(id = R.string.desc_storageAccess),
                            unitButton = {
                                FilledTonalButton(
                                    onClick = {
                                        mediaPermissionState.launchPermissionRequest()
                                        mediaPermissionRationale.value =
                                            mediaPermissionState.status.shouldShowRationale
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    enabled = buttonStorageEnabled.value
                                ) {
                                    Box {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_outlined_sd_card_24),
                                            contentDescription = stringResource(id = R.string.desc_grantAccess),
                                            modifier = Modifier
                                                .padding(end = 8.dp)
                                                .size(ButtonDefaults.IconSize)
                                        )

                                        Text(
                                            text = stringResource(id = R.string.str_grantAccess),
                                            modifier = Modifier
                                                .padding(end = 8.dp)
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            )
        }
    }
}