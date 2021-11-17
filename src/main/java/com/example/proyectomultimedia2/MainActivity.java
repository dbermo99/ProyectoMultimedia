package com.example.proyectomultimedia2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button altaBtn, bajaBtn, buscarModificarBtn, mostrarTodosBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        altaBtn = (Button) findViewById(R.id.altaBtn);
        bajaBtn = (Button) findViewById(R.id.bajaBtn);
        buscarModificarBtn = (Button) findViewById(R.id.buscarModificarBtn);
        mostrarTodosBtn = (Button) findViewById(R.id.mostrarTodosBtn);

        altaBtn.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AltaActivity.class);
            startActivity(intent);
        });

        bajaBtn.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), BajaActivity.class);
            startActivity(intent);
        });

        buscarModificarBtn.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), BuscarModificarActivity.class);
            startActivity(intent);
        });

        mostrarTodosBtn.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MostrarTodosActivity.class);
            startActivity(intent);
        });

    }
}