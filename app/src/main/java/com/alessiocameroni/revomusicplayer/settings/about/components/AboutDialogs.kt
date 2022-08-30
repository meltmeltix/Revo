package com.alessiocameroni.revomusicplayer.settings.about.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.alessiocameroni.revomusicplayer.R

@Composable
fun CreditsDialogDetails(
    modifier: Modifier,
    openDialog: MutableState<Boolean>,
    stringTitle: String,
    stringDescription: String,
    unitProfile: @Composable() (() -> Unit)?,
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
                        .clip(RoundedCornerShape(16.dp))
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