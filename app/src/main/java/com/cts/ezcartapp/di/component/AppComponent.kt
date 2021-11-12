package com.cts.ezcartapp.di.component

import android.app.Application
import com.cts.ezcartapp.EZApplication
import com.cts.ezcartapp.di.module.AppModule
import com.cts.ezcartapp.di.module.internalmodule.*
import com.cts.ezcartapp.di.module.internalmodule.authentication.LoginActivityModule
import com.cts.ezcartapp.di.module.internalmodule.authentication.LoginViewModelModule
import com.cts.ezcartapp.di.module.internalmodule.authentication.RegistrationActivityModule
import com.cts.ezcartapp.di.module.internalmodule.authentication.RegistrationViewModelModule
import com.cts.ezcartapp.di.viewmodelfactory.ViewModelFactoryModule
import com.cts.ezcartapp.views.HomeActivity
import com.cts.ezcartapp.views.LoginActivity
import com.cts.ezcartapp.views.RegistrationActivity
import com.cts.ezcartapp.views.ui.cart.ViewCartFragment
import com.cts.ezcartapp.views.ui.feedback.FeedbackFragment
import com.cts.ezcartapp.views.ui.home.HomeFragment
import com.cts.ezcartapp.views.ui.home.ProductDetailFragment
import com.cts.ezcartapp.views.ui.orders.YourOrdersFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AndroidSupportInjectionModule::class, ViewModelFactoryModule::class, LocalDependencyBuilder::class])
interface AppComponent : AndroidInjector<EZApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    override fun inject(baseApp: EZApplication)
}

@Module
internal abstract class LocalDependencyBuilder {
    @ContributesAndroidInjector(
        modules = [LoginActivityModule::class,
            LoginViewModelModule::class]
    )
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [RegistrationActivityModule::class, RegistrationViewModelModule::class])
    abstract fun bindRegistrationActivity(): RegistrationActivity

    @ContributesAndroidInjector(modules = [HomeActivityModule::class, HomeActivityFragmentsProvider::class])
    abstract fun bindHomeActivity(): HomeActivity


}

@Module
internal abstract class HomeActivityFragmentsProvider {
    @ContributesAndroidInjector(modules = [ HomeFragmentVMModule::class])
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [ ProductDetailVMModule::class])
    abstract fun bindProductDetailFragment(): ProductDetailFragment

    @ContributesAndroidInjector(modules = [ OrderVMModule::class])
    abstract fun bindYourOrderFragment(): YourOrdersFragment

    @ContributesAndroidInjector(modules = [FeedbackVMModule::class])
    abstract fun bindFeedBackFragment(): FeedbackFragment

    @ContributesAndroidInjector(modules = [ViewCartVMModule::class])
    abstract fun bindViewCartFragment(): ViewCartFragment
}