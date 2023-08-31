package com.meltix.revo.ui.screens.settings.customization.albumDetailsLayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelySupportInfoText
import com.meltix.revo.R
import com.meltix.revo.data.classes.album.HeaderLayout
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AlbumViewLayoutScreen(
    navController: NavController,
    viewModel: AlbumDetailsLayoutViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)
    
    val selectedLayout by viewModel.headerLayout.collectAsStateWithLifecycle(HeaderLayout.REVO)

    RevoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AlbumDetailsLytLayout(
                windowWidthSizeClass = windowClass.widthSizeClass,
                onBackButtonClick = { navController.navigateUp() }
            ) { itemList(windowClass, viewModel, selectedLayout) }
        }
    }
}

private fun LazyListScope.itemList(
    windowClass: WindowSizeClass,
    viewModel: AlbumDetailsLayoutViewModel,
    selectedLayout: HeaderLayout
) {
    item {
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.BottomCenter
        ) { AlbumPreview(windowClass, selectedLayout) }
    }
    
    item { PixelySupportInfoText(stringText = stringResource(id = R.string.info_albumViewLayout)) }
    
    item {
        Column(
            modifier = Modifier.selectableGroup(),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            LayoutSelector(
                options = HeaderLayout.values(),
                selected = selectedLayout,
                onSelected = { viewModel.setHeaderLayout(it) },
            )
        }
    }
}