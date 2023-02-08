package com.alessiocameroni.revomusicplayer.ui.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomePermissionRow(
    modifier: Modifier,
    number: Int,
    stringMainTitle: String,
    stringSubtitle: String,
    unitButton: @Composable (() -> Unit)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(25.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.primary)
                .size(25.dp)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Column(
            modifier = Modifier
                .padding(vertical = 25.dp)
                .padding(end = 15.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringMainTitle,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 15.sp,
            )
            
            Spacer(modifier = Modifier.height(4.dp))

            unitButton()
        }
    }
}