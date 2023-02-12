package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alessiocameroni.revomusicplayer.R

/**
 * Header components
 */
@Composable
internal fun AlbumViewHeader(
    headlineTextString: String? = null,
    supportingTextString: String? = null,
    leadingUnit: @Composable () -> Unit?,
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clip(MaterialTheme.shapes.extraLarge)
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

        HeaderButtons()
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
            .padding(25.dp)
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
private fun HeaderButtons() {
    Row(
        modifier = Modifier
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        FilledTonalButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(0.5f)
                .height(45.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24), 
                contentDescription = stringResource(id = R.string.str_play),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
            )

            Text(text = stringResource(id = R.string.str_play))
        }
        
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(0.5f)
                .height(45.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_shuffle_24),
                contentDescription = stringResource(id = R.string.str_shuffle),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
            )

            Text(text = stringResource(id = R.string.str_shuffle))
        }
    }
}


/**
 * List components
 */
@Composable
internal fun SectionTitleBar(
    stringTitle: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 15.dp)
            .padding(top = 20.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.primary,
            text = stringTitle,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
