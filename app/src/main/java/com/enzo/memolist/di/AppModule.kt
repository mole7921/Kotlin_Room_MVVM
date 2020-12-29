package com.enzo.memolist.di

import android.app.Application
import androidx.room.Room
import com.enzo.memolist.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: TaskDatabase.Callback
    ) = Room.databaseBuilder(app, TaskDatabase::class.java, "task_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

//    Room啟動時將檢測version是否發生增加，如果有，
//    那麼將找到Migration去執行特定的操作。
//    如果沒有因為 fallbackToDestructiveMigration()，
//    將會刪除資料庫並重建。

//    踩坑資料：https://kknews.cc/zh-tw/code/zpbgyqg.html


    @Provides
    fun provideTaskDao(Database: TaskDatabase) = Database.taskDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope
//區分不同實體