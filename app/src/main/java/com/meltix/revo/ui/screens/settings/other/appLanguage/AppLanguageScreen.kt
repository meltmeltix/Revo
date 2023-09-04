package com.meltix.revo.ui.screens.settings.other.appLanguage

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelyListItem
import com.meltix.pixely_components.PixelySupportInfoText
import com.meltix.revo.R
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AppLanguageScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)
    val changeLanguageEnabled = remember { Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AppLanguageLayout(
            windowClass = windowClass,
            onBackButtonClick = { navController.navigateUp() }
        ) {
            itemList(changeLanguageEnabled) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val intent = Intent(Settings.ACTION_APP_LOCALE_SETTINGS)
                    intent.data = Uri.parse("package:com.meltix.revo")
                    context.startActivity(intent)
                }
            }
        }
    }
}

private fun LazyListScope.itemList(changeLanguageEnabled: Boolean, onItemClick: () -> Unit) {
    item {
        PixelyListItem(
            modifier = Modifier.clickable(enabled = changeLanguageEnabled) { onItemClick() },
            headlineTextString = stringResource(id = R.string.app_language),
            trailingContent = {
                if(changeLanguageEnabled) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                        contentDescription = stringResource(id = R.string.info_appLanguage)
                    )
                }
            }
        )
    }
    
    item {
        PixelySupportInfoText(
            stringText =
            stringResource(id = R.string.info_helpAppLanguage),
            painterInfoIcon = painterResource(id = R.drawable.ic_outlined_info_24),
            descriptionInfoIcon = stringResource(id = R.string.info_helpAppLanguage)
        )
    }
}