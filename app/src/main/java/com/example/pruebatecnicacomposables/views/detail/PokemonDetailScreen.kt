package com.example.pruebatecnicacomposables.views.detail

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.pruebatecnicacomposables.R
import com.example.pruebatecnicacomposables.views.detail.models.Pokemon
import com.example.pruebatecnicacomposables.views.detail.viewmodel.PokemonDetailViewModel
import com.example.pruebatecnicacomposables.views.main.CircularImage
import com.example.pruebatecnicacomposables.views.main.viewmodel.ListaPokemonViewModel
import com.example.pruebatecnicacomposables.views.ui.theme.PruebaTecnicaComposablesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailScreen : ComponentActivity() {
    private lateinit var pokemonDetailViewModel: PokemonDetailViewModel
    private var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonDetailViewModel = ViewModelProvider(this).get(PokemonDetailViewModel::class.java)
        id = intent.getIntExtra("id",0)
        setContent {
            PruebaTecnicaComposablesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val pokemon = pokemonDetailViewModel.pokemon.value
                    pokemon?.let {
                        PokemonDetailScreen(it)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        pokemonDetailViewModel.getDetailPokemon(this, id)
    }

}

@Composable
fun PokemonDetailScreen(
    pokemon: Pokemon
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        CircularImage(
            imageUrl = pokemon.sprites.front_default,
            text = pokemon.name,
            placeholder = painterResource(R.drawable.ic_launcher_background)
        )
        Text(text = "ID: ${pokemon.id}", fontSize = 20.sp)
        Text(text = "Name: ${pokemon.name}", fontSize = 20.sp)
        Text(text = "Height: ${pokemon.height}", fontSize = 20.sp)
        Text(text = "Weight: ${pokemon.weight}", fontSize = 20.sp)
        Text(text = "Type: ${pokemon.types.joinToString(", "){it.type.name}}", fontSize = 20.sp)
       FavoriteButton()
    }
}

@Composable
fun FavoriteButton() {
    var isFavorite by remember { mutableStateOf(false) }

    IconButton(onClick = {
        isFavorite = !isFavorite
    }) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = "Boton de favoritos",
            tint = if (isFavorite) androidx.compose.ui.graphics.Color.Red else androidx.compose.ui.graphics.Color.Gray
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PruebaTecnicaComposablesTheme {

    }
}