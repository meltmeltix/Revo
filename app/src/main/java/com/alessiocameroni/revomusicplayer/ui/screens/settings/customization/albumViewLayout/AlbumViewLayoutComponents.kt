package com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.albumViewLayout

import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.navigation.NavController
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.album.HeaderLayout
import com.alessiocameroni.revomusicplayer.util.functions.selectAlbumViewHeaderLayoutString
import com.alessiocameroni.revomusicplayer.util.functions.selectAlbumViewHeaderLayoutSupportingString

// Scaffold components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumViewLayoutTopActionBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        title = { Text(text = stringResource(id = R.string.str_albumViewLayout)) },
        navigationIcon = {
            IconButton(
                onClick = { navController.navigateUp() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = stringResource(id = R.string.str_back)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun LayoutSelector(
    options: Array<HeaderLayout>,
    selected: HeaderLayout,
    onSelected: (HeaderLayout) -> Unit
) {
    options.forEach { option ->
        PixelyListItem(
            modifier = Modifier
                .selectable(
                    selected = (option == selected),
                    onClick = { onSelected(option) },
                    role = Role.RadioButton
                ),
            headlineTextString = selectAlbumViewHeaderLayoutString(option),
            supportingTextString = selectAlbumViewHeaderLayoutSupportingString(option),
            leadingContent = { RadioButton(selected = (option == selected), onClick = null) }
        )
    }
}