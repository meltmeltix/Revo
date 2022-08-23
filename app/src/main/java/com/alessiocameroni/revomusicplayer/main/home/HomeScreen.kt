package com.alessiocameroni.revomusicplayer.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(text = stringResource(id = R.string.str_home)) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(Screens.SearchScreen.route) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = stringResource(id = R.string.desc_searchmenu)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(Screens.SettingsScreen.route) }
                        ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outlined_settings_24),
                            contentDescription = stringResource(id = R.string.str_settings)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)){

            }
        }
    )
}