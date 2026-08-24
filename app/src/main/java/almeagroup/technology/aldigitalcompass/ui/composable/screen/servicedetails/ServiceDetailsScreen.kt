package almeagroup.technology.aldigitalcompass.ui.composable.screen.servicedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import almeagroup.technology.aldigitalcompass.data.model.ServiceModel
import almeagroup.technology.aldigitalcompass.ui.composable.shared.RBVRHContentWrapper
import almeagroup.technology.aldigitalcompass.ui.composable.shared.RBVRHEmptyView
import almeagroup.technology.aldigitalcompass.ui.state.DataUiState
import almeagroup.technology.aldigitalcompass.ui.theme.ChipBackground
import almeagroup.technology.aldigitalcompass.ui.viewmodel.ServiceDetailsViewModel
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ServiceDetailsScreen(
    serviceId: Int,
    modifier: Modifier = Modifier,
    viewModel: ServiceDetailsViewModel = koinViewModel(),
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {
    val serviceState by viewModel.serviceState.collectAsState()
    LaunchedEffect(serviceId) { viewModel.observeServiceById(serviceId) }
    ServiceDetailsContent(serviceState, modifier, onNavigateToCheckout)
}

@Composable
private fun ServiceDetailsContent(
    serviceState: DataUiState<ServiceModel>,
    modifier: Modifier = Modifier,
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {
    RBVRHContentWrapper(
        dataState = serviceState,
        modifier = modifier,
        dataPopulated = {
            ServicesDetailsPopulated((serviceState as DataUiState.Populated).data, onNavigateToCheckout = onNavigateToCheckout)
        },
        dataEmpty = {
            RBVRHEmptyView(
                modifier = Modifier.fillMaxSize(),
                primaryText = "Service information unavailable",
            )
        },
    )
}

@Composable
private fun ServicesDetailsPopulated(
    service: ServiceModel,
    modifier: Modifier = Modifier,
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {
    var selectedTime by remember { mutableStateOf(service.availableTime?.firstOrNull()) }
    LazyColumn(modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 28.dp)) {
        item {
            AsyncImage(
                model = service.imageUrl,
                contentDescription = service.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(280.dp).clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)),
            )
        }
        item {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Surface(color = ChipBackground, shape = RoundedCornerShape(50)) {
                    Text(service.category, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), style = MaterialTheme.typography.labelMedium)
                }
                Text(service.name, style = MaterialTheme.typography.headlineSmall)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("From £${service.price.toInt()}", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.width(10.dp))
                    Text("·", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.width(10.dp))
                    Icon(Icons.Rounded.Schedule, null, Modifier.size(18.dp), MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.width(4.dp))
                    Text("${service.durationMinutes} min", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text(service.description, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyLarge)
                Text("What you’ll receive", style = MaterialTheme.typography.titleLarge)
                service.features.forEach { feature ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.CheckCircle, null, Modifier.size(22.dp), MaterialTheme.colorScheme.secondary)
                        Spacer(Modifier.width(10.dp))
                        Text(feature, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Text("Available times", style = MaterialTheme.typography.titleLarge)
            }
        }
        item {
            LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(service.availableTime.orEmpty()) { time ->
                    val chosen = selectedTime == time
                    Box(
                        modifier = Modifier
                            .background(if (chosen) MaterialTheme.colorScheme.primary else ChipBackground, RoundedCornerShape(50))
                            .clickable { selectedTime = time }
                            .padding(horizontal = 18.dp, vertical = 10.dp),
                    ) {
                        Text(
                            time.format(DateTimeFormatter.ofPattern("h:mm a")),
                            color = if (chosen) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        }
        item {
            Button(
                onClick = { onNavigateToCheckout(service.id) },
                modifier = Modifier.fillMaxWidth().padding(20.dp).height(54.dp),
                enabled = selectedTime != null,
            ) {
                Text("Book Consultation")
            }
        }
    }
}
