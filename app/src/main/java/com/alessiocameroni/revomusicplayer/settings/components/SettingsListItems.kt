package com.alessiocameroni.revomusicplayer.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.alessiocameroni.revomusicplayer.R

@Preview(showBackground = true)
@Composable
fun PreviewSettingsItem() {
    SettingsItem(
        modifier = Modifier.fillMaxWidth(),
        stringMainTitle = "This is a long title",
        stringSubtitle = "And this is a long string explaining",
        leadingUnit = {
            Icon(
                painter = painterResource(id = R.drawable.ic_outlined_settings_24),
                contentDescription = "Description"
            )
        },
        trailingUnit = {
            Switch(
                checked = false, onCheckedChange = {}
            )
        }
    )
}

@Composable
fun SettingsItem(
    modifier: Modifier,
    stringMainTitle: String,
    stringSubtitle: String,
    leadingUnit: @Composable (() -> Unit?),
    trailingUnit: @Composable (() -> Unit?)
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        val constraints = ConstraintSet {
            val leadingBox = createRefFor("LeadingBox")
            val trailingBox = createRefFor("TrailingBox")
            val textTitle = createRefFor("TextTitle")
            val textSubtitle = createRefFor("TextSubtitle")

            constrain(leadingBox) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }

            constrain(textTitle) {
                start.linkTo(leadingBox.end)
                top.linkTo(parent.top)
            }

            constrain(textSubtitle) {
                start.linkTo(leadingBox.end)
                top.linkTo(textTitle.bottom)
            }

            constrain(trailingBox) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        }

        ConstraintLayout(
            constraints,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .layoutId("LeadingBox")
                    .padding(horizontal = 5.dp, vertical = 8.dp)
                    .size(60.dp)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                leadingUnit()
            }

            Text(
                modifier = Modifier
                    .layoutId("TextTitle")
                    .padding(top = 12.dp),
                text = stringMainTitle,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )

            Text(
                modifier = Modifier
                    .layoutId("TextSubtitle"),
                text = stringSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp
            )

            Box(
                modifier = Modifier
                    .layoutId("TrailingBox")
                    .padding(horizontal = 5.dp, vertical = 8.dp)
                    .size(60.dp)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                trailingUnit()
            }
        }
    }
}

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
            }

            constrain(textTitle) {
                start.linkTo(settingIcon.end)
                top.linkTo(parent.top)
                bottom.linkTo(textSubtitle.top)
            }

            constrain(textSubtitle) {
                start.linkTo(settingIcon.end)
                top.linkTo(textTitle.bottom)
            }
        }

        ConstraintLayout(constraints, modifier = Modifier.fillMaxWidth()) {
            Icon(
                modifier = Modifier
                    .layoutId("SettingIcon")
                    .padding(start = 30.dp, top = 30.dp)
                    .size(26.dp),
                painter = painterIcon,
                contentDescription = stringTitleItem,
            )

            Text(
                modifier = Modifier
                    .layoutId("TextTitle")
                    .padding(start = 20.dp, top = 18.dp)
                    .width(280.dp),
                text = stringTitleItem,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Text(
                modifier = Modifier
                    .layoutId("TextSubtitle")
                    .padding(start = 20.dp, bottom = 18.dp)
                    .width(280.dp),
                text = stringSubtitleItem,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
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
            modifier = Modifier
                .padding(25.dp, 25.dp, 25.dp, 0.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            text = stringTitle,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}