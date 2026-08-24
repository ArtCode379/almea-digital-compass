package almeagroup.technology.aldigitalcompass.di

import androidx.room.Room
import almeagroup.technology.aldigitalcompass.data.database.RBVRHDatabase
import org.koin.dsl.module

private const val DB_NAME = "rbvrh_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = RBVRHDatabase::class.java,
        name = DB_NAME
        ).build()
    }

    single { get<RBVRHDatabase>().bookingDao()}

}