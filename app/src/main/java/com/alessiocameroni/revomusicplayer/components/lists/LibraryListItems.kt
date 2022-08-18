package com.alessiocameroni.revomusicplayer.components.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId

@Preview(showBackground = true)
@Composable
fun Preview() {
    OneColumnItem(
        stringTitleItem = "Main Title",
        stringSubtitleItem = "Subtitle"
    )
}

@Composable
fun OneColumnItem(
    stringTitleItem: String,
    stringSubtitleItem: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
    ) {
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

            }

            constrain(textSubtitle) {

            }

            constrain(menuButton) {

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
                    .size(64.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.primary)
            ) {

            }

            /*Text(
                modifier = Modifier.layoutId("TextTitle"),
                text = stringTitleItem
            )*/
        }
    }
}