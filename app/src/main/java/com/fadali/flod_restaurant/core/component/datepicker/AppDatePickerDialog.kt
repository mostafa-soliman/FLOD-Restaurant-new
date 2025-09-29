package com.fadali.flod_restaurant.core.component.datepicker

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.appText
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerDialog(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    dateRange: ClosedRange<LocalDate> = LocalDate.of(1900, 1, 1)..LocalDate.now(),
    title: String? = null,
    confirmBtnText: String = stringResource(R.string.date_picker_confirm_btn),
    confirmBtnEnabled: Boolean = true,
    dismissBtnText: String = stringResource(R.string.date_picker_dismiss_btn),
    selectedDate: LocalDate = dateRange.endInclusive,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    if (!isVisible) return

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = localDateToMillis(selectedDate),
        yearRange = dateRange.start.year..dateRange.endInclusive.year,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val date = Instant.ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()

                return !date.isBefore(dateRange.start) && !date.isAfter(dateRange.endInclusive)
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year in (dateRange.start.year..dateRange.endInclusive.year)
            }
        }
    )

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                enabled = confirmBtnEnabled,
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val localDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(localDate)
                    }
                    onDismiss()
                }
            ) {
                Text(
                    text = confirmBtnText,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = dismissBtnText,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.appText,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            title = {
                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.appText,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(DatePickerTitlePadding)
                    )
                } else {
                    DatePickerDefaults.DatePickerTitle(
                        displayMode = DisplayMode.Input,
                        modifier = Modifier.padding(DatePickerTitlePadding)
                    )
                }
            },
        )
    }
}

private val DatePickerTitlePadding = PaddingValues(start = 24.dp, end = 12.dp, top = 16.dp)

private fun localDateToMillis(date: LocalDate): Long {
    return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

@Preview(showBackground = true)
@Composable
private fun AppDatePickerDialogPreview() {
    FLODRestaurantTheme {
        AppDatePickerDialog(
            isVisible = true,
            title = "Select Your Birthday",
            selectedDate = LocalDate.now(),
            onDateSelected = {},
            onDismiss = {}
        )
    }
}
