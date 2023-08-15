package com.meltix.revo.ui.screens.settings.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meltix.revo.R
import com.meltix.revo.ui.components.SmallImageContainer

@Composable
fun AppLogoItem() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
        Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter =
            painterResource(id = R.drawable.ill_revo_r_monochrome),
            contentDescription = "Desc",
            modifier = Modifier
                .padding(vertical = 30.dp)
                .size(50.dp),
            contentScale = ContentScale.Fit,
            colorFilter =
            ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer)
        )

        Image(
            painter =
            painterResource(id = R.drawable.ill_revo_text_logo),
            contentDescription = "Desc",
            modifier = Modifier
                .padding(vertical = 20.dp)
                .size(120.dp, 60.dp),
            contentScale = ContentScale.Fit,
            colorFilter =
            ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer)
        )
    }
}

@Composable
fun CenterCreditItem(
    titleText: String,
    subText: String,
    imageContent: @Composable (() -> Unit)
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SmallImageContainer(
            modifier = Modifier
                .clip(CircleShape)
                .size(70.dp),
            painterPlaceholder =
                painterResource(id = R.drawable.ic_outlined_account_circle_24)
        ) {
            imageContent()
        }

        Text(
            text = titleText,
            modifier = Modifier
                .padding(top = 5.dp),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.sp
        )

        Text(
            text = subText,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}