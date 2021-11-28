package com.example.reto2.recyclerview;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto2.HomeActivity;
import com.example.reto2.PokemonDetallesActivity;
import com.example.reto2.R;

public class PokemonView extends RecyclerView.ViewHolder {

    private PokemonModel pokemonModel;

    private ImageView pokemonImage;
    private TextView pokemonName;
    private Button verPokemonBtn;

    public PokemonView(@NonNull View itemView) {
        super(itemView);


        pokemonImage = itemView.findViewById(R.id.pokemonImage);
        pokemonName= itemView.findViewById(R.id.pokemonName);
        verPokemonBtn =itemView.findViewById(R.id.verPokemonBtn);

        verPokemonBtn.setOnClickListener(this::verPokemon);
    }

    private void verPokemon(View view) {

        Intent intent = new Intent(view.getContext(), PokemonDetallesActivity.class);
        intent.putExtra("pokemonModel",pokemonModel);
        view.getContext().startActivity(intent);
    }

    public void setPokemonModel(PokemonModel pokemonModel){
        this.pokemonModel=pokemonModel;
    }
    public ImageView getPokemonImage() {
        return pokemonImage;
    }

    public void setPokemonImage(ImageView pokemonImage) {
        this.pokemonImage = pokemonImage;
    }

    public TextView getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(TextView pokemonName) {
        this.pokemonName = pokemonName;
    }
}
