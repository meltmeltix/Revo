package com.meltix.revo.ui.screens.settings.other

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelyListItem
import com.meltix.pixely_components.PixelySectionTitle
import com.meltix.revo.R
import com.meltix.revo.ui.navigation.SettingsScreens
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun OtherScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        OtherLayout(
            windowClass = windowClass,
            onBackButtonClick = { navController.navigateUp() }
        ) { itemList { navController.navigate(it) } }
    }
}

fun LazyListScope.itemList(onItemClick: (String) -> Unit) {
    item { PixelySectionTitle(stringTitle = stringResource(id = R.string.str_generalPrefs)) }
    
    item {
        PixelyListItem(
            modifier = Modifier.clickable { onItemClick(SettingsScreens.AppLanguage.route) },
            headlineTextString = stringResource(id = R.string.str_appLanguage),
            supportingTextString = stringResource(id = R.string.app_language)
        )
    }
}