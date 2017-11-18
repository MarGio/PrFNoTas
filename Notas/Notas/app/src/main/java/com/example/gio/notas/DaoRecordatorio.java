package com.example.gio.notas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DaoRecordatorio {
    private  Context _contexto;
    private SQLiteDatabase _midb;

    public DaoRecordatorio(Context contexto){
        this._contexto = contexto;
        this._midb = new MiDBOpenHelper(contexto).getWritableDatabase();
    }


    public long insert(Recordatorio c){

        ContentValues cv = new ContentValues();

        cv.put(MiDBOpenHelper.COLUMNS_RECORDATORIO[1],c.getTitulo());
        cv.put(MiDBOpenHelper.COLUMNS_RECORDATORIO[2],c.getDescripcion());
        cv.put(MiDBOpenHelper.COLUMNS_RECORDATORIO[3],c.getFecha());
        cv.put(MiDBOpenHelper.COLUMNS_RECORDATORIO[4],c.getHora());

        return _midb.insert(MiDBOpenHelper.TABLE_RECORDATORIO_NAME,null,cv) ;

    }



    public long update(Recordatorio c){
        ContentValues cv = new ContentValues();

        cv.put(MiDBOpenHelper.COLUMNS_RECORDATORIO[1],c.getTitulo());
        cv.put(MiDBOpenHelper.COLUMNS_RECORDATORIO[2],c.getDescripcion());
        cv.put(MiDBOpenHelper.COLUMNS_RECORDATORIO[3],c.getFecha());
        cv.put(MiDBOpenHelper.COLUMNS_RECORDATORIO[4],c.getHora());

        return _midb.update(MiDBOpenHelper.TABLE_RECORDATORIO_NAME,
                cv,
                "_id=?",
                new String[] { String.valueOf( c.getId())});

    }

    public int delete(String id){
        return  _midb.delete("recordatorio","_id='"+id+"'",null);
    }



    public List<Recordatorio> Buscar(String nombre){
        List<Recordatorio> ls=null;

        Cursor c = _midb.query(MiDBOpenHelper.TABLE_RECORDATORIO_NAME,
                MiDBOpenHelper.COLUMNS_RECORDATORIO,
                null,
                null,
                null,
                null,
                MiDBOpenHelper.COLUMNS_RECORDATORIO[1]);

        if (c.moveToFirst()) {
            ls = new ArrayList<Recordatorio>();
            do {
                Recordatorio ctc = new Recordatorio();

                ctc.setId(
                        c.getInt(
                                c.getColumnIndex(
                                        MiDBOpenHelper.COLUMNS_RECORDATORIO[0])
                        )
                );

                ctc.setId(c.getInt(0));
                ctc.setTitulo(c.getString(1));
                ctc.setDescripcion(c.getString(2));
                ctc.setFecha(c.getString(3));
                ctc.setHora(c.getString(4));


                if(c.getString(1).toUpperCase().startsWith(nombre.toUpperCase())) {
                   ls.add(ctc);
               }

            }while(c.moveToNext());
        }

        return ls;
    }

    public List<Recordatorio> getAllNotificacines() {
        List<Recordatorio> studentsArrayList = new ArrayList<Recordatorio>();
        String selectQuery = "SELECT  * FROM " + "recordatorio";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Recordatorio recordatorio = new Recordatorio();
                recordatorio.setId(c.getInt(c.getColumnIndex("_id")));
                recordatorio.setTitulo(c.getString(c.getColumnIndex("Titulo")));
                recordatorio.setDescripcion(c.getString(c.getColumnIndex("Descripcion")));
                recordatorio.setFecha(c.getString(c.getColumnIndex("Fecha")));
                recordatorio.setHora(c.getString(c.getColumnIndex("Hora")));
                studentsArrayList.add(recordatorio);
            } while (c.moveToNext());
        }
        return studentsArrayList;
    }

    public List<Recordatorio> notificacionescumplidas(String fecha) {
        List<Recordatorio> studentsArrayList = new ArrayList<Recordatorio>();
        String selectQuery = "SELECT  * FROM " + "recordatorio WHERE Fecha='"+fecha+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Recordatorio recordatorio = new Recordatorio();
                recordatorio.setId(c.getInt(c.getColumnIndex("_id")));
                recordatorio.setTitulo(c.getString(c.getColumnIndex("Titulo")));
                recordatorio.setDescripcion(c.getString(c.getColumnIndex("Descripcion")));
                recordatorio.setFecha(c.getString(c.getColumnIndex("Fecha")));
                recordatorio.setHora(c.getString(c.getColumnIndex("Hora")));
                studentsArrayList.add(recordatorio);
            } while (c.moveToNext());
        }
        return studentsArrayList;
    }



    public ArrayList<Recordatorio> obtenercontacto(String id) {
        ArrayList<Recordatorio> studentsArrayList = new ArrayList<Recordatorio>();
        String selectQuery = "select * from recordatorio where _id='"+id+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Recordatorio recordatorio = new Recordatorio();
                recordatorio.setId(c.getInt(c.getColumnIndex("_id")));
                recordatorio.setTitulo(c.getString(c.getColumnIndex("Titulo")));
                recordatorio.setDescripcion(c.getString(c.getColumnIndex("Descripcion")));
                recordatorio.setFecha(c.getString(c.getColumnIndex("Fecha")));
                recordatorio.setHora(c.getString(c.getColumnIndex("Hora")));
                studentsArrayList.add(recordatorio);
            } while (c.moveToNext());
        }
        return studentsArrayList;
    }


}
