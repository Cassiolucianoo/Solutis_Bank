package com.cassiolucianodasilva.solutisbank.model

import com.google.gson.annotations.SerializedName

class LoginModel {

    @SerializedName("nome")
    var name: String = ""

    @SerializedName("cpf")
    var cpf: String = ""

    @SerializedName("saldo")
    var balance: Float = 0.00F

    @SerializedName("token")
    var token: String = ""

}
