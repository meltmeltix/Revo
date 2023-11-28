package com.meltix.revo.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.meltix.revo.R
import com.meltix.revo.ui.theme.RevoTheme

@Composable
fun LibraryPermissions(
    onButtonClick: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            snackbarHost = { /*TODO Add snackbar and permissions*/ }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(24.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outlined_folder_off_24),
                    contentDescription = "",
                    modifier = Modifier
                        .size(50.dp)
                        .aspectRatio(1f),
                    tint = MaterialTheme.colorScheme.onSurface
                )
    
                Spacer(modifier = Modifier.height(20.dp))
    
                Text(
                    text = stringResource(id = R.string.permission),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
    
                Spacer(modifier = Modifier.height(10.dp))
    
                FilledTonalButton(
                    onClick = { onButtonClick() },
                ) { Text(text = stringResource(id = R.string.grant_permission)) }
            }
        }
    }
}

@Composable
fun LibraryLoading(itemType: String) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(R.string.loading, itemType.lowercase()),
                color = MaterialTheme.colorScheme.onSurface
            )
            LinearProgressIndicator(modifier = Modifier.clip(MaterialTheme.shapes.medium))
        }
    }
}

@Composable
fun LibraryEmpty(
    painterIcon: Painter,
    headlineText: String,
    supportingText: String,
    onButtonClick: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
        ) {
            Icon(
                painter = painterIcon,
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .aspectRatio(1f),
                tint = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = headlineText,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )
            
            Text(
                text = supportingText,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
    
            Spacer(modifier = Modifier.height(10.dp))
            
            FilledTonalButton(
                onClick = { onButtonClick() },
                contentPadding = PaddingValues(start = 16.dp, end = 24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                    contentDescription = stringResource(id = R.string.refresh)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.refresh))
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE,
    device = Devices.PIXEL_7,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun Preview() {
    RevoTheme {  }
}