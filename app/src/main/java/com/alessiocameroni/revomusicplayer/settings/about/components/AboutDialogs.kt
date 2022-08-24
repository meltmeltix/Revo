package com.alessiocameroni.revomusicplayer.settings.about.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun PreviewComposable() {
    CreditsDialogDetails(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .width(560.dp),
        stringTitle = "Very title",
        stringDescription = "Very very very description"
    )
}

@Composable
fun CreditsDialogDetails(
    modifier: Modifier,
    stringTitle: String,
    stringDescription: String
) {
    Box(
        modifier = modifier
    ) {

    }
}