package com.alessiocameroni.revomusicplayer.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.ContentState

@Composable
fun SmallImageContainer(
    modifier: Modifier,
    painterPlaceholder: Painter,
    leadingUnit: @Composable () -> Unit?,
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .size(60.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterPlaceholder,
            contentDescription = stringResource(id = R.string.str_albumImage),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        leadingUnit()
    }
}

@Composable
fun LargeImageContainer(
    modifier: Modifier = Modifier,
    painterPlaceholder: Painter,
    leadingUnit: @Composable () -> Unit?,
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .size(110.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterPlaceholder,
            contentDescription = stringResource(id = R.string.str_albumImage),
            modifier = Modifier.size(35.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        leadingUnit()
    }
}

@Composable
fun LoadingContent(
    padding: PaddingValues,
    headlineString: String,
) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(bottom = 70.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = headlineString,
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(20.dp))

            LinearProgressIndicator(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium),
            )
        }
    }
}

@Composable
fun NoContentMessage(
    padding: PaddingValues,
    leadingIcon: Painter,
    headlineString: String,
    infoString: String
) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp)
                .padding(bottom = 70.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = leadingIcon,
                contentDescription = stringResource(id = R.string.str_songs),
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .size(50.dp)
            )
            Text(
                text = headlineString,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = infoString,
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun ContentSelector(
    state: ContentState,
    loadingUnit: @Composable (() -> Unit),
    failedUnit: @Composable (() -> Unit),
    contentUnit: @Composable (() -> Unit)
) {
    when(state) {
        ContentState.LOADING -> {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) { loadingUnit() }

        }
        ContentState.FAILURE -> {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) { failedUnit() }
        }
        ContentState.SUCCESS -> {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) { contentUnit() }
        }
    }
}