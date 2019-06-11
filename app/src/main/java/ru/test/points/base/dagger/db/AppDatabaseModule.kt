package ru.test.points.base.dagger.db

import android.content.Context
import dagger.Module
import androidx.room.Room
import androidx.room.migration.Migration
import dagger.Provides
import javax.inject.Singleton


@Module
class AppDatabaseModule {

    @Singleton
    @Provides
    fun providesAppDb(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "app-db")
            .fallbackToDestructiveMigration()
            .build()

}