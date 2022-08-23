package com.alessiocameroni.revomusicplayer.settings.about.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Preview(showBackground = true)
@Composable
fun PreviewComponent() {

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

        }

        ConstraintLayout(constraints, modifier = Modifier.fillMaxWidth()) {

        }
    }
}

@Composable
fun CreditsDialog(
    modifier: Modifier
) {

}