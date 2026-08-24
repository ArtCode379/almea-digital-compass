package almeagroup.technology.aldigitalcompass.di

import almeagroup.technology.aldigitalcompass.data.repository.BookingRepository
import almeagroup.technology.aldigitalcompass.data.repository.RBVRHOnboardingRepo
import almeagroup.technology.aldigitalcompass.data.repository.ServiceRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        RBVRHOnboardingRepo(
            rbvrhOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ServiceRepository() }

    single{
        BookingRepository(
            bookingDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}