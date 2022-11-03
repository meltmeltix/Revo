package com.alessiocameroni.revomusicplayer.library.playlists.playlistsscreen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.alessiocameroni.revomusicplayer.R

@Composable
fun PlaylistItem(
    modifier: Modifier,
    unitAlbumImage: @Composable (() -> Unit?)?,
    stringTitleItem: String,
    stringSongAmount: String,
    stringMinutes: String,
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
                    .size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                if (unitAlbumImage != null) {
                    unitAlbumImage()
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_playlist_play_24),
                        contentDescription = stringResource(id = R.string.str_playlists),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Text(
                modifier = Modifier
                    .layoutId("TextTitle")
                    .width(260.dp)
                    .padding(start = 15.dp, top = 20.dp),
                text = stringTitleItem,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Text(
                modifier = Modifier
                    .layoutId("TextSubtitle")
                    .width(260.dp)
                    .padding(start = 15.dp, bottom = 20.dp),
                text = stringSongAmount + " " +
                        stringResource(id = R.string.str_lc_tracks) + " · " +
                        stringMinutes + " " +
                        stringResource(id = R.string.str_lc_minutes),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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