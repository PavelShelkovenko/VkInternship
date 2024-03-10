package com.shelkovenko.vkinternship.di

import android.app.Application
import androidx.room.Room
import com.shelkovenko.vkinternship.data.ProductsRepositoryImpl
import com.shelkovenko.vkinternship.data.local.ProductDao
import com.shelkovenko.vkinternship.data.local.ProductDatabase
import com.shelkovenko.vkinternship.data.remote.ApiService
import com.shelkovenko.vkinternship.domain.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesApiService(): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesDatabase(application: Application): ProductDatabase {
        return Room.databaseBuilder(
            application,
            ProductDatabase::class.java,
            ProductDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideProductDao(db: ProductDatabase): ProductDao {
        return db.productDao()
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        apiService: ApiService,
        productDao: ProductDao
    ): ProductsRepository {
        return ProductsRepositoryImpl(
            apiService,
            productDao
        )
    }
}
