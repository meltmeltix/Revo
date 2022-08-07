package com.alessiocameroni.revomusicplayer

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

class SearchActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = (LocalContext.current as? Activity)
            val context = LocalContext.current

            var text by rememberSaveable { mutableStateOf("") }
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
            
            RevoMusicPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            SmallTopAppBar(
                                title = {
                                    TextField(
                                        modifier = Modifier.fillMaxWidth(),
                                        value = text,
                                        onValueChange = { text = it },
                                        placeholder = { Text(text = stringResource(id = R.string.str_phsearch)) },
                                        textStyle = MaterialTheme.typography.titleMedium,
                                        singleLine = true,
                                        trailingIcon = {
                                            IconButton(onClick = { text = "" }) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_filled_close_24),
                                                    contentDescription = stringResource(id = R.string.desc_searchmenu),
                                                    tint = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        },
                                        keyboardOptions = KeyboardOptions( imeAction = ImeAction.Search ),
                                        keyboardActions = KeyboardActions( onSearch = {  }),
                                        colors = TextFieldDefaults.textFieldColors(
                                            containerColor = MaterialTheme.colorScheme.background,
                                            unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                                            focusedIndicatorColor = MaterialTheme.colorScheme.background
                                        )
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = { activity?.finish() }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                                            contentDescription = stringResource(id = R.string.desc_back)
                                        )
                                    }
                                },
                                scrollBehavior = scrollBehavior
                            )
                        }
                    ) {

                    }
                }
            }
        }
    }
}