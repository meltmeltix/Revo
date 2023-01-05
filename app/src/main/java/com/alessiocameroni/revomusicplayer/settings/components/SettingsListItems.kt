package com.alessiocameroni.revomusicplayer.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsLUnitItem(
    modifier: Modifier,
    stringMainTitle: String,
    stringSubtitle: String,
    leadingUnit: @Composable (() -> Unit?)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .size(60.dp),
            contentAlignment = Alignment.Center
        ) {
            leadingUnit()
        }

        Column(
            modifier = Modifier
                .padding(end = 15.dp)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringMainTitle,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SettingsLUnitTitleItem(
    modifier: Modifier,
    stringMainTitle: String,
    leadingUnit: @Composable (() -> Unit?)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .size(60.dp),
            contentAlignment = Alignment.Center
        ) {
            leadingUnit()
        }

        Column(
            modifier = Modifier
                .padding(end = 15.dp)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringMainTitle,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun SettingsRUnitItem(
    modifier: Modifier,
    stringMainTitle: String,
    stringSubtitle: String,
    trailingUnit: @Composable (() -> Unit?)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(start = 15.dp)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringMainTitle,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .size(60.dp),
            contentAlignment = Alignment.Center
        ) {
            trailingUnit()
        }
    }
}

@Composable
fun SettingsRUnitTitleItem(
    modifier: Modifier,
    stringMainTitle: String,
    trailingUnit: @Composable () -> Unit?
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(start = 25.dp)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringMainTitle,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .size(60.dp),
            contentAlignment = Alignment.Center
        ) {
            trailingUnit()
        }
    }
}

@Composable
fun SettingsTextItem(
    modifier: Modifier,
    stringMainTitle: String,
    stringSubtitle: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 15.dp)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringMainTitle,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun SettingsTitleItem(
    modifier: Modifier,
    stringMainTitle: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 25.dp)
                .padding(top = 16.dp, bottom = 20.dp)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringMainTitle,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun SectionTitle(
    modifier: Modifier,
    stringTitle: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(25.dp, 25.dp, 25.dp, 0.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            text = stringTitle,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun InfoText(
    modifier: Modifier,
    stringText: String
) {
    Text(
        modifier = modifier
            .padding(horizontal = 25.dp, vertical = 10.dp)
            .fillMaxWidth(),
        text = stringText,
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 14.sp
    )
}