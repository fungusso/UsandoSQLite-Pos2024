package br.edu.utfpr.usandosqlite_pos2024

import android.R
import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import android.widget.Toast.*
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite_pos2024.adapter.MeuAdapter
import br.edu.utfpr.usandosqlite_pos2024.database.DatabaseHandler
import br.edu.utfpr.usandosqlite_pos2024.databinding.ActivityListarBinding
import kotlin.properties.Delegates

class ListarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListarBinding
    private lateinit var banco: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        banco = DatabaseHandler(this)


        val registros: Cursor =
            banco.cursorList() //fonte  tem que ter selecionado o campo _id, com exatamente este nome

        val adapter = MeuAdapter(this, registros)
        binding.lvPrincipal.adapter = adapter //destino

        val registros1 = banco.list()
        var songs = mutableListOf<Int>()
        var songs1 = mutableListOf<Int>()
        registros1.forEach {
            var i = +1
            if (it.tip_lan == "Debito") {
                songs.add(with(i) { (it.val_lan) }.toInt())
            } else {
                songs1.add(with(i) { (it.val_lan) }.toInt())
            }
        }
        var saldao = songs1.sum() - songs.sum()
        binding.etSaldo.text = saldao.toString()

        // Toast.makeText( this, "Saldo ${saldao}", Toast.LENGTH_LONG ).show()


    }


}
