package com.example.pokemonapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapp.databinding.ActivitySeleccionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeleccionBinding
    private lateinit var listaPokemon: ListaPokemon

    private val tagListaPokemon = "TAG_LISTA_POKEMON"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvPokemon.layoutManager = LinearLayoutManager(this)
        binding.rvPokemon.adapter = AdapterPokemon()

        readFromPreferences()

        actualizarAdapter(listaPokemon)

        initBotonDescarga()
    }

    private fun initBotonDescarga() {
        binding.bDescarga.text = if (listaPokemon.listaPokemon.isNullOrEmpty()) {
            getString(R.string.descargar_pokemons)
        } else {
            getString(R.string.recargar_pokemons)
        }

        binding.bDescarga.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                listaPokemon = ObtenerPokemonRequest.get()
                withContext(Dispatchers.Main) {
                    actualizarAdapter(listaPokemon)
                }
                writeInPreferences()


            }
        }
    }

    private fun actualizarAdapter(listaPokemon : ListaPokemon){
        (binding.rvPokemon.adapter as AdapterPokemon).actualizarLista(listaPokemon)
        listaPokemon.imprimirPokemons()
    }

    private fun writeInPreferences() {
        getPreferences(Context.MODE_PRIVATE).edit().apply {
            //println(this@MainActivity.listaPokemon.toJson())
            putString(tagListaPokemon, this@MainActivity.listaPokemon.toJson())
            apply()
        }
    }

    private fun readFromPreferences() {
        val pokemonsText = getPreferences(Context.MODE_PRIVATE).getString(tagListaPokemon, "")
        listaPokemon = if (pokemonsText.isNullOrBlank()){
            ListaPokemon()
        } else {
            ListaPokemon.fromJson(pokemonsText)
        }
    }

}