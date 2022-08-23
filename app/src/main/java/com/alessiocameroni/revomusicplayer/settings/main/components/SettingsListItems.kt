package com.alessiocameroni.revomusicplayer.settings.main.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId

@Composable
fun SettingsCategoryItem(
    modifier: Modifier,
    painterIcon: Painter,
    stringTitleItem: String,
    stringSubtitleItem: String,
) {
    Box(
        modifier = modifier
    ) {
        val constraints = ConstraintSet {
            val settingIcon = createRefFor("SettingIcon")
            val textTitle = createRefFor("TextTitle")
            val textSubtitle = createRefFor("TextSubtitle")

            constrain(settingIcon) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }

            constrain(textTitle) {
                start.linkTo(settingIcon.end)
                top.linkTo(parent.top)
                bottom.linkTo(textSubtitle.top)
            }

            constrain(textSubtitle) {
                start.linkTo(settingIcon.end)
                top.linkTo(textTitle.bottom)
                bottom.linkTo(parent.bottom)
            }
        }

        ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
            Icon(
                modifier = Modifier
                    .layoutId("SettingIcon")
                    .padding(start = 30.dp)
                    .size(26.dp),
                painter = painterIcon,
                contentDescription = stringTitleItem,
            )

            Text(
                modifier = Modifier
                    .layoutId("TextTitle")
                    .padding(start = 15.dp, top = 18.dp)
                    .width(285.dp),
                text = stringTitleItem,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Text(
                modifier = Modifier
                    .layoutId("TextSubtitle")
                    .padding(start = 15.dp, bottom = 18.dp)
                    .width(285.dp),
                text = stringSubtitleItem,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Composable
fun SettingsActionItem(
    modifier: Modifier,
    stringTitleItem: String,
    stringSubtitleItem: String,
    unitAction: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        val constraints = ConstraintSet {
            val textTitle = createRefFor("TextTitle")
            val textSubtitle = createRefFor("TextSubtitle")
            val boxAction = createRefFor("BoxAction")

            constrain(textTitle) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(textSubtitle.top)
            }

            constrain(textSubtitle) {
                start.linkTo(parent.start)
                top.linkTo(textTitle.bottom)
            }

            constrain(boxAction) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        }

        ConstraintLayout(constraints, modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .layoutId("TextTitle")
                    .padding(start = 25.dp, top = 18.dp)
                    .width(270.dp),
                text = stringTitleItem,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Text(
                modifier = Modifier
                    .layoutId("TextSubtitle")
                    .padding(start = 25.dp, bottom = 18.dp)
                    .width(270.dp),
                text = stringSubtitleItem,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )

            Box(
                modifier = Modifier
                    .layoutId("BoxAction")
                    .padding(top = 14.dp, end = 15.dp)
                    .size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                unitAction()
            }
        }
    }
}

@Composable
fun SettingsTitleActionItem(
    modifier: Modifier,
    stringTitleItem: String,
    unitAction: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
    ) {
        val constraints = ConstraintSet {
            val textTitle = createRefFor("TextTitle")
            val boxAction = createRefFor("BoxAction")

            constrain(textTitle) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }

            constrain(boxAction) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        }

        ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .layoutId("TextTitle")
                    .padding(start = 25.dp)
                    .width(270.dp),
                text = stringTitleItem,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Box(
                modifier = Modifier
                    .layoutId("BoxAction")
                    .padding(end = 15.dp)
                    .size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                unitAction()
            }
        }
    }
}


@Composable
fun SectionTitle(
    modifier: Modifier,
    stringTitle: String,
) {
    Box(
        modifier = modifier
    ) {
        Text(
            text = stringTitle,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(25.dp, 25.dp, 25.dp, 0.dp)
                .fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}