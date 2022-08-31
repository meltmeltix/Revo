package com.alessiocameroni.revomusicplayer.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.alessiocameroni.revomusicplayer.R


@Preview(showBackground = true)
@Composable
fun PreviewComponent() {
    TwoColumnListItem(
        modifier = Modifier
            .width(180.dp),
        unitAlbumImage = null,
        stringTitleItem = "Title",
        stringSubtitleItem = "Subtitle"
    ) {
        DropdownMenuItem(
            text = {
                Text(text = stringResource(id = R.string.str_addtoplaylist))
            },
            onClick = { /*TODO*/ },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_playlist_add_24),
                    contentDescription = stringResource(id = R.string.desc_addtoplaylist)
                )
            }
        )
    }
}

@Composable
fun OneColumnListItem(
    modifier: Modifier,
    unitAlbumImage: @Composable (() -> Unit?)?,
    stringTitleItem: String,
    stringSubtitleItem: String,
    unitMenuItems: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box( modifier = modifier.height(84.dp) ) {
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
                    .clip(RoundedCornerShape(16.dp))
                    .size(64.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                if (unitAlbumImage != null) {
                    unitAlbumImage()
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                        contentDescription = stringResource(id = R.string.str_tracks),
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
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Text(
                modifier = Modifier
                    .layoutId("TextSubtitle")
                    .width(250.dp)
                    .padding(start = 15.dp, bottom = 20.dp),
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
fun TwoColumnListItem(
    modifier: Modifier,
    unitAlbumImage: @Composable() (() -> Unit?)?,
    stringTitleItem: String,
    stringSubtitleItem: String,
    unitMenuItems: @Composable() (() -> Unit)?,
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
                    .clip(RoundedCornerShape(16.dp))
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
                        contentDescription = stringResource(id = R.string.str_tracks),
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
                style = MaterialTheme.typography.bodyLarge,
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