package com.example.proyectomultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class AltaActivity extends AppCompatActivity {


    File f;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta);

        f = getDatabasePath("almacen.sqlite");
        db = SQLiteDatabase.openOrCreateDatabase(f.getPath(), null);
        db.execSQL("create table if not exists almacen(marca varchar2(15), modelo varchar2(15), color varchar(15), anioLanzamiento Integer(4), PRIMARY KEY(marca, modelo))");

    }

    public void alta(View view) {

        TextView marcaText = (TextView) findViewById(R.id.marcaText);
        TextView modeloText = (TextView) findViewById(R.id.modeloText);
        TextView colorText = (TextView) findViewById(R.id.colorText);
        TextView anioLanzamientoText = (TextView) findViewById(R.id.anioLanzamientoText);

        String marca = marcaText.getText().toString();
        String modelo = modeloText.getText().toString();
        String color = colorText.getText().toString();
        String anioLanzamiento = anioLanzamientoText.getText().toString();

        //GUARDAMOS EN LA BBDD
        db.execSQL("insert into almacen values ('"+marca+"','"+modelo+"','"+color+"', '"+anioLanzamiento+"')");

    }

    public void guardarFichero(View vista) {

        TextView marcaText = (TextView) findViewById(R.id.marcaText);
        TextView modeloText = (TextView) findViewById(R.id.modeloText);
        TextView colorText = (TextView) findViewById(R.id.colorText);
        TextView anioLanzamientoText = (TextView) findViewById(R.id.anioLanzamientoText);

        String marca = marcaText.getText().toString();
        String modelo = modeloText.getText().toString();
        String color = colorText.getText().toString();
        String anioLanzamiento = anioLanzamientoText.getText().toString();

        try
        {

            BufferedWriter bw = new BufferedWriter(new FileWriter("almacen.txt"));

            bw.write(modelo+";"+marca+";"+color+";"+anioLanzamiento+"\n");

            bw.close();

            Toast.makeText(getApplicationContext(), "Funciona!!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            //e.printStackTrace();
        }

    }

}