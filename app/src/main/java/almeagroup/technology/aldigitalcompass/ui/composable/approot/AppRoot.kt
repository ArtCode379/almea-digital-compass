package almeagroup.technology.aldigitalcompass.ui.composable.approot

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import almeagroup.technology.aldigitalcompass.R
import almeagroup.technology.aldigitalcompass.ui.composable.navigation.AppNavHost
import almeagroup.technology.aldigitalcompass.ui.composable.navigation.NavRoute
import kotlin.reflect.KClass

private val navigationItems: List<BottomNavItem> = listOf(
    BottomNavItem(
        titleRes = R.string.rbvrh_bottom_bar_nav_item_home_title,
        icon = Icons.Default.Home,
        route = NavRoute.Home
    ),
    BottomNavItem(
        titleRes = R.string.rbvrh_bottom_bar_nav_item_bookings_title,
        icon = Icons.Default.Book,
        route = NavRoute.Bookings
    ),
    BottomNavItem(
        titleRes = R.string.rbvrh_bottom_bar_nav_item_settings_title,
        icon = Icons.Default.Settings,
        route = NavRoute.Settings
    ),
)

private val topBarHiddenScreens: List<KClass<out NavRoute>> = listOf(
    NavRoute.Splash::class,
    NavRoute.Onboarding::class,
    //[COMMON][TopPrepRouteClass]
    //[COMMON][TopDetailsRouteClass]
)

private val bottomBarHiddenScreens: List<KClass<out NavRoute>> = listOf(
    NavRoute.Splash::class,
    NavRoute.Onboarding::class,
    NavRoute.ServiceDetails::class,
    NavRoute.Checkout::class,
    //[COMMON][BottomPrepRouteClass]
    //[COMMON][BottomDetailsRouteClass]
)

@Composable
fun AppRoot() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val shouldShowBottomBar = !currentDestination.matchesAnyRoute(bottomBarHiddenScreens)
    val shouldShowTopBar = !currentDestination.matchesAnyRoute(topBarHiddenScreens)

    val onNavigateToRoute = { item: BottomNavItem ->
        navController.navigate(item.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    AppRootContent(
        navController = navController,
        currentDestination = currentDestination,
        shouldShowTopBar = shouldShowTopBar,
        shouldShowBottomBar = shouldShowBottomBar,
        onNavigateToRoute = onNavigateToRoute,
        onNavigateBack = { navController.popBackStack() }
    )
}

@Composable
private fun AppRootContent(
    navController: NavHostController,
    currentDestination: NavDestination?,
    shouldShowTopBar: Boolean,
    shouldShowBottomBar: Boolean,
    onNavigateToRoute: (BottomNavItem) -> Unit,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            if (shouldShowTopBar) {
                AppTopBar(
                    currentDestination = currentDestination,
                    onNavigateBack = onNavigateBack,
                )
            }
        },

        bottomBar = {
            if (shouldShowBottomBar) {
                AppBottomBar(
                    currentDestination = currentDestination,
                    navigationItems = navigationItems,
                    onNavigateToRoute = onNavigateToRoute,
                )
            }
        },

        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        AppNavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        )
    }
}

fun NavDestination?.matchesAnyRoute(routes: List<KClass<out NavRoute>>): Boolean {
    return this?.let { destination ->
        routes.any { route -> destination.hasRoute(route) }
    } == true
}