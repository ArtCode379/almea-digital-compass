package almeagroup.technology.aldigitalcompass.data.repository

import almeagroup.technology.aldigitalcompass.data.datastore.RBVRHOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RBVRHOnboardingRepo(
    private val rbvrhOnboardingStoreManager: RBVRHOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return rbvrhOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            rbvrhOnboardingStoreManager.setOnboardedState(state)
        }
    }
}