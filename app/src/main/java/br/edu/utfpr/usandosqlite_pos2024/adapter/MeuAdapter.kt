package br.edu.utfpr.usandosqlite_pos2024.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import br.edu.utfpr.usandosqlite_pos2024.R
import br.edu.utfpr.usandosqlite_pos2024.R.layout.elemento_lista
import br.edu.utfpr.usandosqlite_pos2024.entity.Cadastro

class MeuAdapter(val context : Context, val cursor : Cursor) : BaseAdapter() {
    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(position: Int): Any {
        cursor.moveToPosition( position )
        val cadastro = Cadastro(
            cursor.getInt(  0 ),
            cursor.getString( 1 ),
            cursor.getString( 2 ),
            cursor.getString( 3),
            cursor.getInt(4)

        )
        return cadastro
    }


    override fun getItemId(position: Int): Long {
        cursor.moveToPosition( position )
        return cursor.getLong( 0 )
    }


    @SuppressLint("ViewHolder", "MissingInflatedId", "ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ) as LayoutInflater
        val v = inflater.inflate( elemento_lista, null )
        val tvKeyElementoListar = v.findViewById<TextView>( R.id.tvKeyElementoListar)
        val tvDataElementoListar = v.findViewById<TextView>( R.id.tvDataElementoListar)
        val tvTipElementoListar = v.findViewById<TextView>( R.id.tvTipElementoListar )
        val tvDesElementoListar = v.findViewById<TextView>(R.id.tvDesElementoListar)
        val tvValElementoListar = v.findViewById<TextView>(R.id.tvValElementoListar)
       // val tvBotao = v.findViewById<Button>(R.id.Excbutton)
       // tvBotao.setOnClickListener{


      //  }
        cursor.moveToPosition( position )

        tvKeyElementoListar.setText(cursor.getString( 0 ) )
        tvDataElementoListar.setText( cursor.getString( 1 ) )
        tvTipElementoListar.setText( cursor.getString( 2 ) )
        tvDesElementoListar.setText(cursor.getString(3))
        tvValElementoListar.setText(cursor.getString(4))


        val tipo = tvTipElementoListar.text.toString()
        println(tipo)
        if (tipo == "Debito") {
            tvKeyElementoListar.setTextColor(Color.RED)
            tvDataElementoListar.setTextColor(Color.RED)
            tvTipElementoListar.setTextColor(Color.RED)
            tvDesElementoListar.setTextColor(Color.RED)
            tvValElementoListar.setTextColor(Color.RED)
        }

        return v

    }

}
