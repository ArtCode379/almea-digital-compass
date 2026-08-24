package almeagroup.technology.aldigitalcompass.ui.composable.navigation

//[COMMON][import_fillMaxSize]
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
//[COMMON][import_DetailsScreen]
//[COMMON][import_PrepScreen]
import almeagroup.technology.aldigitalcompass.ui.composable.screen.bookings.BookingsScreen
import almeagroup.technology.aldigitalcompass.ui.composable.screen.checkout.CheckoutScreen
import almeagroup.technology.aldigitalcompass.ui.composable.screen.onboarding.OnboardingScreen
import almeagroup.technology.aldigitalcompass.ui.composable.screen.home.HomeScreen
import almeagroup.technology.aldigitalcompass.ui.composable.screen.servicedetails.ServiceDetailsScreen
import almeagroup.technology.aldigitalcompass.ui.composable.screen.settings.SettingsScreen
import almeagroup.technology.aldigitalcompass.ui.composable.screen.splash.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Splash,
        modifier = modifier,
    ) {
        //[COMMON][PrepComposable]

        //[COMMON][DetailsComposable]

        composable<NavRoute.Splash> {
            SplashScreen(
                onNavigateToHomeScreen = {
                    navController.navigate(route = NavRoute.Home) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateToOnboarding = {
                    navController.navigate(route = NavRoute.Onboarding) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<NavRoute.Onboarding> {
            OnboardingScreen(
                onNavigateToHomeScreen = {
                    navController.navigate(route = NavRoute.Home) {
                        popUpTo(NavRoute.Onboarding) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<NavRoute.Home> {
            HomeScreen(
                onNavigateToServiceDetails = { serviceId: Int ->
                    navController.navigate(
                        route = NavRoute.ServiceDetails(serviceId = serviceId)
                    )
                }
            )
        }

        composable<NavRoute.ServiceDetails> { backStackEntry ->
            val serviceDetails: NavRoute.ServiceDetails = backStackEntry.toRoute()
            ServiceDetailsScreen(
                serviceId = serviceDetails.serviceId,
                onNavigateToCheckout = { serviceId: Int ->
                    navController.navigate(
                        route = NavRoute.Checkout(serviceId = serviceId)
                    )
                }
            )
        }

        composable<NavRoute.Checkout> { backStackEntry ->
            val checkout: NavRoute.Checkout = backStackEntry.toRoute()
            CheckoutScreen(
                serviceId = checkout.serviceId,
                onNavigateToBookingsScreen = {
                    navController.navigate(NavRoute.Bookings) {
                        popUpTo(NavRoute.Home) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<NavRoute.Bookings> {
            BookingsScreen()
        }

        composable<NavRoute.Settings> {
            SettingsScreen()
        }
    }
}