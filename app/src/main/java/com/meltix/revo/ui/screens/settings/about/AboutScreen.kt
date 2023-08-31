package com.meltix.revo.ui.screens.settings.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelyListItem
import com.meltix.pixely_components.PixelyListItemDefaults
import com.meltix.pixely_components.PixelySectionTitle
import com.meltix.pixely_components.PixelySegmentedColumn
import com.meltix.pixely_components.PixelySegmentedRow
import com.meltix.revo.BuildConfig
import com.meltix.revo.R
import com.meltix.revo.ui.components.SmallImageContainer
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AboutScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)
    val uriHandler = LocalUriHandler.current
    
    RevoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AboutLayout(
                windowWidthClass = windowClass.widthSizeClass,
                onBackButtonClick = { navController.navigateUp() }
            ) { itemList(uriHandler) }
        }
    }
}

private fun LazyListScope.itemList(uriHandler: UriHandler) {
    item { AppLogoItem() }
    
    item {
        PixelySectionTitle(
            stringTitle = stringResource(id = R.string.str_membersAndContributors),
            horizontalContentPadding = 15.dp
        )
    }
    
    item {
        PixelySegmentedColumn {
            PixelySegmentedRow(
                modifier = Modifier
                    .clickable { uriHandler.openUri("https://github.com/meltmeltix") }
            ) {
                CenterCreditItem(
                    titleText = stringResource(id = R.string.str_AlessioCameroni),
                    subText = stringResource(id = R.string.info_AlessioCameroni)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ill_meltix_logo),
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = stringResource(R.string.str_AlessioCameroni),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        )
                    }
                }
            }
            
            PixelySegmentedRow(
                modifier = Modifier
                    .clickable { uriHandler.openUri("https://github.com/arigata9") }
            ) {
                PixelyListItem(
                    headlineTextString = stringResource(id = R.string.str_arigata9),
                    supportingTextString = stringResource(id = R.string.info_arigata9),
                    leadingContent = {
                        SmallImageContainer(
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .size(54.dp)
                                .clip(CircleShape),
                            painterPlaceholder =
                            painterResource(id = R.drawable.ic_outlined_account_circle_24)
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(),
                                painter =
                                painterResource(id = R.drawable.bm_arigata9),
                                contentDescription =
                                stringResource(id = R.string.str_arigata9),
                            )
                        }
                    },
                    colors = PixelyListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                            2.dp
                        )
                    )
                )
            }
        }
    }
    
    item {
        PixelySectionTitle(
            stringTitle = stringResource(id = R.string.str_other),
            horizontalContentPadding = 15.dp
        )
    }
    
    item { PixelySegmentedColumn {
        PixelySegmentedRow(
            modifier = Modifier
                .clickable { uriHandler.openUri("https://github.com/meltmeltix/Revo") }
        ) {
            PixelyListItem(
                headlineTextString = stringResource(id = R.string.str_github),
                supportingTextString = stringResource(id = R.string.str_checkOnGithub),
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_github_24),
                        contentDescription = stringResource(id = R.string.str_github)
                    )
                },
                colors = PixelyListItemDefaults.colors(
                    containerColor =
                    MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
                )
            )
        }
        
        PixelySegmentedRow {
            PixelyListItem(
                headlineTextString = stringResource(id = R.string.str_openSourceLicenses),
                supportingTextString = stringResource(id = R.string.info_openSourceLicenses),
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_workspace_premium_24),
                        contentDescription = stringResource(id = R.string.str_openSourceLicenses)
                    )
                },
                colors = PixelyListItemDefaults.colors(
                    containerColor =
                    MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
                )
            )
        }
        
        PixelySegmentedRow {
            PixelyListItem(
                headlineTextString = stringResource(id = R.string.str_appVersion),
                supportingTextString = BuildConfig.VERSION_NAME,
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_info_24),
                        contentDescription = stringResource(id = R.string.str_appVersion)
                    )
                },
                colors = PixelyListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
                )
            )
        }
    } }
}