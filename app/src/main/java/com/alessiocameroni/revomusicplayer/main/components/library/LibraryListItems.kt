package com.alessiocameroni.revomusicplayer.main.components.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun OneColumnListItem(
    modifier: Modifier,
    stringTitleItem: String,
    stringSubtitleItem: String
) {
    Box( modifier = modifier ) {
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
                    .background(MaterialTheme.colorScheme.primary)
            ) {

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
                    onClick = { /*expanded = true*/ },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                        contentDescription = stringResource(id = R.string.str_settings)
                    )
                }
            }
        }
    }
}