package com.ementas.projecto.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ementas.projecto.models.Ementa;
import com.ementas.projecto.models.Fatura;

import java.util.List;


public class LocalCache extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "mobilecanteen";
    private static final String EMENTA_TABLE_NAME = "ementa";
    private static final String FATURA_TABLE_NAME = "fatura";

    public LocalCache(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createEmentaTable = "CREATE TABLE " + EMENTA_TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY, " +
                "diadasemana TEXT, " +
                "data TEXT, " +
                "refeicao TEXT, " +
                "sopa TEXT, " +
                "carne TEXT, " +
                "peixe TEXT, " +
                "vegetariano TEXT, " +
                "sombremesa TEXT, " +
                "haementa TEXT " +
                ")";

        String createFaturaTable = "CREATE TABLE " + FATURA_TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "data TEXT, " +
                "preco FLOAT, " +
                "refeicao TEXT, " +
                "prato TEXT " +
                ")";

        db.execSQL(createEmentaTable);
        db.execSQL(createFaturaTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EMENTA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FATURA_TABLE_NAME);
        onCreate(db);
    }

    public boolean saveEmentas(List<Ementa> ementas) {
        boolean allSaved = true;
        for (Ementa e : ementas) {
            if (!save(e)) {
                allSaved = false;
            }
        }

        return allSaved;
    }

    public boolean save(Ementa ementa) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", ementa.getId());
        values.put("diadasemana", ementa.getDiadasemana());
        values.put("data", ementa.getData());
        values.put("refeicao", ementa.getRefeicao());
        values.put("sopa", ementa.getSopa());
        values.put("carne", ementa.getCarne());
        values.put("peixe", ementa.getPeixe());
        values.put("vegetariano", ementa.getVegetariano());
        values.put("sobremesa", ementa.getSobremesa());
        values.put("haementa", ementa.getHaementa());

        long id = db.insert(EMENTA_TABLE_NAME, null, values);
        if (id > -1) {
            ementa.setId(id);
            return true;
        }

        return false;
    }

    public boolean saveFatura(Fatura fatura) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("data", fatura.getData());
        values.put("preco", fatura.getPreco());
        values.put("refeicao", fatura.getRefeicao());
        values.put("prato", fatura.getPrato());

        long id = db.insert(FATURA_TABLE_NAME, null, values);
        if (id > -1) {
            //fatura.setId(id);
            return true;
        }

        return false;
    }
}