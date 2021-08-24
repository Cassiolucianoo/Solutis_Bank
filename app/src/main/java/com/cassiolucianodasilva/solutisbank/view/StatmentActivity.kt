package com.cassiolucianodasilva.solutisbank.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cassiolucianodasilva.solutisbank.R
import com.cassiolucianodasilva.solutisbank.service.constants.StatmentsConstants
import com.cassiolucianodasilva.solutisbank.view.adapter.StatmentAdapter
import com.cassiolucianodasilva.solutisbank.viewModel.StatmentViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.header_list_card.*


class StatmentActivity : AppCompatActivity(), View.OnClickListener {
    private val mAdapter = StatmentAdapter()
    private lateinit var mViewModel: StatmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statment)
        mViewModel = ViewModelProvider(this).get(StatmentViewModel::class.java)

        loadData()
        loadStatment()

        //referenciando recyclerView
        val recycler = findViewById<RecyclerView>(R.id.recyler_statment)

        // para que os elementos apareçam, é necessário configurar  um LayoutManager, portanto, crie um objeto do tipo
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = mAdapter

        logout.setOnClickListener(this)

        observe()

    }

    private fun loadData() {
        val values = mViewModel.loadData()
        nomeUsuario.text = values[StatmentsConstants.USER.USER_NAME]
        numeroConta.text = values[StatmentsConstants.USER.USER_CPF]
        valorConta.text = values[StatmentsConstants.USER.USER_BALANCE]
    }


    private fun observe() {
        mViewModel.statment.observe(this, Observer {
            if (it.count() > 0) {
                mAdapter.updateStatment(it)
            }
        })

    }

    private fun loadStatment() {
        mViewModel.loadStatment()

    }


    /**
     * Função com dialog sair da aplicação
     */
    private fun exit() {

        AlertDialog.Builder(this)
            //.setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Sair da Conta")
            .setMessage("Deseja realmente sair ?")
            .setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, which -> finish() })
            .setNegativeButton("Não", null)
            .show()

    }

    /**
     *Ao precionar o botão do dispositivo voltar e apresenta o alert
     */
    override fun onBackPressed() {

        exit()
    }

    /**
     *Finalizar aplicação ao selecionar bt logout
     */
    override fun onClick(v: View) {
        if (v.id == R.id.logout) {
            mViewModel.logout()
            exit()

        }
    }


}


