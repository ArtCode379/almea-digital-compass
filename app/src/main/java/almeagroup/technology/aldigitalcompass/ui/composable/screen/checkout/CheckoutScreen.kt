package almeagroup.technology.aldigitalcompass.ui.composable.screen.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import almeagroup.technology.aldigitalcompass.data.entity.BookingEntity
import almeagroup.technology.aldigitalcompass.data.repository.ServiceRepository
import almeagroup.technology.aldigitalcompass.ui.state.DataUiState
import almeagroup.technology.aldigitalcompass.ui.viewmodel.CheckoutViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CheckoutScreen(
    serviceId: Int,
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onNavigateToBookingsScreen: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val bookingState by viewModel.orderState.collectAsStateWithLifecycle()
    val emailInvalidState by viewModel.emailInvalidState.collectAsStateWithLifecycle()
    val service = remember(serviceId) { ServiceRepository().getById(serviceId) }
    var phone by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    val isButtonEnabled by remember {
        derivedStateOf {
            viewModel.customerFirstName.isNotBlank() &&
                viewModel.customerLastName.isNotBlank() &&
                viewModel.customerEmail.isNotBlank() &&
                phone.isNotBlank() &&
                selectedDate.isNotBlank()
        }
    }

    if (bookingState is DataUiState.Populated) {
        CheckoutDialog((bookingState as DataUiState.Populated<BookingEntity>).data, service?.name.orEmpty(), selectedDate, onNavigateToBookingsScreen)
    }

    CheckoutContent(
        serviceName = service?.name.orEmpty(),
        servicePrice = service?.price ?: 0.0,
        customerFirstName = viewModel.customerFirstName,
        customerLastName = viewModel.customerLastName,
        customerEmail = viewModel.customerEmail,
        phone = phone,
        selectedDate = selectedDate,
        notes = notes,
        isEmailInvalid = emailInvalidState,
        modifier = modifier,
        focusManager = focusManager,
        isButtonEnabled = isButtonEnabled,
        onFirstNameChanged = viewModel::updateCustomerFirstName,
        onLastNameChanged = viewModel::updateCustomerLastName,
        onEmailChanged = viewModel::updateCustomerEmail,
        onPhoneChanged = { phone = it },
        onDateChanged = { selectedDate = it },
        onNotesChanged = { notes = it },
        onPlaceBookingButtonClick = { viewModel.placeBooking(serviceId) },
    )
}

@Composable
private fun CheckoutContent(
    serviceName: String,
    servicePrice: Double,
    customerFirstName: String,
    customerLastName: String,
    customerEmail: String,
    phone: String,
    selectedDate: String,
    notes: String,
    isEmailInvalid: Boolean,
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    isButtonEnabled: Boolean,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onDateChanged: (String) -> Unit,
    onNotesChanged: (String) -> Unit,
    onPlaceBookingButtonClick: () -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text("Book your consultation", style = MaterialTheme.typography.headlineSmall)
        Text("Tell us how to reach you. A consultant will review your request before the session.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
            Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier.weight(1f)) {
                    Text(serviceName, style = MaterialTheme.typography.titleMedium)
                    Text("Initial advisory session", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text("£${servicePrice.toInt()}", style = MaterialTheme.typography.titleMedium)
            }
        }
        CheckoutTextField(customerFirstName, onFirstNameChanged, "First name", Modifier.fillMaxWidth())
        CheckoutTextField(customerLastName, onLastNameChanged, "Last name", Modifier.fillMaxWidth())
        CheckoutTextField(customerEmail, onEmailChanged, "Email", Modifier.fillMaxWidth(), isError = isEmailInvalid, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
        CheckoutTextField(phone, onPhoneChanged, "Phone", Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
        OutlinedTextField(
            value = selectedDate,
            onValueChange = onDateChanged,
            label = { Text("Preferred date") },
            readOnly = true,
            trailingIcon = { Icon(Icons.Rounded.CalendarMonth, null) },
            modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true },
        )
        OutlinedTextField(
            value = notes,
            onValueChange = onNotesChanged,
            label = { Text("Notes (optional)") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = {
                focusManager.clearFocus()
                onPlaceBookingButtonClick()
            },
            enabled = isButtonEnabled,
            modifier = Modifier.fillMaxWidth().height(54.dp),
        ) {
            Text("Confirm Booking")
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        onDateChanged(Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")))
                    }
                    showDatePicker = false
                }) {
                    Text("Select")
                }
            },
            dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Cancel") } },
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun CheckoutTextField(
    input: String,
    onInputChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        modifier = modifier,
        enabled = enabled,
        label = { Text(labelText, style = MaterialTheme.typography.titleSmall) },
        isError = isError,
        supportingText = if (isError) ({ Text("Enter a valid email address") }) else null,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
        ),
    )
}
