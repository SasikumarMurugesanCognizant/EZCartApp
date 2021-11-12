package com.cts.ezcartapp.di.module.internalmodule

import androidx.lifecycle.ViewModel
import com.cts.ezcartapp.di.viewmodelfactory.ViewModelKey
import com.cts.ezcartapp.viewmodel.LoginViewModel
import com.cts.ezcartapp.views.ui.cart.ViewCartViewModel
import com.cts.ezcartapp.views.ui.feedback.FeedbackViewModel
import com.cts.ezcartapp.views.ui.home.HomeViewModel
import com.cts.ezcartapp.views.ui.home.ProductDetailViewModel
import com.cts.ezcartapp.views.ui.orders.YourOrdersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeFragmentVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeVM (viewModel : HomeViewModel): ViewModel
}

@Module
abstract class ProductDetailVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailViewModel::class)
    abstract fun bindProductVM (viewModel : ProductDetailViewModel): ViewModel
}

@Module
abstract class ViewCartVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(ViewCartViewModel::class)
    abstract fun bindViewCartVM (viewModel : ViewCartViewModel): ViewModel
}
@Module
abstract class FeedbackVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(FeedbackViewModel::class)
    abstract fun bindFeedbackVM (viewModel : FeedbackViewModel): ViewModel
}

@Module
abstract class OrderVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(YourOrdersViewModel::class)
    abstract fun bindFeedbackVM (viewModel : YourOrdersViewModel): ViewModel
}