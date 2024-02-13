package com.example.composepokedex.di

import com.example.composepokedex.data.repository.PokemonRepository
import com.example.composepokedex.remote.PokeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePokemonRepo(
        api: PokeApi
    ) = PokemonRepository(api)

    @Provides
    @Singleton
    fun providePokemonApi(
    ): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://pokeapi.co/api/v2")
            .build()
            .create(PokeApi::class.java)
    }

}
