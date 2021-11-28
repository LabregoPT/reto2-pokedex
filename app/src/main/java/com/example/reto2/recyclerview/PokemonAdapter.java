package com.example.reto2.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonView> {


    private ArrayList<PokemonModel> pokemones;

    public PokemonAdapter() {
        pokemones = new ArrayList<>();
    }

    public void addPokemon(PokemonModel pokemonModel) {
        pokemones.add(pokemonModel);
        notifyItemInserted(pokemones.size() - 1);
    }

    @NonNull
    @Override
    public PokemonView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.pokemonrow, parent, false);
        PokemonView skeleton = new PokemonView(row);
        return skeleton;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonView skeleton, int position) {
        PokemonModel pokemon = pokemones.get(position);
        skeleton.setPokemonModel(pokemon);
        skeleton.getPokemonName().setText(pokemon.getName());
        Picasso.get().load(pokemon.getImagen()).into(skeleton.getPokemonImage());
    }

    @Override
    public int getItemCount() {
        return pokemones.size();
    }

    public void clear(){
        int size = pokemones.size();
        if(size > 0){
            for(int i =0; i<size; i++){
                pokemones.remove(0);
            }
            notifyDataSetChanged();
        }
    }
}
