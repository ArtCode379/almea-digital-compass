package almeagroup.technology.aldigitalcompass.ui.composable.screen.home

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
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.AutoGraph
import androidx.compose.material.icons.rounded.CloudQueue
import androidx.compose.material.icons.rounded.DataUsage
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import almeagroup.technology.aldigitalcompass.data.model.ServiceModel
import almeagroup.technology.aldigitalcompass.ui.composable.shared.RBVRHContentWrapper
import almeagroup.technology.aldigitalcompass.ui.composable.shared.RBVRHEmptyView
import almeagroup.technology.aldigitalcompass.ui.state.DataUiState
import almeagroup.technology.aldigitalcompass.ui.theme.ChipBackground
import almeagroup.technology.aldigitalcompass.ui.theme.GradientEnd
import almeagroup.technology.aldigitalcompass.ui.theme.GradientStart
import almeagroup.technology.aldigitalcompass.ui.viewmodel.ServiceViewModel
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ServiceViewModel = koinViewModel(),
    onNavigateToServiceDetails: (serviceId: Int) -> Unit,
) {
    val servicesState by viewModel.servicesState.collectAsState()
    HomeContent(servicesState, modifier, onNavigateToServiceDetails)
}

@Composable
private fun HomeContent(
    servicesState: DataUiState<List<ServiceModel>>,
    modifier: Modifier = Modifier,
    onNavigateToServiceDetails: (serviceId: Int) -> Unit,
) {
    RBVRHContentWrapper(
        dataState = servicesState,
        modifier = modifier,
        dataPopulated = {
            ServicesPopulated((servicesState as DataUiState.Populated).data, onNavigateToServiceDetails = onNavigateToServiceDetails)
        },
        dataEmpty = {
            RBVRHEmptyView(modifier = Modifier.fillMaxSize(), primaryText = "No services available")
        },
    )
}

@Composable
private fun ServicesPopulated(
    services: List<ServiceModel>,
    modifier: Modifier = Modifier,
    onNavigateToServiceDetails: (serviceId: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Column(Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                Text("Navigate change with confidence", style = MaterialTheme.typography.headlineMedium)
                Text("Independent technology advice, built around your next decision.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        item {
            AvailabilityBanner(onClick = { onNavigateToServiceDetails(services.first().id) })
        }
        item {
            Text("Explore expertise", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 20.dp))
        }
        item {
            CategoryRow()
        }
        item {
            Text("Recommended for you", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 20.dp))
        }
        items(services, key = { it.id }) { service ->
            ServiceCard(service, onClick = { onNavigateToServiceDetails(service.id) })
        }
        item {
            Text("Selected work", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp))
        }
        item {
            PortfolioRow()
        }
        item {
            Column(Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
                Text("Knowledge base", style = MaterialTheme.typography.titleLarge)
                Text("Perspectives for technology leaders", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        item {
            KnowledgeCard("Responsible AI beyond the pilot", "A governance-first path from promising use case to dependable business capability.", "6 min read")
        }
        item {
            KnowledgeCard("What cloud value really looks like", "Move the migration conversation from infrastructure cost to measurable operating outcomes.", "5 min read")
        }
        item {
            KnowledgeCard("Cyber resilience is an operating model", "Why ownership, rehearsal, and recovery design matter as much as preventive controls.", "7 min read")
        }
    }
}

@Composable
private fun PortfolioRow() {
    val cases = listOf(
        Triple("Global cloud foundation", "40% faster environment delivery", "https://images.unsplash.com/photo-1451187580459-43490279c0fa?auto=format&fit=crop&w=900&q=85"),
        Triple("Secure digital workplace", "12 markets aligned", "https://images.unsplash.com/photo-1563013544-824ae1b704d3?auto=format&fit=crop&w=900&q=85"),
        Triple("Executive data platform", "One trusted KPI layer", "https://images.unsplash.com/photo-1551288049-bebda4e38f71?auto=format&fit=crop&w=900&q=85"),
    )
    LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(cases) { case ->
            Card(modifier = Modifier.width(250.dp), shape = RoundedCornerShape(18.dp)) {
                AsyncImage(case.third, case.first, Modifier.fillMaxWidth().height(128.dp), contentScale = ContentScale.Crop)
                Column(Modifier.padding(14.dp)) {
                    Text(case.first, style = MaterialTheme.typography.titleMedium)
                    Text(case.second, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@Composable
private fun KnowledgeCard(title: String, summary: String, readingTime: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text("INSIGHT · $readingTime", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.labelMedium)
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(summary, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun AvailabilityBanner(onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(Brush.horizontalGradient(listOf(GradientStart, GradientEnd))).padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text("NEXT AVAILABLE", color = Color.White.copy(alpha = 0.72f), style = MaterialTheme.typography.labelMedium)
                Text("Today · 1:30 PM", color = Color.White, style = MaterialTheme.typography.titleLarge)
                Text("Digital Strategy Audit", color = Color.White.copy(alpha = 0.82f), style = MaterialTheme.typography.bodyMedium)
            }
            Icon(Icons.Rounded.ArrowForward, null, tint = Color.White)
        }
    }
}

@Composable
private fun CategoryRow() {
    val categories = listOf(
        "Strategy" to Icons.Rounded.AutoGraph,
        "Security" to Icons.Rounded.Security,
        "Cloud" to Icons.Rounded.CloudQueue,
        "Data" to Icons.Rounded.DataUsage,
    )
    LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(categories) { category ->
            CategoryCard(category.first, category.second)
        }
    }
}

@Composable
private fun CategoryCard(label: String, icon: ImageVector) {
    Card(colors = CardDefaults.cardColors(containerColor = ChipBackground), shape = RoundedCornerShape(16.dp)) {
        Column(
            modifier = Modifier.width(104.dp).padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(icon, null, Modifier.size(30.dp), MaterialTheme.colorScheme.secondary)
            Text(label, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun ServiceCard(service: ServiceModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = service.imageUrl,
                contentDescription = service.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(112.dp).clip(RoundedCornerShape(14.dp)),
            )
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(service.category.uppercase(), color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.labelMedium)
                Text(service.name, style = MaterialTheme.typography.titleMedium)
                Text(service.description, maxLines = 2, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("From £${service.price.toInt()}", style = MaterialTheme.typography.labelLarge)
                    Text("Book Now", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}
