package com.example.proyectomultimedia2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class BuscarModificarActivity extends AppCompatActivity {

    Button buscarBtn, inicioBtn, modificarBtn;

    File f;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_modificar);

        buscarBtn = (Button) findViewById(R.id.buscarBtn);
        inicioBtn = (Button) findViewById(R.id.inicioBtn);
        modificarBtn = (Button) findViewById(R.id.modificarBtn);

        f = getDatabasePath("almacen.sqlite");
        db = SQLiteDatabase.openOrCreateDatabase(f.getPath(), null);
        db.execSQL("create table if not exists almacen(marca varchar2(15), modelo varchar2(15), color varchar(15), anioLanzamiento Integer(4),disponibilidad varchar(25), PRIMARY KEY(marca, modelo))");

        inicioBtn.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    public void buscar(View view) {
        TextView marcaTexto = (TextView) findViewById(R.id.marcaTexto);
        TextView modeloTexto = (TextView) findViewById(R.id.modeloTexto);
        TextView colorTexto = (TextView) findViewById(R.id.colorTexto);
        RadioButton opcion1 = (RadioButton) findViewById(R.id.opcion1Rb);
        RadioButton opcion2 = (RadioButton) findViewById(R.id.opcion2Rb);
        RadioButton opcion3 = (RadioButton) findViewById(R.id.opcion3Rb);
        CheckBox getafeCb = (CheckBox) findViewById(R.id.getafeCb);
        CheckBox parlaCb = (CheckBox) findViewById(R.id.parlaCb);
        CheckBox leganesCb = (CheckBox) findViewById(R.id.leganesCb);

        String marca = marcaTexto.getText().toString();
        String modelo = modeloTexto.getText().toString();

        Cursor cursor = db.rawQuery("SELECT * FROM almacen WHERE marca LIKE '"+marca+"' AND modelo LIKE '"+modelo+"'", null);

        while (cursor.moveToNext()) {
            //String marca2 = cursor.getString(0);
            //String modelo2 = cursor.getString(1);
            String color2 = cursor.getString(2);
            String anioLanzamiento2 = cursor.getString(3);
            String disponibilidad = cursor.getString(4);

            colorTexto.setText(color2);
            if(anioLanzamiento2.equalsIgnoreCase("2000-2010"))
                opcion1.setChecked(true);
            else if(anioLanzamiento2.equalsIgnoreCase("2011-2020"))
                opcion2.setChecked(true);
            else if(anioLanzamiento2.equalsIgnoreCase(">2020"))
                opcion3.setChecked(true);
            String locales[] = disponibilidad.split(" ");
            for(String s: locales) {
                if(s.trim().equalsIgnoreCase("getafe"))
                    getafeCb.setChecked(true);
                if(s.trim().equalsIgnoreCase("leganes"))
                    leganesCb.setChecked(true);
                if(s.trim().equalsIgnoreCase("parla"))
                    parlaCb.setChecked(true);
            }
        }
    }

    public void modificar(View view) {
        TextView marcaTexto = (TextView) findViewById(R.id.marcaTexto);
        TextView modeloTexto = (TextView) findViewById(R.id.modeloTexto);
        TextView colorTexto = (TextView) findViewById(R.id.colorTexto);
        RadioButton opcion1 = (RadioButton) findViewById(R.id.opcion1Rb);
        RadioButton opcion2 = (RadioButton) findViewById(R.id.opcion2Rb);
        RadioButton opcion3 = (RadioButton) findViewById(R.id.opcion3Rb);
        CheckBox getafeCb = (CheckBox) findViewById(R.id.getafeCb);
        CheckBox parlaCb = (CheckBox) findViewById(R.id.parlaCb);
        CheckBox leganesCb = (CheckBox) findViewById(R.id.leganesCb);

        String marca = marcaTexto.getText().toString();
        String modelo = modeloTexto.getText().toString();
        String color = colorTexto.getText().toString();
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

        //MODIFICAMOS LA BASE DE DATOS
        db.execSQL("update almacen set color = '"+color+"', anioLanzamiento = '"+anioLanzamiento+"', disponibilidad = '"+disponibilidad+"' where marca like '"+marca+"' and modelo like '"+modelo+"' ");

        //CON ESTO LEEMOS EL TXT Y LO MODIFICAMOS Y LO GUARDAMOS EN UN STRING
        String texto = "";
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("almacen.txt")));
            String linea = "";
            while((linea = fin.readLine()) != null) {
                if(linea.length()>0) {
                    String datos[] = linea.split(";");
                    datos[2] = color;
                    datos[3] = anioLanzamiento;
                    datos[4] = disponibilidad;
                    texto += datos[0]+";"+datos[1]+";"+datos[2]+";"+datos[3]+";"+disponibilidad + "\n";
                }
            }
            fin.close();
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }

        //ESTO ES PARA VOLVER A GUARDARLO
        try {
            OutputStreamWriter fout= new OutputStreamWriter(openFileOutput("almacen.txt", Context.MODE_PRIVATE));
            String linea = "";
            for(int i=0; i<texto.length(); i++) {
                if(texto.charAt(i) != '\n') {
                    linea += texto.charAt(i);
                } else {
                    String separado[] = linea.split(";");
                    String marca1 = separado[0];
                    String modelo1 = separado[1];
                    String color1 = separado[2];
                    String anioLanzamiento1 = separado[3];
                    String disponibilidad1 = separado[4];
                    fout.write(marca1+";"+modelo1+";"+color1+";"+anioLanzamiento1+";"+disponibilidad1+"\n");
                    linea = "";
                }
            }
            fout.close();
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }
    }

}