package com.example.in2dm3_03.ejerciciosqlite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AdaptadorBD bd;
    private Button aniadir,mostrar,borrar,actualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bd = new AdaptadorBD(this);
        aniadir=(Button)findViewById(R.id.buttonAniadirContacto);
        mostrar=(Button)findViewById(R.id.buttonMostrar);
        borrar=(Button)findViewById(R.id.buttonBorrar);
        actualizar=(Button)findViewById(R.id.buttonActualizar);
    }

    public void onclick(View v){
        Button boton = (Button) v;

        if(boton.getId()==R.id.buttonAniadirContacto){
            crear();
            Toast.makeText(getApplicationContext(),"Creado",Toast.LENGTH_LONG);
        }else if(boton.getId()==R.id.buttonMostrar){
            bd.abrir();
            Cursor c = bd.obtenerTodosLosContactos();
            if (c.moveToFirst())
            {
                do {
                    MostrarContacto(c);
                } while (c.moveToNext());
            }
            bd.cerrar();
        }else if(boton.getId()==R.id.buttonBorrar){
            borrar();
        }else if(boton.getId()==R.id.buttonActualizar){
            actualizar();
        }
    }

    public void crear() {
        String nombre="Unai";
        String mail="unai@gmail.com";
        //abrir BD
        bd.abrir();
        //---añadir un contacto--
        long id = bd.insertarContacto(nombre, mail);
        Toast.makeText(getApplicationContext(),"Contacto "+ nombre+ " Creado",Toast.LENGTH_SHORT).show();
        //añadir otro contacto
        //id = bd.insertarContacto("Mikel", "mikel@gmail.com");
        //cerrar base de datos
        bd.cerrar();
    }

    public void MostrarContacto(Cursor c)
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "Nombre: " + c.getString(1) + "\n" +
                        "Email:  " + c.getString(2),
                Toast.LENGTH_LONG).show();
    }


    public void borrar(){
        //---borrar un contacto---
        bd.abrir();
        if (bd.borrarContacto(7))
            Toast.makeText(this, "Borrado realizado.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Borrado fallido.", Toast.LENGTH_LONG).show();
        bd.cerrar();
    }

    public void actualizar(){
        //---actualizar un contacto---
        bd.abrir();
        if (bd.actualizarContacto(1, "Maria", "maria@yahoo.es"))
            Toast.makeText(this, "Actualización realizada .", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Actualización fallida.", Toast.LENGTH_LONG).show();
        bd.cerrar();
    }
}
