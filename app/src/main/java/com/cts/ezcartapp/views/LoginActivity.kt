package com.cts.ezcartapp.views

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.cts.ezcartapp.utils.Constants.isLoggedIn
import com.cts.ezcartapp.utils.Constants.userId
import com.cts.ezcartapp.utils.Constants.userName
import com.cts.ezcartapp.utils.Constants.userPassword
import com.cts.ezcartapp.R
import com.cts.ezcartapp.data.database.AppDatabase
import com.cts.ezcartapp.databinding.ActivityLoginBinding
import com.cts.ezcartapp.di.viewmodelfactory.ViewModelProviderFactory
import com.cts.ezcartapp.utils.SharedPreference
import com.cts.ezcartapp.viewmodel.LoginViewModel
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    lateinit var dataBinding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private lateinit var appDataBase: AppDatabase
    private lateinit var sharedPref: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this);
        supportActionBar?.hide()
        sharedPref = SharedPreference(this)
        if (sharedPref.getLoginStatus(isLoggedIn, false)) {
            startActivity(
                Intent(
                    this,
                    HomeActivity::class.java
                ).setFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
            )
        }
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        dataBinding.lifecycleOwner = this


        viewModel = ViewModelProvider(this,viewModelProviderFactory)[LoginViewModel::class.java]
        dataBinding.viewModel = viewModel

        dataBinding.btnSignup.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        dataBinding.btnLogin.setOnClickListener {
            viewModel.onLoginClick()
        }
        initObserver()
    }

    private fun initObserver() {
        viewModel.userLiveData.observe(this, {
            if (it.isEmailValid()) {
                viewModel.doLogin(it)
            } else {
                showWarningMessage("Please enter valid Email ID")
            }
        })
        viewModel.loginStatus.observe(this, {
            if (it) {
                showWarningMessage("Login Success.. ${viewModel.userId.value?:""}")
                sharedPref.save(userId,viewModel.userId.value?:"")
                sharedPref.save(userName,viewModel.username.value?:"")
                sharedPref.save(userPassword,viewModel.password.value?:"")
                sharedPref.save(isLoggedIn,true)
                startActivity(
                    Intent(
                        this,
                        HomeActivity::class.java
                    ).setFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
                )

            } else {
                showWarningMessage("Login Failed..")
            }
        })
    }

    private fun showWarningMessage(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}