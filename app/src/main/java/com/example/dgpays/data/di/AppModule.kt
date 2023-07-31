package com.example.dgpays.data.di

import android.content.Context
import androidx.room.Room
import com.example.dgpays.data.IyzicoApi
import com.example.dgpays.data.database.AppDatabase
import com.example.dgpays.main.repositories.PaymentRepositoryImpl
import com.example.dgpays.main.repositories.PaymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideIyzicoApi() : IyzicoApi = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/api")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(IyzicoApi::class.java)

    @Provides
    @Singleton
    fun provideIyzicoRepository(api: IyzicoApi) : PaymentRepository = PaymentRepositoryImpl(api)

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

}

