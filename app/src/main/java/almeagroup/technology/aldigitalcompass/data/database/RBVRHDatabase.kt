package almeagroup.technology.aldigitalcompass.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import almeagroup.technology.aldigitalcompass.data.dao.BookingDao
import almeagroup.technology.aldigitalcompass.data.database.converter.Converters
import almeagroup.technology.aldigitalcompass.data.entity.BookingEntity

@Database(
    entities = [BookingEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RBVRHDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao
}

