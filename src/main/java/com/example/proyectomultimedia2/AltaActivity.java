package com.example.proyectomultimedia2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AltaActivity extends AppCompatActivity {

    Button inicioBtn;
    ListView listaMostrar;

    File f;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta);

        f = getDatabasePath("almacen.sqlite");
        db = SQLiteDatabase.openOrCreateDatabase(f.getPath(), null);
        db.execSQL("create table if not exists almacen(marca varchar2(15), modelo varchar2(15), color varchar(15), anioLanzamiento Integer(4),disponibilidad varchar(25), PRIMARY KEY(marca, modelo))");

        inicioBtn = (Button) findViewById(R.id.inicioBtn);
        listaMostrar = (ListView) findViewById(R.id.listaMostrar);

        inicioBtn.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    public void alta(View view) {

        TextView marcaText = (TextView) findViewById(R.id.marcaText);
        TextView modeloText = (TextView) findViewById(R.id.modeloText);
        TextView colorText = (TextView) findViewById(R.id.colorText);
        RadioButton opcion1 = (RadioButton) findViewById(R.id.primeroRb);
        RadioButton opcion2 = (RadioButton) findViewById(R.id.segundoRb);
        RadioButton opcion3 = (RadioButton) findViewById(R.id.terceroRb);
        CheckBox getafeCb = (CheckBox) findViewById(R.id.getafeCb);
        CheckBox parlaCb = (CheckBox) findViewById(R.id.parlaCb);
        CheckBox leganesCb = (CheckBox) findViewById(R.id.leganesCb);

        String marca = marcaText.getText().toString();
        String modelo = modeloText.getText().toString();
        String color = colorText.getText().toString();
        String anioLanzamiento = "";
        if(opcion1.isChecked()) {
            anioLanzamiento = "2000-2010";
        } else if(opcion2.isChecked()) {
            anioLanzamiento = "2011-2020";
        } else if(opcion3.isChecked()) {
            anioLanzamiento = ">2020";
        }
        String disponibilidad = "";
        if(getafeCb.isChecked())
            disponibilidad += "Getafe ";
        if(parlaCb.isChecked())
            disponibilidad += "Parla ";
        if(leganesCb.isChecked())
            disponibilidad += "Leganes ";

        //GUARDAMOS EN LA BBDD
        db.execSQL("insert into almacen values ('"+marca+"','"+modelo+"','"+color+"', '"+anioLanzamiento+"', '"+disponibilidad+"')");

        //GUARDAMOS EN UN TXT
        try
        {
            OutputStreamWriter fout= new OutputStreamWriter(openFileOutput("almacen.txt", Context.MODE_APPEND));
            fout.write(marca+";"+modelo+";"+color+";"+anioLanzamiento+";"+disponibilidad+"\n");
            fout.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error al crear el TXT", Toast.LENGTH_SHORT).show();
        }

        //MOSTRAMOS EN EL LISTVIEW
        ArrayList<String> datos = new ArrayList<>();
        datos.add("Marca: "+marca);
        datos.add("Modelo: "+modelo);
        datos.add("Color: "+color);
        datos.add("Año de lanzamiento: "+anioLanzamiento);
        datos.add("Disponibilidad"+disponibilidad);
        ArrayAdapter adaptador1 = new ArrayAdapter(this,android.R.layout.simple_list_item_1,datos);
        listaMostrar.setAdapter(adaptador1);
    }

}