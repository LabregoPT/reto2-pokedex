package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reto2.model.Entrenador;
import com.example.reto2.modelpokemonjson.PokeApi;
import com.example.reto2.recyclerview.PokemonAdapter;
import com.example.reto2.recyclerview.PokemonModel;
import com.example.reto2.util.Constantes;
import com.example.reto2.util.HTTPSWebUtilDomi;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;

import java.util.Locale;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button atraparBtn;
    private EditText pokemonET;
    private RecyclerView pokemonRecycler;
    private LinearLayoutManager manager;
    private PokemonAdapter adapter;
    private Entrenador entrenador;
    private TextView listPokemonTV;
    private EditText buscarPokemonET;
    private Button buscarPokmonBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buscarPokemonET = findViewById(R.id.buscarPokemonET);
        atraparBtn = findViewById(R.id.atraparBtn);
        buscarPokmonBtn = findViewById(R.id.buscarPokmonBtn);
        listPokemonTV =findViewById(R.id.listoPokemonTV);
        pokemonET = findViewById(R.id.pokemonET);
        pokemonRecycler=findViewById(R.id.pokemonRecycler);
        manager = new LinearLayoutManager(this);
        pokemonRecycler.setLayoutManager(manager);
        adapter = new PokemonAdapter();
        pokemonRecycler.setAdapter(adapter);
        pokemonRecycler.setHasFixedSize(true);
        entrenador = (Entrenador) getIntent().getExtras().get("entrenador");

        atraparBtn.setOnClickListener(this);
        buscarPokmonBtn.setOnClickListener(this);
        cargarPokemones();
        listPokemonTV.setText("Lista de Pokémon de "+ entrenador.getName());
    }

    public void cargarPokemones(){
        FirebaseFirestore.getInstance().collection("entrenadores").document(entrenador.getId()).collection("pokemones").get().addOnCompleteListener(
                task -> {
                    for (DocumentSnapshot doc:task.getResult()){
                        PokemonModel pokemonModel = doc.toObject(PokemonModel.class);
                        pokemonModel.setEntrenador(entrenador);
                        adapter.addPokemon(pokemonModel);
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.atraparBtn:
                HTTPSWebUtilDomi https = new HTTPSWebUtilDomi();

                new Thread(
                        ()->{
                            String pokeName = pokemonET.getText().toString().toLowerCase(Locale.ROOT);
                            String response= https.GETrequest(Constantes.BASE_URL+pokeName);
                            Gson gson = new Gson();
                            PokeApi pokeApiResult = gson.fromJson(response, PokeApi.class);
                            if(pokeApiResult == null){
                                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "No se pudo encontrar un Pokémon de nombre "+pokeName, Toast.LENGTH_SHORT).show());
                            }else{

                                PokemonModel pokemonModel = new PokemonModel();
                                String name = pokeApiResult.getName();
                                pokemonModel.setName(name.substring(0,1).toUpperCase()+name.substring(1));
                                pokemonModel.setId(UUID.randomUUID().toString());

                                pokemonModel.setAtaque(""+pokeApiResult.getStats()[0].getBase_stat());
                                pokemonModel.setDefensa(""+pokeApiResult.getStats()[1].getBase_stat());
                                pokemonModel.setVelocidad(""+pokeApiResult.getStats()[2].getBase_stat());
                                pokemonModel.setVida(""+pokeApiResult.getStats()[3].getBase_stat());
                                pokemonModel.setEntrenador(entrenador);
                                String type = pokeApiResult.getTypes()[0].getType().getName();
                                pokemonModel.setTipo(type.substring(0,1).toUpperCase()+type.substring(1));
                                pokemonModel.setImagen(pokeApiResult.getSprites().getFront_default());

                                Query query = FirebaseFirestore.getInstance().collection("entrenadores").
                                        document(entrenador.getId()).collection("pokemones").whereEqualTo("name", pokemonModel.getName());
                                query.get().addOnCompleteListener(
                                        task -> {
                                            //Aquí es donde recibimos los datos pero en un tiempo distinto por que el callback puede tardar en responder la query

                                            //AQUÍ SI EL USUARIO NO TIENE EL POKEMON ENTONCES LO AGREGA
                                            if (task.getResult().size() == 0) {
                                                adapter.addPokemon(pokemonModel);
                                                FirebaseFirestore.getInstance().collection("entrenadores").
                                                        document(entrenador.getId()).collection("pokemones").
                                                        document(pokemonModel.getId()).set(pokemonModel);

                                            } else {//SI EL USUARIO YA TIENE EL POKEMON ENTONCES NO LO AGREGA
                                                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Ya tienes este Pokemon", Toast.LENGTH_SHORT).show());
                                            }
                                        }
                                );
                            }
                        }
                ).start();
                break;
            case R.id.buscarPokmonBtn:
                String search = buscarPokemonET.getText().toString();
                if(search.equals("")){ //Si la búsqueda está vacía, pintar toda la lista
                    adapter.clear();
                    cargarPokemones();
                }else{
                    //Si se introdujeron términos de búsqueda, pintar los resultados
                    search = search.substring(0,1).toUpperCase()+search.substring(1);
                    Query query = FirebaseFirestore.getInstance().collection("entrenadores").
                            document(entrenador.getId()).collection("pokemones").
                            whereEqualTo("name", search);

                    query.get().addOnCompleteListener(
                            task -> {
                                if (task.getResult().size() == 0) {

                                    //Si no hay resultados, notificarlo al usuario.
                                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "No se encontraron resultados", Toast.LENGTH_SHORT).show());
                                }else{

                                    //Si hay resultados, pintar los datos filtrados.
                                    adapter.clear();
                                    for (DocumentSnapshot doc:task.getResult()){
                                        PokemonModel pokemonModel = doc.toObject(PokemonModel.class);
                                        pokemonModel.setEntrenador(entrenador);
                                        adapter.addPokemon(pokemonModel);
                                    }
                                }
                            }
                    );
                }
                break;

        }

    }


}