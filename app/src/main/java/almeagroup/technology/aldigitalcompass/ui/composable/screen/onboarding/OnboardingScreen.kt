package almeagroup.technology.aldigitalcompass.ui.composable.screen.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoGraph
import androidx.compose.material.icons.rounded.CloudQueue
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import almeagroup.technology.aldigitalcompass.ui.theme.ChipBackground
import almeagroup.technology.aldigitalcompass.ui.viewmodel.RBVRHOnboardingVM
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private data class OnboardingPage(val title: String, val description: String, val icon: ImageVector)

private val onboardingPages = listOf(
    OnboardingPage("See your digital landscape clearly", "Explore focused advisory services that turn complex technology choices into an actionable roadmap.", Icons.Rounded.AutoGraph),
    OnboardingPage("Strengthen every critical layer", "Assess cyber risk, cloud readiness, data foundations, and operations with experienced technology consultants.", Icons.Rounded.Security),
    OnboardingPage("Move from insight to momentum", "Book an initial consultation, choose a time, and leave with a practical next step for your organization.", Icons.Rounded.CloudQueue),
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: RBVRHOnboardingVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
) {
    val onboardingSetState by viewModel.onboardingSetState.collectAsState()

    LaunchedEffect(onboardingSetState) {
        if (onboardingSetState) {
            onNavigateToHomeScreen()
        }
    }

    OnboardingScreenContent(modifier = modifier, onOnboardingComplete = viewModel::setOnboarded)
}

@Composable
private fun OnboardingScreenContent(
    modifier: Modifier = Modifier,
    onOnboardingComplete: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { pageIndex ->
            val page = onboardingPages[pageIndex]
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier.size(128.dp).background(ChipBackground, RoundedCornerShape(32.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(page.icon, null, Modifier.size(66.dp), MaterialTheme.colorScheme.primary)
                }
                Spacer(Modifier.height(42.dp))
                Text(page.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(12.dp))
                Text(page.description, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyLarge)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 26.dp)) {
            onboardingPages.indices.forEach { index ->
                val color = if (index == pagerState.currentPage) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outline
                Box(Modifier.size(if (index == pagerState.currentPage) 10.dp else 8.dp).background(color, CircleShape))
            }
        }
        Button(
            onClick = {
                if (pagerState.currentPage == onboardingPages.lastIndex) {
                    onOnboardingComplete()
                } else {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                }
            },
            modifier = Modifier.fillMaxWidth().height(54.dp),
        ) {
            Text(if (pagerState.currentPage == onboardingPages.lastIndex) "Get Started" else "Continue", color = Color.White)
        }
    }
}
