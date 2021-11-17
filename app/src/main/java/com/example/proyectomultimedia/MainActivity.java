package com.example.proyectomultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button altaBtn, bajaBtn, modificarBtn, buscarBtn, mostrarTodosBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        altaBtn = (Button) findViewById(R.id.altaBtn);
        bajaBtn = (Button) findViewById(R.id.bajaBtn);
        modificarBtn = (Button) findViewById(R.id.modificarBtn);
        buscarBtn = (Button) findViewById(R.id.buscarBtn);
        mostrarTodosBtn = (Button) findViewById(R.id.mostrarTodosBtn);

        altaBtn.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AltaActivity.class);
            startActivity(intent);
        });

        bajaBtn.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), BajaActivity.class);
            startActivity(intent);
        });

        modificarBtn.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ModificarActivity.class);
            startActivity(intent);
        });

        buscarBtn.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), BuscarActivity.class);
            startActivity(intent);
        });

        mostrarTodosBtn.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ModificarActivity.class);
            startActivity(intent);
        });

    }
}