package almeagroup.technology.aldigitalcompass.ui.composable.screen.bookings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EventAvailable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import almeagroup.technology.aldigitalcompass.ui.composable.shared.RBVRHContentWrapper
import almeagroup.technology.aldigitalcompass.ui.state.BookingUiState
import almeagroup.technology.aldigitalcompass.ui.state.DataUiState
import almeagroup.technology.aldigitalcompass.ui.theme.ChipBackground
import almeagroup.technology.aldigitalcompass.ui.theme.Success
import almeagroup.technology.aldigitalcompass.ui.viewmodel.BookingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookingsScreen(
    modifier: Modifier = Modifier,
    viewModel: BookingViewModel = koinViewModel(),
) {
    val bookingsState by viewModel.bookingsState.collectAsState()
    var canceledBookingNumber by remember { mutableStateOf("") }
    var shouldShowDialog by remember { mutableStateOf(false) }

    BookingsContent(
        bookingsState,
        modifier,
        onCancelBookingButtonClick = { bookingNumber ->
            canceledBookingNumber = bookingNumber
            shouldShowDialog = true
        },
    )

    if (shouldShowDialog) {
        AlertDialog(
            onDismissRequest = { shouldShowDialog = false },
            title = { Text("Cancel this booking?") },
            text = { Text("This consultation will be removed from your upcoming sessions.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.cancelBooking(canceledBookingNumber)
                    shouldShowDialog = false
                }) {
                    Text("Cancel booking", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton(onClick = { shouldShowDialog = false }) { Text("Keep booking") } },
        )
    }
}

@Composable
private fun BookingsContent(
    bookingsState: DataUiState<List<BookingUiState>>,
    modifier: Modifier = Modifier,
    onCancelBookingButtonClick: (bookingNumber: String) -> Unit,
) {
    RBVRHContentWrapper(
        dataState = bookingsState,
        modifier = modifier,
        dataPopulated = {
            BookingsPopulated((bookingsState as DataUiState.Populated).data, onCancelBookingButtonClick = onCancelBookingButtonClick)
        },
        dataEmpty = { EmptyBookings() },
    )
}

@Composable
private fun EmptyBookings() {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Surface(color = ChipBackground, shape = RoundedCornerShape(24.dp)) {
            Icon(Icons.Rounded.EventAvailable, null, Modifier.padding(24.dp).size(52.dp), MaterialTheme.colorScheme.secondary)
        }
        Spacer(Modifier.size(22.dp))
        Text("No bookings yet", style = MaterialTheme.typography.headlineSmall)
        Text("Browse Services from Home to schedule your first consultation.", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun BookingsPopulated(
    bookings: List<BookingUiState>,
    modifier: Modifier = Modifier,
    onCancelBookingButtonClick: (bookingNumber: String) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Text("Your consultations", style = MaterialTheme.typography.headlineSmall)
        }
        items(bookings, key = { it.bookingNumber }) { booking ->
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp),
            ) {
                Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(booking.serviceName, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                        Surface(color = Success.copy(alpha = 0.12f), shape = RoundedCornerShape(50)) {
                            Text("Confirmed", color = Success, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp), style = MaterialTheme.typography.labelMedium)
                        }
                    }
                    Text(booking.timestamp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Session #${booking.bookingNumber}", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium)
                    TextButton(onClick = { onCancelBookingButtonClick(booking.bookingNumber) }) {
                        Text("Cancel", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}
