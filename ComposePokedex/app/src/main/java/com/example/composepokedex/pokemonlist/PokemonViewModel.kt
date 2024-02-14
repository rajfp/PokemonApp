package com.example.composepokedex.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.composepokedex.Constants.PAGE_SIZE
import com.example.composepokedex.data.models.PokemonItem
import com.example.composepokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(val repository: PokemonRepository) : ViewModel() {

    var currPage = 0
    var loading = mutableStateOf(false)
    var endReached = mutableStateOf(false)
    var error = mutableStateOf("")
    var pokemonList = mutableStateOf<List<PokemonItem>>(listOf())

    init {
        loadPaginatedPokemonList()
    }

    fun loadPaginatedPokemonList() {
        viewModelScope.launch {
            val result = repository.getPokemonList(PAGE_SIZE, currPage * 20)
            when (result) {
                is com.example.composepokedex.util.Response.Success -> {
                    endReached.value = currPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokemonItem(entry.name.capitalize(Locale.ROOT), url, number.toInt())
                    }
                    currPage++
                    error.value = ""
                    loading.value = false
                    pokemonList.value += pokedexEntries
                }

                is com.example.composepokedex.util.Response.Failure -> {
                    loading.value = false
                    error.value = result.message!!
                }
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate {
            it?.dominantSwatch?.rgb.let { color ->
                onFinish(Color(color!!))
            }
        }
    }
}