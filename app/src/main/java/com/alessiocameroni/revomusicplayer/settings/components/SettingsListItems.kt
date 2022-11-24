package com.alessiocameroni.revomusicplayer.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId

@Composable
fun SettingsItem(
    modifier: Modifier,
    stringMainTitle: String,
    stringSubtitle: String,
    leadingUnit: @Composable (() -> Unit?),
    trailingUnit: @Composable (() -> Unit?)
) {
    Row(modifier = modifier) {
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
                    .padding(top = 15.dp)
                    .width(250.dp),
                text = stringMainTitle,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )

            Text(
                modifier = Modifier
                    .layoutId("TextSubtitle")
                    .width(250.dp),
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
fun SettingsLUnitItem(
    modifier: Modifier,
    stringMainTitle: String,
    stringSubtitle: String,
    leadingUnit: @Composable (() -> Unit?)
) {
    Row(modifier = modifier) {
        val constraints = ConstraintSet {
            val leadingBox = createRefFor("LeadingBox")
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
        }

        ConstraintLayout(
            constraints,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .layoutId("LeadingBox")
                    .padding(horizontal = 5.dp, vertical = 9.dp)
                    .size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                leadingUnit()
            }

            Text(
                modifier = Modifier
                    .layoutId("TextTitle")
                    .padding(top = 15.dp)
                    .width(310.dp),
                text = stringMainTitle,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )

            Text(
                modifier = Modifier
                    .layoutId("TextSubtitle")
                    .padding(bottom = 15.dp)
                    .width(310.dp),
                text = stringSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SettingsLUnitTitleItem(
    modifier: Modifier,
    stringMainTitle: String,
    leadingUnit: @Composable (() -> Unit?)
) {
    Row(modifier = modifier) {
        val constraints = ConstraintSet {
            val leadingBox = createRefFor("LeadingBox")
            val textTitle = createRefFor("TextTitle")

            constrain(leadingBox) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }

            constrain(textTitle) {
                start.linkTo(leadingBox.end)
                top.linkTo(parent.top)
            }
        }

        ConstraintLayout(
            constraints,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .layoutId("LeadingBox")
                    .padding(horizontal = 5.dp, vertical = 9.dp)
                    .size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                leadingUnit()
            }

            Text(
                modifier = Modifier
                    .layoutId("TextTitle")
                    .padding(top = 25.dp)
                    .width(310.dp),
                text = stringMainTitle,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun SettingsTextItem(
    modifier: Modifier,
    stringMainTitle: String,
    stringSubtitle: String
) {
    Row(modifier = modifier) {
        val constraints = ConstraintSet {
            val textTitle = createRefFor("TextTitle")
            val textSubtitle = createRefFor("TextSubtitle")

            constrain(textTitle) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
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
            Text(
                modifier = Modifier
                    .layoutId("TextTitle")
                    .padding(start = 25.dp, end = 25.dp, top = 15.dp)
                    .width(360.dp),
                text = stringMainTitle,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )

            Text(
                modifier = Modifier
                    .layoutId("TextSubtitle")
                    .padding(start = 25.dp, end = 25.dp, bottom = 15.dp)
                    .width(360.dp),
                text = stringSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun SectionTitle(
    modifier: Modifier,
    stringTitle: String,
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier
                .padding(25.dp, 25.dp, 25.dp, 0.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            text = stringTitle,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun InfoText(
    modifier: Modifier,
    stringTitle: String
) {
    Text(
        modifier = modifier
            .padding(horizontal = 25.dp, vertical = 10.dp),
        text = stringTitle,
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 14.sp
    )
}