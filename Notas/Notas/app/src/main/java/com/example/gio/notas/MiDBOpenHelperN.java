package com.example.gio.notas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mario on 14/11/2017.
 */

public class MiDBOpenHelperN extends SQLiteOpenHelper {
    private static final String DB_NAME = "mibasesitadatosNotas";
    private static final int DB_VERSION = 1;
    public static final String[]COLUMNS_NOTA =
            {"_id","Titulo","Descripcion"};
    public static final String TABLE_NOTA_NAME="nota";
    private  final String TABLE_NOTA = "create table nota ("+
            COLUMNS_NOTA[0]+" integer primary key autoincrement, "+
            COLUMNS_NOTA[1]+" varchar(100) null," +
            COLUMNS_NOTA[2]+" text not null);";

    public MiDBOpenHelperN(Context contexto) {
        super(contexto, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_NOTA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS nota");
        onCreate(sqLiteDatabase);
    }
}
