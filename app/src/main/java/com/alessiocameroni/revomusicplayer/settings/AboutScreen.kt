package com.alessiocameroni.revomusicplayer.settings

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.navigation.Screens
import com.alessiocameroni.revomusicplayer.components.lists.SectionTitle
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec,
        rememberTopAppBarState()
    )

    RevoMusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    LargeTopAppBar(
                        title = { Text(text = stringResource(id = R.string.str_about)) },
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.navigate(Screens.SettingsScreen.route) }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                                    contentDescription = stringResource(id = R.string.desc_back)
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior
                    )
                },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        SectionTitle(
                            stringTitle = stringResource(id = R.string.str_developer),
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(22.dp))
                                .clickable { }
                        ) {
                            MadeWithLoveItem()
                        }

                        Divider()
                        
                        SectionTitle(
                            stringTitle = stringResource(id = R.string.str_specialthanks), 
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(22.dp))
                                .clickable { }
                        ) {

                        }
                    }
                }
            )
        }
    }
}

@Composable
fun MadeWithLoveItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val constraints = ConstraintSet {
            val imageProfile = createRefFor("ImageProfile")
            val boxText = createRefFor("BoxText")

            constrain(imageProfile) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }

            constrain(boxText) {
                start.linkTo(imageProfile.end)
                top.linkTo(parent.top)
            }
        }

        ConstraintLayout(constraints, Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .layoutId("ImageProfile")
                    .padding(horizontal = 25.dp, vertical = 15.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .size(100.dp),
                painter = painterResource(id = R.drawable.ill_meltix_200),
                contentDescription = stringResource(id = R.string.desc_meltix)
            )

            Box(
                modifier = Modifier
                    .layoutId("BoxText")
                    .padding(vertical = 15.dp)
                    .height(100.dp)
            ) {
                val constraintsText = ConstraintSet {
                    val textMadeWithLove = createRefFor("TextMadeWithLove")
                    val textDeveloper = createRefFor("TextDeveloper")

                    constrain(textMadeWithLove) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(textDeveloper.top)
                    }
                    constrain(textDeveloper) {
                        start.linkTo(parent.start)
                        top.linkTo(textMadeWithLove.bottom)
                        bottom.linkTo(parent.bottom)
                    }
                }

                ConstraintLayout(constraintsText, Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier
                            .layoutId("TextMadeWithLove"),
                        text = stringResource(id = R.string.str_madewithloveby),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        modifier = Modifier
                            .layoutId("TextDeveloper"),
                        text = stringResource(id = R.string.str_alessiocameroni),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}
