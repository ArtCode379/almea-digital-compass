package almeagroup.technology.aldigitalcompass.ui.composable.screen.checkout

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import almeagroup.technology.aldigitalcompass.data.entity.BookingEntity

@Composable
fun CheckoutDialog(
    booking: BookingEntity,
    serviceName: String,
    preferredDate: String,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onConfirm,
        title = { Text("Consultation confirmed") },
        text = {
            Text(
                "Session #${booking.bookingNumber}\n\n$serviceName\n$preferredDate\n\nYour consultant will be waiting in the online conference or at the office at the scheduled time.",
            )
        },
        confirmButton = { Button(onClick = onConfirm) { Text("View Bookings") } },
        dismissButton = { TextButton(onClick = onConfirm) { Text("Done") } },
    )
}
