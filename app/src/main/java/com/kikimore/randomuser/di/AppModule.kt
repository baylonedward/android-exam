package com.kikimore.randomuser.di

import android.content.Context
import com.kikimore.randomuser.data.local.PersonDao
import com.kikimore.randomuser.data.local.RandomUserDatabase
import com.kikimore.randomuser.data.remote.RandomUserService
import com.kikimore.randomuser.data.repository.PersonRepository
import com.kikimore.randomuser.data.utils.LoggingInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by: ebaylon.
 * Created on: 15/11/2020.
 */
@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

  @Provides
  fun providMoshi() = Moshi.Builder().build()

  @Provides
  @Singleton
  fun provideOkHttpClient(loggingInterceptor: LoggingInterceptor) = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

  @Provides
  @Singleton
  fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient) = Retrofit.Builder()
    .baseUrl("https://randomuser.me")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(okHttpClient)
    .build()

  @Provides
  @Singleton
  fun provideRandomUserService(retrofit: Retrofit) = retrofit.create(RandomUserService::class.java)

  @Provides
  @Singleton
  fun provideRandomUserDatabase(@ApplicationContext context: Context) =
    RandomUserDatabase.getDatabase(context)

  @Provides
  @Singleton
  fun providePersonDao(db: RandomUserDatabase) = db.personDao()

  @Provides
  @Singleton
  fun providePersonRepository(
    remoteDataSource: RandomUserService,
    localDataSource: PersonDao
  ) = PersonRepository.getInstance(remoteDataSource, localDataSource)
}