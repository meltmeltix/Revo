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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.alessiocameroni.revomusicplayer.R

@Composable
fun LibraryListItem(
    modifier: Modifier,
    unitAlbumImage: @Composable (() -> Unit?)?,
    stringTitleItem: String,
    stringSubtitleItem: String,
    unitMenuItems: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box( modifier = modifier.height(85.dp) ) {
        val constraints = ConstraintSet {
            val albumCover = createRefFor("AlbumCover")
            val textTitle = createRefFor("TextTitle")
            val textSubtitle = createRefFor("TextSubtitle")
            val menuButton = createRefFor("MenuButton")

            constrain(albumCover) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }

            constrain(textTitle) {
                start.linkTo(albumCover.end)
                top.linkTo(parent.top)
                bottom.linkTo(textSubtitle.top)
            }

            constrain(textSubtitle) {
                start.linkTo(albumCover.end)
                top.linkTo(textTitle.bottom)
                bottom.linkTo(parent.bottom)
            }

            constrain(menuButton) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        }

        ConstraintLayout(
            constraints,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .layoutId("AlbumCover")
                    .padding(start = 15.dp)
                    .clip(MaterialTheme.shapes.large)
                    .size(65.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                if (unitAlbumImage != null) {
                    unitAlbumImage()
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                        contentDescription = stringResource(id = R.string.str_songs),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Text(
                modifier = Modifier
                    .layoutId("TextTitle")
                    .width(250.dp)
                    .padding(start = 15.dp, top = 20.dp),
                text = stringTitleItem,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Text(
                modifier = Modifier
                    .layoutId("TextSubtitle")
                    .width(250.dp)
                    .padding(start = 15.dp, bottom = 20.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                text = stringSubtitleItem,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Box(
                modifier = Modifier
                    .layoutId("MenuButton")
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
fun LibraryNoMenuListItem(
    modifier: Modifier,
    unitAlbumImage: @Composable (() -> Unit?)?,
    stringTitleItem: String,
    stringSubtitleItem: String
) {
    Box( modifier = modifier.height(85.dp) ) {
        val constraints = ConstraintSet {
            val albumCover = createRefFor("AlbumCover")
            val textTitle = createRefFor("TextTitle")
            val textSubtitle = createRefFor("TextSubtitle")

            constrain(albumCover) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }

            constrain(textTitle) {
                start.linkTo(albumCover.end)
                top.linkTo(parent.top)
                bottom.linkTo(textSubtitle.top)
            }

            constrain(textSubtitle) {
                start.linkTo(albumCover.end)
                top.linkTo(textTitle.bottom)
                bottom.linkTo(parent.bottom)
            }
        }

        ConstraintLayout(
            constraints,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .layoutId("AlbumCover")
                    .padding(start = 15.dp)
                    .clip(MaterialTheme.shapes.large)
                    .size(65.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                if (unitAlbumImage != null) {
                    unitAlbumImage()
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                        contentDescription = stringResource(id = R.string.str_songs),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Text(
                modifier = Modifier
                    .layoutId("TextTitle")
                    .width(290.dp)
                    .padding(start = 15.dp, top = 20.dp),
                text = stringTitleItem,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Text(
                modifier = Modifier
                    .layoutId("TextSubtitle")
                    .width(290.dp)
                    .padding(start = 15.dp, bottom = 20.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                text = stringSubtitleItem,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Composable
fun LibraryLargeGridItem(
    modifier: Modifier,
    unitAlbumImage: @Composable (() -> Unit?)?,
    stringTitleItem: String,
    stringSubtitleItem: String,
    unitMenuItems: @Composable (() -> Unit)?,
) {
    var expanded by remember { mutableStateOf(false) }

    Box( modifier = modifier ) {
        val constraints = ConstraintSet {
            val albumCover = createRefFor("AlbumCover")
            val textTitle = createRefFor("TextTitle")
            val textSubtitle = createRefFor("TextSubtitle")
            val menuButton = createRefFor("MenuButton")

            constrain(albumCover) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }

            constrain(textTitle) {
                start.linkTo(parent.start)
                top.linkTo(albumCover.bottom)
                end.linkTo(menuButton.start)
            }

            constrain(textSubtitle) {
                start.linkTo(parent.start)
                top.linkTo(textTitle.bottom)
                end.linkTo(menuButton.start)
            }

            constrain(menuButton) {
                top.linkTo(albumCover.bottom)
                end.linkTo(parent.end)
            }
        }

        ConstraintLayout(
            constraints,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .layoutId("AlbumCover")
                    .padding(8.dp, 16.dp, 8.dp, 8.dp)
                    .clip(MaterialTheme.shapes.large)
                    .size(164.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                if (unitAlbumImage != null) {
                    unitAlbumImage()
                } else {
                    Icon(
                        modifier = Modifier
                            .size(48.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                        contentDescription = stringResource(id = R.string.str_songs),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Text(
                modifier = Modifier
                    .layoutId("TextTitle")
                    .padding(8.dp, 2.dp, 8.dp, 0.dp)
                    .width(100.dp),
                text = stringTitleItem,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Text(
                modifier = Modifier
                    .layoutId("TextSubtitle")
                    .padding(8.dp, 0.dp, 8.dp, 8.dp)
                    .width(100.dp),
                text = stringSubtitleItem,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Box(
                modifier = Modifier
                    .layoutId("MenuButton")
                    .padding(8.dp, 0.dp, 8.dp, 8.dp)
                    .size(48.dp)
            ) {
                if (unitMenuItems != null) {
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
}

@Composable
fun LibraryNoMenuLargeGridItem(
    modifier: Modifier,
    unitAlbumImage: @Composable (() -> Unit?)?,
    stringTitleItem: String,
    stringSubtitleItem: String
) {
    Box( modifier = modifier ) {
        val constraints = ConstraintSet {
            val albumCover = createRefFor("AlbumCover")
            val textTitle = createRefFor("TextTitle")
            val textSubtitle = createRefFor("TextSubtitle")

            constrain(albumCover) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }

            constrain(textTitle) {
                start.linkTo(parent.start)
                top.linkTo(albumCover.bottom)
            }

            constrain(textSubtitle) {
                start.linkTo(parent.start)
                top.linkTo(textTitle.bottom)
            }
        }

        ConstraintLayout(
            constraints,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .layoutId("AlbumCover")
                    .padding(8.dp, 16.dp, 8.dp, 8.dp)
                    .clip(MaterialTheme.shapes.large)
                    .size(164.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                if (unitAlbumImage != null) {
                    unitAlbumImage()
                } else {
                    Icon(
                        modifier = Modifier
                            .size(48.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                        contentDescription = stringResource(id = R.string.str_songs),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Text(
                modifier = Modifier
                    .layoutId("TextTitle")
                    .padding(8.dp, 2.dp, 8.dp, 0.dp)
                    .width(164.dp),
                text = stringTitleItem,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Text(
                modifier = Modifier
                    .layoutId("TextSubtitle")
                    .padding(8.dp, 0.dp, 8.dp, 8.dp)
                    .width(164.dp),
                text = stringSubtitleItem,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Composable
fun LibraryActionsItem(
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
fun LibraryHeaderListItem(
    modifier: Modifier,
    stringTitle: String,
    stringSubtitle: String,
    stringInfo: String?,
    unitAlbumImage: @Composable (() -> Unit)?,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .size(220.dp)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            if (unitAlbumImage != null) {
                unitAlbumImage()
            } else {
                Icon(
                    modifier = Modifier
                        .size(48.dp),
                    painter = painterResource(id = R.drawable.ic_outlined_album_24),
                    contentDescription = stringResource(id = R.string.str_albums),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            text = stringTitle,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            text = stringSubtitle,
            style = MaterialTheme.typography.titleMedium
        )

        if (stringInfo != null) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                text = stringInfo,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}