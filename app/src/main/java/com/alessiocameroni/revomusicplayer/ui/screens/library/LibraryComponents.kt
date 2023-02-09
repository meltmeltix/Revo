package com.alessiocameroni.revomusicplayer.ui.screens.library

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens

@Composable
fun TopBarDropDownMenu(
    expanded: MutableState<Boolean>,
    navController: NavController
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = MaterialTheme.shapes.large)) {
        DropdownMenu(
            modifier = Modifier.widthIn(min = 180.dp),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.str_settings))
                },
                onClick = {
                    navController.navigate(Screens.SettingsScreen.route)
                    expanded.value = false
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_settings_24),
                        contentDescription = stringResource(id = R.string.desc_settings)
                    )
                }
            )
        }
    }
}

@Composable
fun ItemDropDownMenu(
    expanded: MutableState<Boolean>,
    navController: NavController
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = MaterialTheme.shapes.large)) {
        DropdownMenu(
            modifier = Modifier.widthIn(min = 180.dp),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {

        }
    }
}

