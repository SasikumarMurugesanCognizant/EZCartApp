package com.cts.ezcartapp.di.module.internalmodule.authentication

import androidx.lifecycle.ViewModel
import com.cts.ezcartapp.di.viewmodelfactory.ViewModelKey
import com.cts.ezcartapp.viewmodel.LoginViewModel
import com.cts.ezcartapp.viewmodel.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RegistrationViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindRegistrationVM (viewModel : RegistrationViewModel): ViewModel
}