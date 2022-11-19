package com.alessiocameroni.revomusicplayer.settings.customization.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.alessiocameroni.revomusicplayer.R

@SuppressLint("CoroutineCreationDuringComposition")
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

        val (selectedOption, onOptionSelected) = remember {
            mutableStateOf(radioOptions[1])
        }

        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            val constraints = ConstraintSet {
                val rowImagePreview = createRefFor("RowImagePreview")
                val titleDialog = createRefFor("TitleDialog")
                val dividerTitleColumn = createRefFor("DividerTitleColumn")
                val columnSelection = createRefFor("ColumnSelection")
                val dividerColumnActions = createRefFor("DividerColumnActions")
                val rowActionButtons = createRefFor("RowActionButtons")

                constrain(rowImagePreview) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }

                constrain(titleDialog) {
                    start.linkTo(parent.start)
                    top.linkTo(rowImagePreview.bottom)
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

                constrain(rowActionButtons) {
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


                Text(
                    modifier = Modifier
                        .layoutId("TitleDialog")
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.str_chooseControls),
                    style = MaterialTheme.typography.headlineSmall
                )

                Divider(
                    modifier = Modifier
                        .layoutId("DividerTitleColumn")
                        .padding(vertical = 8.dp)
                )



                Divider(
                    modifier = Modifier
                        .layoutId("DividerColumnActions")
                        .padding(vertical = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .layoutId("RowActionButtons")
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
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
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text(text = stringResource(id = R.string.str_ok))
                    }
                }
            }
        }
    }
}
