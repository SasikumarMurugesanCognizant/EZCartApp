package com.cts.ezcartapp.views

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cts.ezcartapp.R
import com.cts.ezcartapp.data.entities.UserData
import com.cts.ezcartapp.databinding.ActivityRegistrationBinding
import com.cts.ezcartapp.di.viewmodelfactory.ViewModelProviderFactory
import com.cts.ezcartapp.utils.Extensions.encrypt
import com.cts.ezcartapp.viewmodel.RegistrationViewModel
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class RegistrationActivity : AppCompatActivity() {
    lateinit var dataBinding: ActivityRegistrationBinding
    @Inject
    lateinit var factory: ViewModelProviderFactory
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    lateinit var viewModel: RegistrationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        supportActionBar?.hide()
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        viewModel = ViewModelProvider(this,factory)[RegistrationViewModel::class.java]
        dataBinding.viewModel = viewModel
        initObserver()
        dataBinding.btnRegister.setOnClickListener {
            val firstName = dataBinding.etFirstname.text.toString()
            val lastName = dataBinding.etLastname.text.toString()
            val userId = dataBinding.etUserId.text.toString()
            val password = dataBinding.etPassword.text.toString()
            val mobileNo = dataBinding.etMobileNo.text.toString()
            val validationMessage =
                viewModel.doSignUpValidation(firstName, lastName, userId, password, mobileNo)
            if (validationMessage.isNotEmpty()) {
                showErrorMessage(validationMessage)
            } else {
               password.encrypt()?.let {
                   val userData = UserData(firstName, lastName, userId, it,mobileNo)
                   viewModel.doSignUp(userData)
               }

            }
        }

    }

    private fun showErrorMessage(validationMessage: String) {
        Toast.makeText(this, validationMessage, Toast.LENGTH_SHORT).show()
    }

    private fun initObserver() {
        viewModel.isSignUpCompleted.observe(this, Observer {
            if (it) {
                showErrorMessage("Signup Completed. Please login")
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}