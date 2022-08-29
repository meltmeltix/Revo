package com.alessiocameroni.revomusicplayer.settings.about.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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
    Box( modifier = modifier ) {
        val constraints = ConstraintSet {
            val imageProfile = createRefFor("ImageProfile")
            val boxText = createRefFor("BoxText")

            constrain(imageProfile) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }

            constrain(boxText) {
                start.linkTo(imageProfile.end)
                top.linkTo(parent.top)
            }
        }

        ConstraintLayout(constraints, Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .layoutId("ImageProfile")
                    .padding(horizontal = 25.dp, vertical = 15.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                if (unitProfile != null) {
                    unitProfile()
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_account_circle_24),
                        contentDescription = stringResource(id = R.string.str_usericon),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }


            Box(
                modifier = Modifier
                    .layoutId("BoxText")
                    .padding(vertical = 15.dp)
                    .height(100.dp)
            ) {
                val constraintsText = ConstraintSet {
                    val textMadeWithLove = createRefFor("TextMadeWithLove")
                    val textDeveloper = createRefFor("TextDeveloper")

                    constrain(textMadeWithLove) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(textDeveloper.top)
                    }
                    constrain(textDeveloper) {
                        start.linkTo(parent.start)
                        top.linkTo(textMadeWithLove.bottom)
                        bottom.linkTo(parent.bottom)
                    }
                }

                ConstraintLayout(constraintsText, Modifier.fillMaxSize()) {
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
}

@Composable
fun CreditsItem(
    modifier: Modifier,
    stringTitle: String,
    stringDescription: String,
    unitProfile: @Composable (() -> Unit)?
) {
    Box(
        modifier = modifier
    ) {
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
                    .padding(24.dp, 10.dp, 16.dp, 10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .size(54.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                if (unitProfile != null) {
                    unitProfile()
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_account_circle_24),
                        contentDescription = stringResource(id = R.string.str_usericon),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Box(
                modifier = Modifier
                    .layoutId("BoxText")
                    .padding(vertical = 10.dp)
                    .width(270.dp)
            ) {
                val constraintsText = ConstraintSet {
                    val textTitle = createRefFor("TextTitle")
                    val textDescription = createRefFor("TextDescription")

                    constrain(textTitle) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(textDescription.top)
                    }

                    constrain(textDescription) {
                        start.linkTo(parent.start)
                        top.linkTo(textTitle.bottom)
                    }
                }

                ConstraintLayout(
                    constraintsText,
                    modifier = Modifier.fillMaxWidth()
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
}

@Preview
@Composable
fun PreviewComposable() {
    BigCardAboutItem(
        modifier = Modifier
            .padding(14.dp, 16.dp, 14.dp, 0.dp)
            .fillMaxWidth(),
        unitBanner = {
            Image(
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.ill_revo_banner),
                contentDescription = "Description wee"
            )
        },
        cardShape = RoundedCornerShape(12.dp)
    )
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