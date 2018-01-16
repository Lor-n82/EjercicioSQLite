package com.example.in2dm3_03.ejerciciosqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by in2dm3_03 on 16/01/2018.
 */

public class AdaptadorBD {

    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "nombre";
    static final String KEY_EMAIL = "email";
    static final String TAG = " AdaptadorBD";
    static final String NOMBRE_BASEDATOS = "MiBD";
    static final String TABLA_BASEDATOS= "contactos";
    static final int VERSION_BASEDATOS = 1;

    //sentencia SQL para crear la BD
    static final String CREAR_BASEDATOS =
            "create table contactos (_id integer primary key autoincrement, "
                    + "nombre text not null, email text not null);";
    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public  AdaptadorBD(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
        }

        //crear la BD si no existe
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(CREAR_BASEDATOS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //para actualizar la BD cuando cambia la versión de la BD
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Actualizando base de datos de  versión " + oldVersion + " a "
                    + newVersion + ", lo que destruirá todos los viejos datos");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }
    //---abrir la base de datos---
    public  AdaptadorBD abrir() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database---
    public void cerrar()
    {
        DBHelper.close();
    }
    //---insertar un contacto en la base de datos---
    public long insertarContacto(String nombre, String email)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, nombre);
        initialValues.put(KEY_EMAIL, email);
        return db.insert(TABLA_BASEDATOS, null, initialValues);
    }
    //---borrar un  contacto---
    public boolean borrarContacto(long rowId)
    {
        return db.delete(TABLA_BASEDATOS, KEY_ROWID + "=" + rowId, null) > 0;
    }
    //---devolver todos los contactos---
//usa la interface Cursor para devolver el resultado de las consultas
    public Cursor obtenerTodosLosContactos()
    {
        return db.query(TABLA_BASEDATOS, new String[] {KEY_ROWID, KEY_NAME,
                KEY_EMAIL}, null, null, null, null, null);
    }
    //---devolver un contacto---
    public Cursor obtenerContacto(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, TABLA_BASEDATOS, new String[] {KEY_ROWID,
                                KEY_NAME, KEY_EMAIL}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //---actualizar un contacto---
    public boolean actualizarContacto(long rowId, String name, String email)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_EMAIL, email);
        return db.update(TABLA_BASEDATOS, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}

