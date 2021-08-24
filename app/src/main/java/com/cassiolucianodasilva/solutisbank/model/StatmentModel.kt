package com.cassiolucianodasilva.solutisbank.model

import com.google.gson.annotations.SerializedName
import java.util.*

class StatmentModel {

    @SerializedName("descricao")
    var description: String = ""

    @SerializedName("data")
    lateinit var date: Date

    @SerializedName("valor")
    var value: Double = 0.00

}


