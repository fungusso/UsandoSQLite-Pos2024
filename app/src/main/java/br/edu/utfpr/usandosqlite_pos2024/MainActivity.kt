package br.edu.utfpr.usandosqlite_pos2024

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite_pos2024.R.array.array_credito
import br.edu.utfpr.usandosqlite_pos2024.R.array.array_debito
import br.edu.utfpr.usandosqlite_pos2024.database.DatabaseHandler
import br.edu.utfpr.usandosqlite_pos2024.databinding.ActivityMainBinding
import br.edu.utfpr.usandosqlite_pos2024.entity.Cadastro


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var banco: DatabaseHandler
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var Sel1: String
    private lateinit var etData: String
    private lateinit var etTiplan: String
    private lateinit var etDeslan: String
    private lateinit var etVal: String
    private lateinit var etCod: String

    //   private lateinit var Sel2 : String


    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonListener()

        banco = DatabaseHandler(this)
        spinner1 = this.findViewById(R.id.etTiplan)
        spinner2 = this.findViewById(R.id.etDeslan)


        val Tiplan = arrayOf("Debito", "Credito")
        //   val Contdeb = arrayOf("Luz", "Agua", "Aluguel")
        //   val Contcre = arrayOf("Vendas", "Dividendos","Recuperacao Tributaria")
        spinner1.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Tiplan)
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                Sel1 = spinner1.getSelectedItem().toString()
                if (Sel1 == "Debito") {
                    spinner2.adapter = ArrayAdapter.createFromResource(
                        parent!!.getContext(),
                        array_debito, R.layout.spinner_layout
                    )

                }
                if (Sel1 == "Credito") {
                    spinner2.adapter = ArrayAdapter.createFromResource(
                        parent!!.getContext(),
                        array_credito, R.layout.spinner_layout
                    )

                }
                // Toast.makeText(this@MainActivity,"SELECAO "+ Sel1, Toast.LENGTH_LONG).show()
                //  }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(
                    this@MainActivity,
                    "Expresso da Meia Noite" + Sel1,
                    Toast.LENGTH_SHORT
                ).show()

            }

        }
    }

    private fun setButtonListener() {
        binding.btIncluir.setOnClickListener {
            btIncluirOnClick()
        }

        binding.btAlterar.setOnClickListener {
            btAlterarOnClick()
        }

        binding.btExcluir.setOnClickListener {
            btExcluirOnClick()
        }

        binding.btPesquisar.setOnClickListener {
            btPesquisarOnClick()
        }

        binding.btListar.setOnClickListener {
            btPListarOnClick()
        }
    }

    private fun btIncluirOnClick() {
        //    println(binding.etData)
        //    println(binding.etData)
        etData = binding.etData.text.toString()
        etTiplan = binding.etTiplan.selectedItem.toString()
        etDeslan = binding.etDeslan.selectedItem.toString()
        etVal = binding.etVal.text.toString()


        if (etData == "" || etTiplan == "" || etDeslan == "" || etVal == "" || etDeslan == "Selecione o Debito" || etDeslan == "Selecione o Credito") {
            Toast.makeText(this, "Campo(s) Invalido(s)!!!", Toast.LENGTH_LONG).show()
        } else {
            banco.insert(
                Cadastro(
                    0, binding.etData.text.toString(), binding.etTiplan.selectedItem.toString(),
                    binding.etDeslan.selectedItem.toString(), binding.etVal.text.toString().toInt()
                )
            )
            Toast.makeText(this, "Sucesso!!!", Toast.LENGTH_LONG).show()

        }
    }

    private fun btAlterarOnClick() {
        etData = binding.etData.text.toString()
        etTiplan = binding.etTiplan.selectedItem.toString()
        etDeslan = binding.etDeslan.selectedItem.toString()
        etVal = binding.etVal.text.toString()
        if (etData == "" || etTiplan == "" || etDeslan == "" || etVal == "" || etDeslan == "Selecione o Debito" || etDeslan == "Selecione o Credito") {
            Toast.makeText(this, "Campo(s) Invalido(s)!!!", Toast.LENGTH_LONG).show()
        } else {
            banco.update(
                Cadastro(
                    binding.etCod.text.toString().toInt(),
                    binding.etData.text.toString(),
                    binding.etTiplan.selectedItem.toString(),
                    binding.etDeslan.selectedItem.toString(),
                    binding.etVal.text.toString().toInt()
                )
            )
            Toast.makeText(this, "Sucesso!!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun btExcluirOnClick() {
        etCod = binding.etCod.text.toString()
        if (etCod == "") {
            Toast.makeText(this, "Chave Invalida!!!", Toast.LENGTH_LONG).show()
        } else {
            banco.delete(binding.etCod.text.toString().toInt())
            Toast.makeText(this, "Sucesso!!!", Toast.LENGTH_LONG).show()
        }

    }

    private fun btPesquisarOnClick() {
        etCod = binding.etCod.text.toString()

        if (etCod != "") {
            val registro = banco.find(binding.etCod.text.toString().toInt())

            if (registro != null) {

                if (registro.tip_lan == "Debito") {
                        this.binding.etTiplan.setSelection(0)

                        if (registro.des_lan == "Luz") {
                            this.binding.etDeslan.setSelection(1)
                            }
                        if (registro.des_lan ==  "Agua" ){
                            this.binding.etDeslan.setSelection(2)
                            }
                        if (registro.des_lan ==  "Aluguel" ){
                            this.binding.etDeslan.setSelection(3)
                         }

                }
                if (registro.tip_lan == "Credito" ) {
                        this.binding.etTiplan.setSelection(1)
                        if(registro.des_lan =="Venda") {
                                this.binding.etDeslan.setSelection(1)
                            }
                        if(registro.des_lan == "Dividendos") {
                                this.binding.etDeslan.setSelection(2)
                            }
                        if(registro.des_lan == "Recuperacao") {
                                this.binding.etDeslan.setSelection(3)
                            }

                        }
                this.binding.etData.setText(registro.data_lan)
                this.binding.etVal.setText((registro.val_lan).toString())
            } else {
                Toast.makeText(this, "Registro não encontrado", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Digite o Código", Toast.LENGTH_LONG).show()
        }
    }

    private fun btPListarOnClick() {
        val intent = Intent(this, ListarActivity::class.java)
        startActivity(intent)
        //  val registro =  banco.list()

    }
}