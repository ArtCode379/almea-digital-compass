package almeagroup.technology.aldigitalcompass.ui.composable.screen.servicedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import almeagroup.technology.aldigitalcompass.R
import almeagroup.technology.aldigitalcompass.data.model.ServiceModel
import almeagroup.technology.aldigitalcompass.ui.composable.shared.RBVRHContentWrapper
import almeagroup.technology.aldigitalcompass.ui.composable.shared.RBVRHEmptyView
import almeagroup.technology.aldigitalcompass.ui.state.DataUiState
import almeagroup.technology.aldigitalcompass.ui.viewmodel.ServiceDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServiceDetailsScreen(
    serviceId: Int,
    modifier: Modifier = Modifier,
    viewModel: ServiceDetailsViewModel = koinViewModel(),
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {
    val serviceState by viewModel.serviceState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeServiceById(serviceId)
    }

    ServiceDetailsContent(
        serviceState = serviceState,
        modifier = modifier,
        onNavigateToCheckout = onNavigateToCheckout
    )
}

@Composable
private fun ServiceDetailsContent(
    serviceState: DataUiState<ServiceModel>,
    modifier: Modifier = Modifier,
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {
    Column(modifier = modifier) {
        RBVRHContentWrapper<ServiceModel>(
            dataState = serviceState,

            dataPopulated = {
                ServicesDetailsPopulated(
                    service = (serviceState as DataUiState.Populated).data,
                    onNavigateToCheckout = onNavigateToCheckout,
                )
            },

            dataEmpty = {
                RBVRHEmptyView(
                    primaryText = stringResource(R.string.rbvrh_services_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun ServicesDetailsPopulated(
    service: ServiceModel,
    modifier: Modifier = Modifier,
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {

}