package com.example.composepokedex.pokemondetail

import androidx.lifecycle.ViewModel
import com.example.composepokedex.data.Pokemon
import com.example.composepokedex.data.repository.PokemonRepository
import com.example.composepokedex.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    suspend fun getPokemonInfo(pokemonName: String): Response<Pokemon> {
        return pokemonRepository.getPokemonInfo(pokemonName)
    }
}