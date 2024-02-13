package com.example.composepokedex.data.repository

import com.example.composepokedex.data.Pokemon
import com.example.composepokedex.data.PokemonList
import com.example.composepokedex.remote.PokeApi
import com.example.composepokedex.util.Response
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val pokeApi: PokeApi
) {

    suspend fun getPokemonList(limit: Int, offset: Int): Response<PokemonList> {
        val response = try {
            Response.Success(pokeApi.getPokemonList(limit, offset))
        } catch (e: Exception) {
            Response.Failure(message = "An error occured")
        }
        return response
    }
    suspend fun getPokemonInfo(name:String): Response<Pokemon> {
        val response = try {
            Response.Success(pokeApi.getPokemonInfo(name))
        } catch (e: Exception) {
            Response.Failure(message = "An error occured")
        }
        return response
    }
}