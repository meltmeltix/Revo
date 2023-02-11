package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alessiocameroni.revomusicplayer.R

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AlbumViewHeader(
        leadingUnit = {  }
    )
}

@Composable
internal fun AlbumViewHeader(
    headlineTextString: String? = null,
    supportingTextString: String? = null,
    leadingUnit: @Composable () -> Unit?,
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 25.dp)
                .padding(bottom = 25.dp)
                .clip(MaterialTheme.shapes.medium)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(120.dp),
                painter = painterResource(id = R.drawable.ic_outlined_album_24),
                contentDescription = stringResource(id = R.string.desc_albumImage),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.15f))
            )

            HeaderText(
                headlineTextString = headlineTextString,
                supportingTextString = supportingTextString
            )

            leadingUnit()
        }
    }
}

@Composable
private fun HeaderText(
    headlineTextString: String?,
    supportingTextString: String?
) {
    val headline: String = headlineTextString ?: "Album Title"
    val supporting: String = supportingTextString ?: "0 Songs - 00:00 Minutes"

    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier,
                text = headline,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Text(
                modifier = Modifier,
                text = supporting,
                color = Color.White
            )
        }
    }
}

@Composable
private fun HeaderButtons(

) {

}