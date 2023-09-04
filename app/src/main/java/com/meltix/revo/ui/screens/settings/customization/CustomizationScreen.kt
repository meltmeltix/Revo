package com.meltix.revo.ui.screens.settings.customization

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelyListItem
import com.meltix.pixely_components.PixelySectionTitle
import com.meltix.revo.R
import com.meltix.revo.ui.navigation.SettingsScreens
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun CustomizationScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)

    RevoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CustomizationLayout(
                windowClass = windowClass,
                onBackButtonClick = { navController.navigateUp() }
            ) { itemList { navController.navigate(it) } }
        }
    }
}

private fun LazyListScope.itemList(onItemClick: (String) -> Unit) {
    item { PixelySectionTitle(stringTitle = stringResource(id = R.string.str_theme)) }
    
    item {
        PixelyListItem(
            modifier = Modifier.clickable { },
            headlineTextString = stringResource(id = R.string.str_appTheme)
        )
    }
    
    item {
        var checked by remember { mutableStateOf(false) }
        
        PixelyListItem(
            modifier = Modifier.selectable(selected = checked, onClick = { checked = !checked }),
            headlineTextString = stringResource(id = R.string.str_dynamicColors),
            trailingContent = { Switch(checked = checked, onCheckedChange = null) },
            trailingContentDivider = true
        )
    }
    
    item { PixelySectionTitle(stringTitle = stringResource(id = R.string.str_interface)) }
    
    item {
        PixelyListItem(
            modifier = Modifier.clickable { onItemClick(SettingsScreens.PlayerLayout.route) },
            headlineTextString = stringResource(id = R.string.str_layoutPlayer),
            supportingTextString = stringResource(id = R.string.info_layoutPlayer)
        )
    }
    
    item {
        PixelyListItem(
            modifier = Modifier.clickable { onItemClick(SettingsScreens.AlbumViewLayout.route) },
            headlineTextString = stringResource(id = R.string.str_albumViewLayout),
            supportingTextString = stringResource(id = R.string.info_albumViewLayout)
        )
    }
}