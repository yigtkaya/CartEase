package com.example.dgpays.data.di

import android.content.Context
import androidx.room.Room
import com.example.dgpays.data.IyzicoApi
import com.example.dgpays.data.database.AppDatabase
import com.example.dgpays.main.repositories.PaymentRepositoryImpl
import com.example.dgpays.main.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideIyzicoApi() : IyzicoApi = Retrofit.Builder()
        .baseUrl("https://dgpays-case.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(IyzicoApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) : AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "AppDatabase").build()
    }

    @Provides
    @Singleton
    fun provideMarketItemDao(database: AppDatabase) = database.marketItemDao()

    @Provides
    @Singleton
    fun provideBasketDao(database: AppDatabase) = database.basketDao()

    @Provides
    @Singleton
    fun provideOrderDao(database: AppDatabase) = database.orderDao()


    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}

