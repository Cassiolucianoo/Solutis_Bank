package com.cassiolucianodasilva.solutisbank.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cassiolucianodasilva.solutisbank.R
import com.cassiolucianodasilva.solutisbank.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        editPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        progress.visibility = View.GONE
        buttonLogin.setOnClickListener(this)

        alertLogin.setVisibility(View.INVISIBLE);
        cacheLogin()

        observe()


    }

    override fun onClick(v: View) {
        if (v.id == R.id.buttonLogin) {
            handleLogin()
            progress.visibility = View.VISIBLE
        }
    }

    private fun cacheLogin() {
        mViewModel.cacheLogin()

    }



    /**
     * Autentica usuário
     */
    private fun handleLogin() {
        val user = editUser.text.toString()
        val password = editPassword.text.toString()

        mViewModel.doLogin(user, password)

        progress.visibility = View.GONE

    }


    /**
     * TODO
     *Observa ViewModel
     */
    private fun observe() {

        mViewModel.login.observe(this, Observer {
            if (it.success()) {
                startActivity(Intent(this, StatmentActivity::class.java))
                finish()
                progress.visibility = View.VISIBLE
            } else {

                if (editUser.getText().toString().trim().equals("")) {
                    editUser.setHintTextColor(Color.parseColor("#C30000"))
                    editUser.setTextColor(Color.parseColor("#C30000"))
                    editUser.setHint("User")

                } else {

                    editUser.setHintTextColor(Color.parseColor("#485465"))
                    editUser.setTextColor(Color.parseColor("#485465"))
                    editUser.setHint("User")

                }
                if (editPassword.getText().toString().trim().equals("")) {
                    editPassword.setHintTextColor(Color.parseColor("#C30000"))
                    editPassword.setTextColor(Color.parseColor("#C30000"))
                    editPassword.setHint("Password")

                } else {
                    editPassword.setHintTextColor(Color.parseColor("#485465"))
                    editPassword.setTextColor(Color.parseColor("#485465"))
                    editPassword.setHint("Password")

                }
                alertLogin.setVisibility(View.VISIBLE);
                Toast.makeText(applicationContext, it.failure(), Toast.LENGTH_SHORT).show()
                progress.visibility = View.GONE
            }
        })

        //verifica se o campo está em cache
        mViewModel.lastLogin.observe(this, Observer
        {
            if (it != "") {
                editUser.setText(it)

            }
        })
    }



}
