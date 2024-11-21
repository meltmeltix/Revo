package com.meltix.revo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.meltix.pixely_components.RoundedDropDownMenu
import com.meltix.revo.R
import com.meltix.revo.ui.components.ImageContainer
import com.meltix.revo.util.functions.WindowType

@Composable
fun BarDropDownMenu(onNavigate: () -> Unit) {
    val expandedMenu = remember { mutableStateOf(false) }
    
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expandedMenu.value = true }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                contentDescription = stringResource(id = R.string.menu)
            )
        }
        
        RoundedDropDownMenu(
            expanded = expandedMenu.value,
            onDismissRequest = { expandedMenu.value = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.settings)) },
                onClick = { onNavigate() },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_settings_24),
                        contentDescription = stringResource(id = R.string.settings)
                    )
                }
            )
        }
    }
}

@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    windowType: WindowType
) {
    var checked by remember { mutableStateOf(false) }
    
    Surface(modifier = modifier, color = color) {
        when(windowType) {
            WindowType.COMPACT_PORTRAIT, WindowType.COMPACT_LANDSCAPE, WindowType.COMPACT_WINDOW -> {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Contents(
                        pictureEnabled = true,
                        checked = checked,
                        onCheckedChange = { checked = it }
                    )
                    
                    LinearProgressIndicator(
                        progress = { 0.5f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .clip(MaterialTheme.shapes.extraLarge),
                    )
                }
            }
            else -> {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ImageContainer(
                        modifier = Modifier.size(90.dp),
                        placeholder = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                                contentDescription = stringResource(id = R.string.songs),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                    
                    Contents(
                        pictureEnabled = false,
                        checked = checked,
                        onCheckedChange = { checked = it }
                    )
                    
                    LinearProgressIndicator(
                        progress = { 0.5f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .clip(MaterialTheme.shapes.extraLarge),
                    )
                }
            }
        }
    }
}

@Composable
private fun Contents(
    pictureEnabled: Boolean,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.height(48.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if(pictureEnabled) {
            ImageContainer(
                placeholder = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                        contentDescription = stringResource(id = R.string.songs),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }
        
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Song Name",
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Artist Name",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    
        FilledIconToggleButton(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) },
            colors = IconButtonDefaults.filledTonalIconToggleButtonColors(
                checkedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                checkedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            if (checked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filled_pause_24),
                    contentDescription = stringResource(id = R.string.pause)
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filled_play_arrow_24),
                    contentDescription = stringResource(id = R.string.play)
                )
            }
        }
    }
}