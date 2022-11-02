package com.alessiocameroni.revomusicplayer.settings.about.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.alessiocameroni.revomusicplayer.R

@Preview(showBackground = true)
@Composable
fun CreditsDialogDetailsPreview(){
    val openDialog = remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current

    CreditsDialogDetails(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .width(500.dp),
        openDialog = openDialog,
        stringTitle = stringResource(id = R.string.str_alessiocameroni),
        stringDescription = stringResource(id = R.string.bio_alessiocameroni),
        unitProfile = {
            Image(
                painter = painterResource(id = R.drawable.ill_meltix_200),
                contentDescription = stringResource(id = R.string.str_alessiocameroni)
            )
        }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            IconButton(
                onClick = { uriHandler.openUri("https://www.instagram.com/meltmeltix/") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_instagram_24),
                    contentDescription = stringResource(id = R.string.str_instgram)
                )
            }

            IconButton(
                onClick = { uriHandler.openUri("https://github.com/alessiocameroni") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_github_24),
                    contentDescription = stringResource(id = R.string.str_github)
                )
            }

            IconButton(
                onClick = { uriHandler.openUri("https://twitter.com/meltmeltix") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_twitter_24),
                    contentDescription = stringResource(id = R.string.str_twitter)
                )
            }
        }
    }
}

@Composable
fun CreditsDialogDetails(
    modifier: Modifier,
    openDialog: MutableState<Boolean>,
    stringTitle: String,
    stringDescription: String,
    unitProfile: @Composable (() -> Unit)?,
    columnButtonItems: @Composable () -> Unit
) {
    Dialog(onDismissRequest = { openDialog.value = false }) {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            val constraints = ConstraintSet {
                val boxImage = createRefFor("BoxImage")
                val textTitle = createRefFor("TextTitle")
                val textDescription = createRefFor("TextDescription")
                val divider = createRefFor("Divider")
                val boxSocialButtons = createRefFor("BoxSocialButtons")

                constrain(boxImage) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }

                constrain(textTitle) {
                    start.linkTo(parent.start)
                    top.linkTo(boxImage.bottom)
                    end.linkTo(parent.end)
                }

                constrain(textDescription) {
                    start.linkTo(parent.start)
                    top.linkTo(textTitle.bottom)
                    end.linkTo(parent.end)
                }

                constrain(divider) {
                    start.linkTo(parent.start)
                    top.linkTo(textDescription.bottom)
                    end.linkTo(parent.end)
                }

                constrain(boxSocialButtons) {
                    start.linkTo(parent.start)
                    top.linkTo(divider.bottom)
                    end.linkTo(parent.end)
                }
            }

            ConstraintLayout(
                constraints,
                modifier = Modifier
                    .padding(24.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .layoutId("BoxImage")
                        .padding(bottom = 16.dp)
                        .size(84.dp)
                        .clip(MaterialTheme.shapes.large)
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

                Text(
                    modifier = Modifier
                        .layoutId("TextTitle")
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    modifier = Modifier
                        .layoutId("TextDescription")
                        .padding(bottom = 14.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringDescription,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )

                Divider(
                    modifier = Modifier
                        .layoutId("Divider")
                        .padding(bottom = 14.dp)
                )

                Box(
                    modifier = Modifier
                        .layoutId("BoxSocialButtons")
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    columnButtonItems()
                }
            }
        }
    }
}