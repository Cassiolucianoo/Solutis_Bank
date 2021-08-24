package com.cassiolucianodasilva.solutisbank.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cassiolucianodasilva.solutisbank.R
import com.cassiolucianodasilva.solutisbank.model.StatmentModel
import kotlinx.android.synthetic.main.card_bills.view.*
import java.text.SimpleDateFormat
import java.text.NumberFormat
import java.util.*

/**
 * TODO
 *RecyclerView.Adapter que é um adapter próprio para RecyclerView
 */
class StatmentAdapter() : RecyclerView.Adapter<StatmentAdapter.StatmentHolder>() {

    //lista  "collections" do StatmentModel
    private var mStatment: List<StatmentModel> = arrayListOf()

    /**
     *chama esse método sempre que precisa criar um novo ViewHolder.
     * O método cria e inicializa o ViewHolder e a View associada, mas não preenche o conteúdo
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatmentHolder {
        val statmentView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_bills, parent, false)

        return StatmentHolder(statmentView)
    }

    /**
     *chama esse método para associar um ViewHolder aos dados.
     * O método busca os dados apropriados e usa esses dados para preencher o layout do fixador de visualização.
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: StatmentHolder, position: Int) {
        holder.bindingData(mStatment[position])
    }

    /**
     *recuperando dado da lista statmentModel
     * @return
     */
    override fun getItemCount(): Int {
        return mStatment.count()
    }


    fun updateStatment(list: List<StatmentModel>) {
        mStatment = list
        notifyDataSetChanged()
    }

    /**
     *Class interna que represente o view holde
     * @constructor
     * TODO
     *
     * @param itemView
     */
    inner class StatmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindingData(statment: StatmentModel) {
            val formatter = SimpleDateFormat("dd/MM/yyyy")


            itemView.billDescription.text = statment.description
            itemView.billDate.text = formatter.format(statment.date)
            itemView.card_value.text = formatDoubleToString(statment.value)

        }

        /**
         * Formata o valor e altera o titulo do pagamento
         *
         * @param value
         * @return
         */

        fun formatDoubleToString(value: Double, showSymbol: Boolean = false): String {
            val numberFormat = NumberFormat.getCurrencyInstance(
                Locale("pt", "BR")
            )
            val symbol = numberFormat.currency!!.symbol
            return if ((!showSymbol) and (value > 0)) {
                "R$" + numberFormat.format(value).replace(symbol, "")
            } else {
                itemView.title_pagamento.text = "Pagamento"
                itemView.card_value.setTextColor(Color.parseColor("#F60404"))
                numberFormat.format(value)
            }
        }

    }


}


