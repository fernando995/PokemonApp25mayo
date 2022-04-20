package com.example.pokemonapp

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvPokemon.layoutManager = LinearLayoutManager(this)
        binding.rvPokemon.adapter = AdapterPokemon()

        binding.bDescarga.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val listaPokemon = ObtenerPokemonRequest.get()
                withContext(Dispatchers.Main) {
                    (binding.rvPokemon.adapter as AdapterPokemon).pokemons = listaPokemon
                    binding.rvPokemon.adapter?.notifyDataSetChanged()
                    listaPokemon.imprimirPokemons()
                }
            }
        }
    }

}