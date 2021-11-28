package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.reto2.model.Entrenador;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button ingresarBtn;
    private EditText userTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ingresarBtn = findViewById(R.id.ingresarBtn);
        userTV=findViewById(R.id.userTV);

        ingresarBtn.setOnClickListener(this::login);

    }


    public void login(View view){
        String userName = userTV.getText().toString();
        Entrenador entrenador = new Entrenador(UUID.randomUUID().toString(), userName);
        Query query = FirebaseFirestore.getInstance().collection("entrenadores").whereEqualTo("name", userName);
        query.get().addOnCompleteListener(
                task -> {
                    //Aquí es donde recibimos los datos pero en un tiempo distinto por que el callback puede tardar en responder la query

                    //AQUÍ SI EL USUARIO NO EXISTE ENTONCES LO CREAMOS E INICIAMOS SESION CON EL
                    if (task.getResult().size()==0){
                        FirebaseFirestore.getInstance().collection("entrenadores").document(entrenador.getId()).set(entrenador);
                        Intent intent = new Intent(this, HomeActivity.class);
                        intent.putExtra("entrenador",entrenador);
                        startActivity(intent);
                    }else {//SI EL USUARIO YA EXISTE ENTONCES SOLO INICIAMOS SESION
                        Entrenador entrenadorExisting = null;
                        for (DocumentSnapshot document: task.getResult()) {
                            entrenadorExisting = document.toObject(Entrenador.class);
                            break;
                        }
                        Intent intent = new Intent(this, HomeActivity.class);
                        intent.putExtra("entrenador",entrenadorExisting);
                        startActivity(intent);
                    }
                }
        );



    }
}