package com.alessiocameroni.revomusicplayer.settings.about.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    CreditsItem(
        modifier = Modifier
            .fillMaxWidth(),
        stringTitle = "This is the title",
        stringDescription = "This is the subtitle"
    )
}

@Composable
fun MadeWithLoveItem(
    modifier: Modifier,
    stringTitle: String,
    stringName: String,
    painterProfile: Painter
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
            Image(
                modifier = Modifier
                    .layoutId("ImageProfile")
                    .padding(horizontal = 25.dp, vertical = 15.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .size(100.dp),
                painter = painterResource(id = R.drawable.ill_meltix_200),
                contentDescription = stringName
            )

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
                            .layoutId("TextDeveloper"),
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
    stringDescription: String
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
                    .background(Color.Blue)
            ) {

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