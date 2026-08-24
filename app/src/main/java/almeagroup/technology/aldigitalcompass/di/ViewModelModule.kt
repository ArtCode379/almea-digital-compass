package almeagroup.technology.aldigitalcompass.di

import almeagroup.technology.aldigitalcompass.ui.viewmodel.BookingViewModel
import almeagroup.technology.aldigitalcompass.ui.viewmodel.CheckoutViewModel
import almeagroup.technology.aldigitalcompass.ui.viewmodel.RBVRHOnboardingVM
import almeagroup.technology.aldigitalcompass.ui.viewmodel.ServiceDetailsViewModel
import almeagroup.technology.aldigitalcompass.ui.viewmodel.ServiceViewModel
import almeagroup.technology.aldigitalcompass.ui.viewmodel.RBVRHSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        RBVRHSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        RBVRHOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ServiceViewModel(
            serviceRepository = get()
        )
    }

    viewModel {
        ServiceDetailsViewModel(
            serviceRepository = get()
        )
    }

    viewModel {
        BookingViewModel(
            bookingRepository = get(),
            serviceRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            bookingRepository = get(),
        )
    }
}