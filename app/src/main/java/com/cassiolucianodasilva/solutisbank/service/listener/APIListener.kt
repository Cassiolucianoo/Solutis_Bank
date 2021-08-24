package com.cassiolucianodasilva.solutisbank.service.listener


interface APIListener<T> {
    //fun onSuccess(result: T, statusCode: Int)
    // fun onFailure(message: String)

    fun onSuccess(model: T)

    fun onFailure(str: String)
}