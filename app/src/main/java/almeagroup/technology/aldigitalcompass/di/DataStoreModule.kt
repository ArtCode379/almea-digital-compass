package almeagroup.technology.aldigitalcompass.di

import almeagroup.technology.aldigitalcompass.data.datastore.RBVRHOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { RBVRHOnboardingPrefs(androidContext()) }
}