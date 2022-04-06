package com.example.pokemonapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pokemonapp.databinding.ActivitySeleccionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeleccionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bDescarga.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val response = ObtenerPokemonRequest.get()
                response.imprimirPokemons()
            }
        }
    }

}