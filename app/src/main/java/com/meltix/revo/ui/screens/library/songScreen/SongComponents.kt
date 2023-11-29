package com.meltix.revo.ui.screens.library.songScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.meltix.pixely_components.PixelyListItem
import com.meltix.pixely_components.RoundedDropDownMenu
import com.meltix.revo.R
import com.meltix.revo.data.classes.song.Song
import com.meltix.revo.ui.components.ImageContainer

fun LazyListScope.songList(list: List<Song>) {
    itemsIndexed(list) { index, item -> key(index) {
        PixelyListItem(
            modifier = Modifier.clickable {  },
            headlineTextString = item.songTitle,
            largeHeadline = false,
            maxHeadlineLines = 1,
            supportingTextString = item.artist,
            maxSupportingLines = 1,
            leadingContent = {
                ImageContainer(
                    modifier = Modifier.padding(start = 8.dp, end = 5.dp),
                    placeholder = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                            contentDescription = item.songTitle
                        )
                    }
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.albumCoverUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = item.songTitle
                    )
                }
            },
            trailingContent = {
                val expanded = remember { mutableStateOf(false) }
    
                Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                    IconButton(onClick = { expanded.value = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                            contentDescription = stringResource(id = R.string.menu)
                        )
                    }
                    
                    ItemDropDownMenu(
                        expanded = expanded,
                        onNavigate = {  }
                    )
                }
            }
        )
    } }
}

@Composable
private fun ItemDropDownMenu(expanded: MutableState<Boolean>, onNavigate: () -> Unit) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
    
    }
}



