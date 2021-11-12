package com.cts.ezcartapp.di.module.internalmodule.authentication

import androidx.lifecycle.ViewModel
import com.cts.ezcartapp.di.viewmodelfactory.ViewModelKey
import com.cts.ezcartapp.viewmodel.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LoginViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel (viewModel : LoginViewModel): ViewModel
}