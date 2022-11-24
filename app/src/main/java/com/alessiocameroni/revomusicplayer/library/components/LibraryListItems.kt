package com.alessiocameroni.revomusicplayer.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alessiocameroni.revomusicplayer.R

@Composable
fun LibraryListItem(
    modifier: Modifier,
    painterPlaceholder: Painter,
    stringMainTitle: String,
    stringSubtitle: String,
    leadingUnit: @Composable (() -> Unit?),
    unitMenuItems: @Composable () -> Unit,
    menuEnabled: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(15.dp, 10.dp, 0.dp, 10.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.primary)
                .size(60.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterPlaceholder,
                contentDescription = stringResource(id = R.string.desc_albumImage),
                tint = MaterialTheme.colorScheme.onPrimary
            )

            leadingUnit()
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .weight(1f)
        ) {
            Text(
                text = stringMainTitle,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = stringSubtitle,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if(menuEnabled) {
            Box(
                modifier = Modifier
                    .padding(end = 5.dp)
            ) {
                IconButton(
                    onClick = { expanded = true },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                        contentDescription = stringResource(id = R.string.str_settings)
                    )
                }

                MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(20.dp))) {
                    DropdownMenu(
                        modifier = Modifier
                            .width(180.dp),
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        unitMenuItems()
                    }
                }
            }
        }
    }
}

@Composable
fun LibraryIconListItem(
    modifier: Modifier,
    stringMainTitle: String,
    stringSubtitle: String,
    leadingUnit: @Composable () -> Unit,
    unitMenuItems: @Composable () -> Unit,
    menuEnabled: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 9.dp)
                .size(60.dp),
            contentAlignment = Alignment.Center
        ) {
            leadingUnit()
        }

        Column(
            modifier = Modifier
                .padding(end = 15.dp)
                .weight(1f)
        ) {
            Text(
                text = stringMainTitle,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = stringSubtitle,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if(menuEnabled) {
            Box(
                modifier = Modifier
                    .padding(end = 5.dp)
            ) {
                IconButton(
                    onClick = { expanded = true },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                        contentDescription = stringResource(id = R.string.str_settings)
                    )
                }

                MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(20.dp))) {
                    DropdownMenu(
                        modifier = Modifier
                            .width(180.dp),
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        unitMenuItems()
                    }
                }
            }
        }
    }
}

@Composable
fun ActionButtonsItem(
    modifier: Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            OutlinedButton(
                modifier = Modifier.height(45.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                    contentDescription = stringResource(id = R.string.str_play)
                )

                Text(
                    text = stringResource(id = R.string.str_play)
                )
            }
        }

        item {
            FilledTonalButton(
                modifier = Modifier.height(45.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_shuffle_24),
                    contentDescription = stringResource(id = R.string.str_shuffle)
                )

                Text(text = stringResource(id = R.string.str_shuffle))
            }
        }
    }
}

@Composable
fun HeaderListItem(
    modifier: Modifier,
    stringTopInfo: String?,
    stringBottomInfo: String,
    displayIcon: Painter,
    unitAlbumImage: @Composable (() -> Unit)?,
) {
    Card( modifier = modifier.padding(horizontal = 16.dp) ) {
        Column( modifier = Modifier.fillMaxWidth() ) {
            if (unitAlbumImage != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clip(MaterialTheme.shapes.large)
                        .height(220.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    unitAlbumImage()
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clip(MaterialTheme.shapes.large)
                        .height(110.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .size(48.dp),
                        painter = displayIcon,
                        contentDescription = stringResource(id = R.string.str_icon),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            if(stringTopInfo != null) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    text = stringTopInfo,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                modifier = Modifier
                    .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                text = stringBottomInfo,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}