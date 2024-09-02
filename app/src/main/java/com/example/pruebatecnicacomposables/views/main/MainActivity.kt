package com.example.pruebatecnicacomposables.views.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pruebatecnicacomposables.ui.theme.PruebaTecnicaComposablesTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberImagePainter
import com.example.pruebatecnicacomposables.R
import com.example.pruebatecnicacomposables.views.main.models.Result
import com.example.pruebatecnicacomposables.utils.Constants
import com.example.pruebatecnicacomposables.views.detail.PokemonDetailScreen
import com.example.pruebatecnicacomposables.views.main.viewmodel.ListaPokemonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var viewModelPrueba: ListaPokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelPrueba = ViewModelProvider(this).get(ListaPokemonViewModel::class.java)
        setContent {
            PruebaTecnicaComposablesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    val list = viewModelPrueba.listPokemon.value
                    Greeting(list)
                }
            }
        }

    }
    override fun onResume() {
        super.onResume()
        viewModelPrueba.getListPokemon(this)
    }

}


@Composable
fun Greeting(listPokemon: List<Result>) {
    val context = LocalContext.current
    Column( modifier = Modifier
        .verticalScroll(rememberScrollState())){
        Text(text = "List Pokemon")
        for (item in listPokemon) {
            val segmentos = item.url.length
            val id: Int = item.url.substring(segmentos-2 , segmentos-1).toInt()
            Divider()
            Row {
                Spacer(modifier = Modifier.width(Dp(5.0F)))
                ////
                val padding = 16.dp
                Column(
                    Modifier
                        .padding(padding)
                        .fillMaxWidth()
                        .clickable(
                            true, "Ver detalles", null
                        ) {
                            goToDetail(context = context, id)
                        }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.width(Dp.Infinity)) {
                        val idImage = item.url.substring(segmentos-2 , segmentos-1)+ ".png"
                        CircularImage(imageUrl = "${Constants.BASE_IMAGE}$idImage", text = item.name, placeholder = painterResource(
                            id = R.drawable.ic_launcher_background
                        ) )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(item.name)
                        }
                    }
                }
            }
        }

    }
}

fun goToDetail(context: Context, id: Int){
    val intent = Intent(context, PokemonDetailScreen::class.java)
    intent.putExtra("id", id)
    context.startActivity(intent)
}

@Composable
fun CircularImage(
    imageUrl: String?,
    text: String?,
    modifier: Modifier = Modifier,
    backgroundColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Gray,
    textColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Black,
    placeholder: Painter
) {
    val initials = getInitials(text)
    val isInvalidText = initials.isEmpty() || initials[0].isDigit() || !initials[0].isLetter()

    if (imageUrl.isNullOrEmpty() || isInvalidText) {
        // Mostrar iniciales o placeholder si la URL está vacía o no es válida
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .size(64.dp)
                .background(backgroundColor, CircleShape)
        ) {
            if (initials.isNotEmpty() && !isInvalidText) {
                Text(
                    text = initials,
                    color = textColor,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Image(
                    painter = placeholder,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    } else {
        // Mostrar imagen si la URL es válida
        Image(painter = rememberImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .background(backgroundColor, CircleShape),
        )
    }
}
fun getInitials(text: String?): String {
    if (text.isNullOrBlank()) return ""
    val words = text.trim().split(" ")
    return when {
        words.size == 1 -> words[0].firstOrNull()?.toString()?.uppercase() ?: ""
        words.size > 1 -> "${words[0].firstOrNull()?.uppercase() ?: ""}${words[1].firstOrNull()?.uppercase() ?: ""}"
        else -> ""
    }
}


fun hola(peli: String){
    Log.e("", peli)
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PruebaTecnicaComposablesTheme {

    }
}
