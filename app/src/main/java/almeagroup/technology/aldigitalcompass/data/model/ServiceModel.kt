package almeagroup.technology.aldigitalcompass.data.model

import java.time.LocalTime

data class ServiceModel(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val availableTime: List<LocalTime>? = null,
    val imageUrl: String,
    val category: String,
    val durationMinutes: Int,
    val features: List<String>,
)
