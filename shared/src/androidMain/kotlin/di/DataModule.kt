package di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.ScoreboardApi
import data.ScoreboardApiImp
import data.UserScoreApi
import data.UserScoreApiImp
import data.remote.TeddyApiHttpClient
import data.repository.TeddyRepositoryImp
import domain.repository.TeddyRepository
import io.ktor.client.engine.okhttp.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    companion object {

        @Provides
        @Singleton
        fun provideHttpClient() = TeddyApiHttpClient(OkHttp)

    }

    @get:[Binds]
    val UserScoreApiImp.userScoreApi : UserScoreApi

    @get:[Binds]
    val ScoreboardApiImp.scoreboardApi : ScoreboardApi

    @get:[Binds]
    val TeddyRepositoryImp.teddyRepository : TeddyRepository


}