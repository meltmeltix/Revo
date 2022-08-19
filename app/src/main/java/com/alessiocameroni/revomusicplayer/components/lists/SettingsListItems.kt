package com.alessiocameroni.revomusicplayer.components.lists

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
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
    SettingsMainItem(
        painterIcon = painterResource(id = R.drawable.ic_outlined_settings_24),
        stringTitleItem = "Setting title",
        stringSubtitleItem = "Setting subtitle"
    )
}

@Composable
fun SettingsMainItem(
    painterIcon: Painter,
    stringTitleItem: String,
    stringSubtitleItem: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
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
                    .padding(start = 15.dp, top = 20.dp)
                    .width(285.dp),
                text = stringTitleItem,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Text(
                modifier = Modifier
                    .layoutId("TextSubtitle")
                    .padding(start = 15.dp, bottom = 20.dp)
                    .width(285.dp),
                text = stringSubtitleItem,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}