package com.example.in2dm3_03.ejerciciosqlite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AdaptadorBD bd;
    private Button aniadir,mostrar,borrar,actualizar;
    private TextView nombre, mail, eliminar, actuId, actuNom, actuMail;
    private String n,m,e,id;
    private int eli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bd = new AdaptadorBD(this);
        aniadir=(Button)findViewById(R.id.buttonAniadirContacto);
        mostrar=(Button)findViewById(R.id.buttonMostrar);
        borrar=(Button)findViewById(R.id.buttonBorrar);
        actualizar=(Button)findViewById(R.id.buttonActualizar);

        nombre=(TextView)findViewById(R.id.editTextNombre);
        mail=(TextView)findViewById(R.id.editTextMail);
        eliminar=(TextView)findViewById(R.id.editTextEliminar);

        actuId=(TextView)findViewById(R.id.editTextActualizarId);
        actuNom=(TextView)findViewById(R.id.editTextActualizarNombre);
        actuMail=(TextView)findViewById(R.id.editTextActualizarMail);
    }

    public void onclick(View v){
        Button boton = (Button) v;

        if(boton.getId()==R.id.buttonAniadirContacto){
            n=nombre.getText().toString();
            m=mail.getText().toString();
            crear(n,m);
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
            e=eliminar.getText().toString();
            borrar(e);
        }else if(boton.getId()==R.id.buttonActualizar){
            id=actuId.getText().toString();
            n=actuNom.getText().toString();
            m=actuMail.getText().toString();
            actualizar(id,n,m);
        }
    }

    public void crear(String n,String m) {
        //String nombre="Unai";
        //String mail="unai@gmail.com";
        //abrir BD
        bd.abrir();
        //---añadir un contacto--
        long id = bd.insertarContacto(n, m);
        Toast.makeText(getApplicationContext(),"Contacto "+ n + " Creado",Toast.LENGTH_SHORT).show();
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


    public void borrar(String e){
        //---borrar un contacto---
        eli=Integer.parseInt(e);
        bd.abrir();
        if (bd.borrarContacto(eli))
            Toast.makeText(this, "Borrado realizado.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Borrado fallido.", Toast.LENGTH_LONG).show();
        bd.cerrar();
    }

    public void actualizar(String id,String n,String m){
        eli=Integer.parseInt(id);

        //---actualizar un contacto---
        bd.abrir();
        if (bd.actualizarContacto(eli, n, m))
            Toast.makeText(this, "Actualización realizada .", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Actualización fallida.", Toast.LENGTH_LONG).show();
        bd.cerrar();
    }
}
