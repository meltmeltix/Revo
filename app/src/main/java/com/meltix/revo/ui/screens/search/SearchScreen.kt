package com.meltix.revo.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.meltix.revo.R
import com.meltix.revo.ui.theme.RevoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }

    RevoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    SearchBar(
                        query = text,
                        onQueryChange = { text = it },
                        onSearch = { /*TODO*/ },
                        active = true,
                        onActiveChange = {  },
                        placeholder = { Text(stringResource(id = R.string.str_search)) },
                        leadingIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                                    contentDescription = stringResource(id = R.string.str_back)
                                )
                            }
                        },
                        trailingIcon = {
                            IconButton(onClick = { text = "" }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_close_24),
                                    contentDescription = stringResource(id = R.string.str_search),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    ) {

                    }
                },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                    ) {

                    }
                }
            )
        }
    }
}