package com.alessiocameroni.revomusicplayer.settings.customization.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.alessiocameroni.revomusicplayer.R

@Composable
fun PlayerLayoutDialog(
    modifier: Modifier,
    openDialog: MutableState<Boolean>
) {
    Dialog(onDismissRequest = { openDialog.value = false }) {
        val radioOptions = listOf(
            stringResource(id = R.string.str_left),
            stringResource(id = R.string.str_center),
            stringResource(id = R.string.str_right),
        )
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1]) }

        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            val constraints = ConstraintSet {
                val boxDemo = createRefFor("BoxDemo")
                val titleDialog = createRefFor("TitleDialog")
                val dividerTitleColumn = createRefFor("DividerTitleColumn")
                val columnSelection = createRefFor("ColumnSelection")
                val dividerColumnActions = createRefFor("DividerColumnActions")
                val boxActionButtons = createRefFor("BoxActionButtons")

                constrain(boxDemo) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }

                constrain(titleDialog) {
                    start.linkTo(parent.start)
                    top.linkTo(boxDemo.bottom)
                    end.linkTo(parent.end)
                }

                constrain(dividerTitleColumn) {
                    start.linkTo(parent.start)
                    top.linkTo(titleDialog.bottom)
                    end.linkTo(parent.end)
                }

                constrain(columnSelection) {
                    start.linkTo(parent.start)
                    top.linkTo(dividerTitleColumn.bottom)
                    end.linkTo(parent.end)
                }

                constrain(dividerColumnActions) {
                    start.linkTo(parent.start)
                    top.linkTo(columnSelection.bottom)
                    end.linkTo(parent.end)
                }

                constrain(boxActionButtons) {
                    start.linkTo(parent.start)
                    top.linkTo(dividerColumnActions.bottom)
                    end.linkTo(parent.end)
                }
            }

            ConstraintLayout(
                constraints,
                modifier = Modifier
                    .padding(24.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .layoutId("BoxDemo")
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .fillMaxWidth()
                        .height(190.dp)
                ) {
                    val imgConstraints = ConstraintSet{
                        val imagePreview = createRefFor("ImagePreview")

                        constrain(imagePreview) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                    }

                    ConstraintLayout(imgConstraints, modifier = Modifier.fillMaxSize()) {
                        PlayerLayoutImagePreview(
                            modifier = Modifier
                                .layoutId("ImagePreview")
                                .height(170.dp),
                            selectedOption = selectedOption
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .layoutId("TitleDialog")
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.str_choosecontrols),
                    style = MaterialTheme.typography.headlineSmall
                )

                Divider(
                    modifier = Modifier
                        .layoutId("DividerTitleColumn")
                        .padding(vertical = 8.dp)
                )

                Column(
                    modifier = Modifier
                        .layoutId("ColumnSelection")
                        .fillMaxWidth()
                        .selectableGroup()
                ) {
                    radioOptions.forEach { text ->
                        Row(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.large)
                                .fillMaxWidth()
                                .height(46.dp)
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = { onOptionSelected(text) },
                                    role = Role.RadioButton
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }

                Divider(
                    modifier = Modifier
                        .layoutId("DividerColumnActions")
                        .padding(vertical = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .layoutId("BoxActionButtons")
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                ) {
                    val constraintsActionButtons = ConstraintSet {
                        val buttonCancel = createRefFor("ButtonCancel")
                        val buttonConfirm =
                            createRefFor("ButtonConfirm")

                        constrain(buttonConfirm) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }

                        constrain(buttonCancel) {
                            top.linkTo(parent.top)
                            end.linkTo(buttonConfirm.start)
                        }
                    }

                    ConstraintLayout(
                        constraintsActionButtons,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(
                            modifier = Modifier
                                .layoutId("ButtonCancel")
                                .padding(horizontal = 8.dp),
                            onClick = { openDialog.value = false }
                        ) {
                            Text(text = stringResource(id = R.string.str_cancel))
                        }

                        TextButton(
                            modifier = Modifier
                                .layoutId("ButtonConfirm"),
                            onClick = {  }
                        ) {
                            Text(text = stringResource(id = R.string.str_ok))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerLayoutImagePreview(
    modifier: Modifier,
    selectedOption: String
) {
    val darkTheme = isSystemInDarkTheme()

    when {
        darkTheme && selectedOption == "Left" -> {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ill_dark_left_player_controls),
                contentDescription = "String"
            )
        }
        darkTheme && selectedOption == "Center" -> {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ill_dark_center_player_controls),
                contentDescription = "String"
            )
        }
        darkTheme && selectedOption == "Right" -> {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ill_dark_right_player_controls),
                contentDescription = "String"
            )
        }
        !darkTheme && selectedOption == "Left" -> {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ill_light_left_player_controls),
                contentDescription = "String"
            )
        }
        !darkTheme && selectedOption == "Center" -> {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ill_light_center_player_controls),
                contentDescription = "String"
            )
        }
        !darkTheme && selectedOption == "Right" -> {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ill_light_right_player_controls),
                contentDescription = "String"
            )
        }
    }
}
