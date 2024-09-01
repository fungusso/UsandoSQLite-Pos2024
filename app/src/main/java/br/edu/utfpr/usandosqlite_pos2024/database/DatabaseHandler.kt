package br.edu.utfpr.usandosqlite_pos2024.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getStringOrNull
import br.edu.utfpr.usandosqlite_pos2024.entity.Cadastro

class DatabaseHandler (context : Context ) : SQLiteOpenHelper ( context, DATABASE_NAME, null, DATABASE_VERSION ) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL( "CREATE TABLE ${TABLE_NAME} (_id INTEGER PRIMARY KEY AUTOINCREMENT, data_lan TEXT, tip_lan TEXT, des_lan TEXT, val_lan INTEGER ) ")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL( "DROP TABLE IF EXISTS ${TABLE_NAME}" )
        onCreate( db )
    }

    fun insert( cadastro : Cadastro) {
        val db = this.writableDatabase


        val registro = ContentValues()
        registro.put( "data_lan", cadastro.data_lan )
        registro.put( "tip_lan", cadastro.tip_lan )
        registro.put("des_lan", cadastro.des_lan)
        registro.put("val_lan",cadastro.val_lan)



        db.insert( TABLE_NAME, null, registro )
    }

    fun update(cadastro : Cadastro) {
        val db = this.writableDatabase

        val registro = ContentValues()
        registro.put( "data_lan", cadastro.data_lan )
        registro.put( "tip_lan", cadastro.tip_lan )
        registro.put( "des_lan", cadastro.des_lan )
        registro.put("val_lan",cadastro.val_lan)
        db.update( TABLE_NAME, registro, "_id=${cadastro._id}", null )
    }

    fun delete( id : Int ) {
        val db = this.writableDatabase

        db.delete( TABLE_NAME, "_id=${id}", null)
    }

    fun find( id : Int ) : Cadastro? {
        val db = this.writableDatabase

        val registro =  db.query(
            "cadastro",
            null,
            "_id=${id}",
            null,
            null,
            null,
            null
        )

        if ( registro.moveToNext() ) {
            val cadastro = Cadastro(
                id,
                registro.getString( DATA_LAN ),
                registro.getString( TIP_LAN ),
                registro.getString(DES_LAN),
                registro.getInt(VAL_LAN)
            )
            return cadastro
        } else {
            return null
        }
    }

    fun list() : MutableList<Cadastro> {
        val db = this.writableDatabase
        val registro =  db.query(
            "cadastro",
            null,
            null,
            null,
            null,
            null,
            null
        )

        var registros = mutableListOf<Cadastro>()

        while ( registro.moveToNext() ) {
            val cadastro = Cadastro(
                registro.getInt( COD ),
                registro.getString( DATA_LAN ),
                registro.getString( TIP_LAN ),
                registro.getString( DES_LAN),
                registro.getInt(VAL_LAN)
            )

            registros.add( cadastro )
        }

        return registros
    }

    fun cursorList() : Cursor {
        val db = this.writableDatabase

        val registro = db.query(
            "cadastro",
            null,
            null,
            null,
            null,
            null,
            null
        )

        return registro
    }

    companion object {
        private const val DATABASE_NAME = "dbfile.sqlite"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "cadastro"
        private const val COD = 0
        private const val DATA_LAN = 1
        private const val TIP_LAN = 2
        private const val DES_LAN = 3
        private const val VAL_LAN = 4
    }

}