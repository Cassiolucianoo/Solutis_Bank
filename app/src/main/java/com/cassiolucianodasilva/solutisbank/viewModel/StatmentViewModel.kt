package com.cassiolucianodasilva.solutisbank.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cassiolucianodasilva.solutisbank.R
import com.cassiolucianodasilva.solutisbank.model.StatmentModel
import com.cassiolucianodasilva.solutisbank.service.PersonRepository
import com.cassiolucianodasilva.solutisbank.service.constants.StatmentsConstants
import com.cassiolucianodasilva.solutisbank.service.listener.APIListener
import com.cassiolucianodasilva.solutisbank.service.repository.local.SecurityPreferences
import com.cassiolucianodasilva.solutisbank.utils.CpfCnpjMask


class StatmentViewModel(application: Application) : AndroidViewModel(application) {

    private val mSecurityPreferences = SecurityPreferences(application)
    private val mStatmentRepository = PersonRepository(application)
    private val mLogout = MutableLiveData<Boolean>()
    private val mStatment = MutableLiveData<List<StatmentModel>>()
    var statment: LiveData<List<StatmentModel>> = mStatment

    fun loadData(): HashMap<String, String> {

        val name = mSecurityPreferences.get(StatmentsConstants.USER.USER_NAME)
        var cpf = mSecurityPreferences.get(StatmentsConstants.USER.USER_CPF)
        val balance = mSecurityPreferences.get(StatmentsConstants.USER.USER_BALANCE)

        val cpfcovert = CpfCnpjMask()
        cpf = cpfcovert.cpfCnpj(cpf)

        val values = HashMap<String, String>()
        values[StatmentsConstants.USER.USER_NAME] = name
        values[StatmentsConstants.USER.USER_CPF] = cpf.format("")
        values[StatmentsConstants.USER.USER_BALANCE] = "R$ " + balance.replace('.', ',')

        return values
    }


    fun loadStatment() {
        mStatmentRepository.statment(
            mSecurityPreferences.get(StatmentsConstants.USER.USER_TOKEN),
            object : APIListener<List<StatmentModel>> {
                override fun onSuccess(model: List<StatmentModel>) {
                    mStatment.value = model
                }

                override fun onFailure(str: String) {

                    Toast.makeText(
                        getApplication(),
                        R.string.erroatualizarextrato,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

    fun logout() {
        // mSecurityPreferences.remove(StatmentsConstants.SHARED.USER_LOGIN)
        mLogout.value = true
    }


}