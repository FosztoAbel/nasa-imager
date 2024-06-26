package shared

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledDatePickerDialog(
    onDateSelected: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    val currentMillis = Clock.System.now().toEpochMilliseconds()
    val datePickerState = rememberDateRangePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= currentMillis
        }
    })

    val selectedStartDate = datePickerState.selectedStartDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    val selectedEndDate = datePickerState.selectedEndDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        colors = DatePickerColors(
            containerColor = Color.DarkGray,
            titleContentColor = Color.White,
            headlineContentColor = Color.White,
            weekdayContentColor = Color.White,
            subheadContentColor = Color.White,
            navigationContentColor = Color.White,
            yearContentColor = Color.White,
            disabledYearContentColor = Color.White,
            currentYearContentColor = Color.White,
            selectedYearContentColor = Color.White,
            disabledSelectedYearContentColor = Color.White,
            selectedYearContainerColor = Color.Red,
            disabledSelectedYearContainerColor = Color.Red,
            dayContentColor = Color.White,
            disabledDayContentColor = Color.Gray,
            selectedDayContentColor = Color.White,
            disabledSelectedDayContentColor = Color.White,
            selectedDayContainerColor = Color(11,61,145),
            disabledSelectedDayContainerColor = Color(11,61,145),
            todayContentColor = Color.White,
            todayDateBorderColor = Color(252, 61, 33),
            dayInSelectionRangeContainerColor =  Color(11,61,145).copy(alpha = 0.6f),
            dayInSelectionRangeContentColor = Color.LightGray,
            dividerColor = Color.DarkGray,
            dateTextFieldColors = TextFieldDefaults.colors()
        ),
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                colors = ButtonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White,
                    disabledContainerColor = Color.DarkGray,
                    disabledContentColor = Color.Black
                ),
                onClick = {
                    onDateSelected(selectedStartDate, selectedEndDate)
                    onDismiss()
                }
            ) {
                Text(text = "Confirm", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            Button(
                colors = ButtonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White,
                    disabledContainerColor = Color.DarkGray,
                    disabledContentColor = Color.Black
                ),
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "Cancel", fontWeight = FontWeight.Bold)
            }
        }
    ) {
        DateRangePicker(
            colors = DatePickerColors(
                containerColor = Color.DarkGray,
                titleContentColor = Color.White,
                headlineContentColor = Color.White,
                weekdayContentColor = Color.White,
                subheadContentColor = Color.White,
                navigationContentColor = Color.White,
                yearContentColor = Color.White,
                disabledYearContentColor = Color.White,
                currentYearContentColor = Color.White,
                selectedYearContentColor = Color.White,
                disabledSelectedYearContentColor = Color.White,
                selectedYearContainerColor = Color.Red,
                disabledSelectedYearContainerColor = Color.Red,
                dayContentColor = Color.White,
                disabledDayContentColor = Color.Gray,
                selectedDayContentColor = Color.White,
                disabledSelectedDayContentColor = Color.White,
                selectedDayContainerColor = Color(11,61,145),
                disabledSelectedDayContainerColor = Color.Blue,
                todayContentColor = Color.White,
                todayDateBorderColor = Color(252, 61, 33),
                dayInSelectionRangeContainerColor =  Color(11,61,145).copy(alpha = 0.6f),
                dayInSelectionRangeContentColor = Color.LightGray,
                dividerColor = Color.DarkGray,
                dateTextFieldColors = TextFieldDefaults.colors()
            ),
            state = datePickerState,
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp, bottom = 16.dp),
            headline = null,
            title = null,
            showModeToggle = false
        )
    }
}

private fun convertMillisToDate(millis: Long): String {
    val instant = Instant.fromEpochMilliseconds(millis)
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dateTime.year}-${dateTime.monthNumber.toString().padStart(2, '0')}-${dateTime.dayOfMonth.toString().padStart(2, '0')}"
}