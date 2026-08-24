package almeagroup.technology.aldigitalcompass.ui.composable.screen.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    SettingsScreenContent(modifier)
}

@Composable
fun SettingsScreenContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val supportUrl = "https://almeagroup.study"
    val openSupport = {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(supportUrl)))
    }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall)
        Text("About", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.labelLarge)
        Card(shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
                SettingRow(Icons.Rounded.Business, "Company", "ALMEA GROUP LIMITED")
                SettingRow(Icons.Rounded.Language, "App version", "1.0.0")
            }
        }
        Text("Support", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.labelLarge)
        Card(shape = RoundedCornerShape(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Rounded.SupportAgent, null, Modifier.size(28.dp), MaterialTheme.colorScheme.secondary)
                Column(Modifier.weight(1f).padding(horizontal = 14.dp)) {
                    Text("Customer Support", style = MaterialTheme.typography.titleMedium)
                    Text(supportUrl, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Icon(Icons.Rounded.ChevronRight, null)
            }
        }
        Button(onClick = openSupport, modifier = Modifier.fillMaxWidth()) {
            Text("Visit Customer Support")
        }
        Text("Legal", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.labelLarge)
        Text("Privacy and service information are available on the company website.", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun SettingRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, Modifier.size(26.dp), MaterialTheme.colorScheme.secondary)
        Column(Modifier.padding(start = 14.dp)) {
            Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium)
            Text(value, style = MaterialTheme.typography.titleMedium)
        }
    }
}
