package com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.playerLayout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alessiocameroni.revomusicplayer.R

@Composable
internal fun PlayerLayoutPreviewHeader(
    modifier: Modifier = Modifier,
    selectedOption: String,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()
            .height(250.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        val darkTheme = isSystemInDarkTheme()
        val imageModifier = Modifier
            .width(240.dp)
            .height(200.dp)

        when {
            darkTheme && selectedOption == stringResource(id = R.string.str_left) -> {
                Image(
                    modifier = imageModifier,
                    painter = painterResource(id = R.drawable.ill_dark_left_player_controls),
                    contentDescription = "String"
                )
            }
            darkTheme && selectedOption == stringResource(id = R.string.str_center) -> {
                Image(
                    modifier = imageModifier,
                    painter = painterResource(id = R.drawable.ill_dark_center_player_controls),
                    contentDescription = "String"
                )
            }
            darkTheme && selectedOption == stringResource(id = R.string.str_right) -> {
                Image(
                    modifier = imageModifier,
                    painter = painterResource(id = R.drawable.ill_dark_right_player_controls),
                    contentDescription = "String"
                )
            }
            !darkTheme && selectedOption == stringResource(id = R.string.str_left) -> {
                Image(
                    modifier = imageModifier,
                    painter = painterResource(id = R.drawable.ill_light_left_player_controls),
                    contentDescription = "String"
                )
            }
            !darkTheme && selectedOption == stringResource(id = R.string.str_center) -> {
                Image(
                    modifier = imageModifier,
                    painter = painterResource(id = R.drawable.ill_light_center_player_controls),
                    contentDescription = "String"
                )
            }
            !darkTheme && selectedOption == stringResource(id = R.string.str_right) -> {
                Image(
                    modifier = imageModifier,
                    painter = painterResource(id = R.drawable.ill_light_right_player_controls),
                    contentDescription = "String"
                )
            }
        }
    }
}