package com.meltix.revo.ui.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.meltix.revo.R
import com.meltix.revo.ui.navigation.Screens

@Composable
fun WelcomeHeader() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val stringRevo = stringResource(id = R.string.app_name)

        Text(text = stringResource(id = R.string.str_heyThere))
        Text(
            buildAnnotatedString {
                append(stringResource(id = R.string.str_welcome))
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(" $stringRevo")
                }
            }
        )
    }
}

@Composable
fun ListPermissionRow(
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

@Composable
fun ForwardAppButton(
    navController: NavController,
    enabledState: Boolean
) {
    Button(
        onClick = {
            navController.navigate(Screens.MainScreen.route) {
                popUpTo(Screens.WelcomeScreen.route) {
                    inclusive = true
                }
            }
        },
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth(),
        enabled = enabledState
    ) {
        Icon(
            painter =
            painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
            contentDescription = stringResource(id = R.string.str_letsGo),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(ButtonDefaults.IconSize)
        )

        Text(text = stringResource(id = R.string.str_letsGo))
    }
}