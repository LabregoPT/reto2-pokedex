package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reto2.recyclerview.PokemonModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class PokemonDetallesActivity extends AppCompatActivity {

    private TextView ataque;
    private TextView defensa;
    private TextView vida;
    private TextView velocidad;
    private TextView nombre;
    private TextView tipo;
    private PokemonModel pokemonModel;
    private Button soltarBtn;
    private ImageView pokemonIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detalles);

        pokemonIV = findViewById(R.id.pokemonIV);
        ataque = findViewById(R.id.PokeAtaqueTV);
        defensa = findViewById(R.id.PokeDefensaTV);
        vida = findViewById(R.id.PokeHpTV);
        velocidad = findViewById(R.id.PokeVelocidadTV);
        nombre = findViewById(R.id.PokeNombreTV);
        tipo = findViewById(R.id.PokeTipoTV);
        soltarBtn=findViewById(R.id.soltarBtn);

        pokemonModel = (PokemonModel) getIntent().getExtras().get("pokemonModel");

        ataque.setText(pokemonModel.getAtaque());
        defensa.setText(pokemonModel.getDefensa());
        vida.setText(pokemonModel.getVida());
        velocidad.setText(pokemonModel.getVelocidad());
        nombre.setText(pokemonModel.getName());
        tipo.setText("("+pokemonModel.getTipo()+")");
        Picasso.get().load(pokemonModel.getImagen()).into(pokemonIV);

        soltarBtn.setOnClickListener(this::borrarPokemon);
    }

    private void borrarPokemon(View view) {
        FirebaseFirestore.getInstance().collection("entrenadores").
                document(pokemonModel.getEntrenador().getId()).collection("pokemones").
                document(pokemonModel.getId()).delete();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("entrenador", pokemonModel.getEntrenador());
        startActivity(intent);
    }
}