package com.alessiocameroni.revomusicplayer.settings.about.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.alessiocameroni.revomusicplayer.BuildConfig
import com.alessiocameroni.revomusicplayer.R

@Composable
fun BigCreditsItem(
    modifier: Modifier,
    stringTitle: String,
    stringName: String,
    unitProfile: @Composable () -> Unit
) {
    Row(modifier = modifier) {
        val constraints = ConstraintSet {
            val imageProfile = createRefFor("ImageProfile")
            val columnText = createRefFor("ColumnText")

            constrain(imageProfile) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }

            constrain(columnText) {
                start.linkTo(imageProfile.end)
                top.linkTo(parent.top)
            }
        }

        ConstraintLayout(constraints, Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .layoutId("ImageProfile")
                    .padding(15.dp, 15.dp, 25.dp, 15.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                if (unitProfile != null) {
                    unitProfile()
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_account_circle_24),
                        contentDescription = stringResource(id = R.string.str_userIcon),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Column(
                modifier = Modifier
                    .layoutId("ColumnText")
                    .padding(vertical = 15.dp)
                    .height(100.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .layoutId("TextMadeWithLove"),
                    text = stringTitle,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    modifier = Modifier
                        .layoutId("TextDeveloper")
                        .width(200.dp),
                    text = stringName,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Composable
fun CreditsItem(
    modifier: Modifier,
    stringTitle: String,
    stringDescription: String,
    unitProfile: @Composable (() -> Unit)?
) {
    Row(modifier = modifier) {
        val constraints = ConstraintSet {
            val boxImage = createRefFor("BoxImage")
            val boxText = createRefFor("BoxText")

            constrain(boxImage) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }

            constrain(boxText) {
                start.linkTo(boxImage.end)
                top.linkTo(parent.top)
            }
        }

        ConstraintLayout(constraints, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .layoutId("BoxImage")
                    .padding(15.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .size(54.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                if (unitProfile != null) {
                    unitProfile()
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_account_circle_24),
                        contentDescription = stringResource(id = R.string.str_userIcon),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Column(
                modifier = Modifier
                    .layoutId("BoxText")
                    .padding(vertical = 15.dp)
                    .width(270.dp)
            ) {
                Text(
                    modifier = Modifier
                        .layoutId("TextTitle")
                        .fillMaxWidth(),
                    text = stringTitle,
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Text(
                    modifier = Modifier
                        .layoutId("TextDescription")
                        .fillMaxWidth(),
                    text = stringDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun BigCardAboutItem(
    modifier: Modifier,
    unitBanner: @Composable () -> Unit,
    cardShape: CornerBasedShape
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = cardShape
    ) {
        val constraints = ConstraintSet {
            val boxBanner = createRefFor("BoxBanner")
            val boxText = createRefFor("BoxText")

            constrain(boxBanner) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }

            constrain(boxText) {
                start.linkTo(parent.start)
                top.linkTo(boxBanner.bottom)
                end.linkTo(parent.end)
            }
        }

        ConstraintLayout(
            constraints,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .layoutId("BoxBanner")
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                unitBanner()
            }

            Box(
                modifier = Modifier
                    .layoutId("BoxText")
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                val versionName = "App version: " + BuildConfig.VERSION_NAME

                Text(
                    text = versionName,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}